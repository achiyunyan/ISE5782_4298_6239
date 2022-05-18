package renderer;

import java.util.*;

import primitives.*;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {

    /**
     * Constant for first moving magnitude rays for shading rays
     */

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = new Double3(1.0);

    /**
     * Ctor for 'RayTracerBasic'
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
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
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
        Double3 kkt = k.product(material.kT);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        return color;
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

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
        return new Ray(point, v.subtract(n.scale(2 * v.dotProduct(n))), n);
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
        return new Ray(point, v, n);
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
}