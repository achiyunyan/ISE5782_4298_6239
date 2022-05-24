package renderer;

import java.util.*;

import static java.lang.System.out;

import primitives.*;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {

    /**
     * Constant for first moving magnitude rays for shading rays
     */

    private static final int MAX_CALC_COLOR_LEVEL = 3;
    private static final double MIN_CALC_COLOR_K = 0.1;
    private static final Double3 INITIAL_K = new Double3(1);

    private double DISTANCE = 350;
    private double RADIUS = 5;
    private int RAYSNUM = 1;

    /**
     * Ctor for 'RayTracerBasic'
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Activates the improvement "Glossy/Blurry"
     * 
     * @param raysNum
     * @return
     */
    public RayTracerBasic setGlossyBlurry(int raysNum, double distance, double radius) {
        RAYSNUM = raysNum;
        RADIUS = radius;
        DISTANCE = distance;
        return this;

    }

    /**
     * Activates the improvement "Glossy/Blurry"
     * 
     * @param raysNum
     * @return
     */
    public RayTracerBasic setGlossyBlurry(int raysNum) {
        RAYSNUM = raysNum;
        return this;

    }

    /**
     * returns the color of the ray
     *
     * @param ray
     * @return the color of the ray
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null)
            return scene.background;
        return calcColor(gp, ray);
    }

    /**
     * Shell function for the recursive calcColor func.
     * 
     * @param geopoint
     * @param ray
     * @return![](../../images/refractionTwoSpheres.png)
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color recursively
     * 
     * @param gp
     * @param ray
     * @param level
     * @param k
     * @return
     */
    public Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        // out.println(level);
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray.getDir(), level, k));
    }

    /**
     * Calculate the color effects caused by the surroundings of the object
     * 
     * @param gp
     * @param v
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kkr = k.product(material.kR);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
            color = calcGlobalEffect(
                    generateBeamToCircle(constructReflectedRay(gp.point, v, n), DISTANCE, RADIUS),
                    level, material.kR, kkr);
        Double3 kkt = k.product(material.kT);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(
                            generateBeamToCircle(constructRefractedRay(gp.point, v, n),
                                    DISTANCE, RADIUS),
                            level, material.kT, kkt));
        return color;
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Global effect of list of rays
     * 
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @return
     */
    private Color calcGlobalEffect(List<Ray> rays, int level, Double3 kx, Double3 kkx) {
        Color sum = new Color(0, 0, 0);
        int intersectionsnum = 0;
        for (Ray ray : rays) {
            GeoPoint gp = findClosestIntersection(ray);
            if (gp != null) {
                sum = sum.add(calcColor(gp, ray, level, kkx));
                intersectionsnum++;
            } 
        }
        if (intersectionsnum > 0)
            sum = sum.reduce(intersectionsnum);
        return (intersectionsnum == 0 ? scene.background : sum).scale(kx);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Calculate the color effects caused by the material itself
     * 
     * @param gp
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        int nShininess = gp.geometry.getMaterial().nShininess;
        Double3 kd = gp.geometry.getMaterial().kD, ks = gp.geometry.getMaterial().kS;

        Color color = gp.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr = transparency(gp, lightSource, l, n);
                // if (unshaded(gp, l, n, lightSource)) {
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                    Color lightIntensity = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(lightIntensity.scale(calcDiffusive(kd, l, n, lightIntensity)),
                            lightIntensity.scale(calcSpecular(ks, l, n, v, nShininess, lightIntensity)));
                }
            }
        }

        return color;
    }

    private Double3 calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n)));
        return ks.scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), nShininess));
    }

    private Double3 calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = l.dotProduct(n);
        return kd.scale(Math.abs(ln));
    }

    /**
     * Construct a reflection ray
     * 
     * @param point
     * @param v
     * @param n
     * @return
     */
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        return new Ray(point.add(n.normalize().scale(0.001)), v.subtract(n.scale(2 * v.dotProduct(n))), n);
    }

    /**
     * Construct a refraction ray
     * 
     * @param point
     * @param v
     * @param n
     * @return
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point.add(n.normalize().scale(-0.001)), v, n);
    }

    /**
     * Find the closest intersection
     * 
     * @param ray
     * @return
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> l = scene.geometries.findGeoIntersections(ray);
        if (l == null) // golo
            return null;
        return ray.findClosestGeoPoint(l);
    }

    /**
     * UnShaded function
     * 
     * @param gp Geo Point
     * @param l  Vector
     * @return
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource ls) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        double maxDistance = ls.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, maxDistance);

        return intersections == null || intersections.isEmpty() || gp.geometry.getMaterial().kT != Double3.ZERO;
    }

    /**
     * Calculates the Transparency
     * 
     * @param geoPoint The Geo Point
     * @param ls       The Light Source
     * @param l
     * @param n
     * @return a numerical value for the transparency
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double lightDistance = ls.getDistance(geoPoint.point);
        var intersections = scene.geometries.findGeoIntersections(lightRay, lightDistance);
        if (intersections == null)
            return new Double3(1.0);
        Double3 ktr = new Double3(1.0);
        for (GeoPoint gp : intersections) {
            ktr = ktr.product(gp.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return new Double3(0.0);
        }
        return ktr;
    }

    /**
     * 
     * 
     * @param source
     * @param target
     * @param radius
     * @return
     */
    private List<Ray> generateBeamToCircle(Ray ray, double distance, double radius) {
        Point source = ray.getP0();
        Vector dir = ray.getDir();
        List<Ray> list = new LinkedList<Ray>();
        // First find the center of the created target area
        Point pCenter = source.add(dir.normalize().scale(distance));
        // Then find another point on the circle and create two orthogonal vectors

        Vector vUp = dir.findNormal().normalize();
        Vector vRight = dir.crossProduct(vUp).normalize();

        // Now iteratively create ray beam two the square which contains the circle
        // Include only rays directed to the circle

        int length = (int) Math.sqrt(RAYSNUM);
        double R = Util.alignZero(2 * radius / length);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                double xJ = Util.alignZero((j - (double) (length - 1) / 2) * R);
                double yI = Util.alignZero(-(i - (double) (length - 1) / 2) * R);
                Point pIJ = pCenter;
                if (xJ != 0)
                    pIJ = pIJ.add(vRight.scale(xJ));
                if (yI != 0)
                    pIJ = pIJ.add(vUp.scale(yI));
                double d = pIJ.distance(pCenter);
                if (radius > d) {
                    list.add(new Ray(source, pIJ.subtract(source).normalize().scale(dir.length())));
                }
            }
        }

        return list.size() != 0 ? list : null;
    }
}