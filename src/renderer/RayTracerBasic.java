package renderer;

import java.util.*;

import primitives.*;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {
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
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return scene.background;
        return calcColor(ray.findClosestGeoPoint(intersections), ray);
    }

    public Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity().add(gp.geometry.getEmission(), calcLocalEffects(gp, ray));
    }

    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        int nShininess = gp.geometry.getMaterial().nShininess;
        Double3 kd = gp.geometry.getMaterial().kD, ks = gp.geometry.getMaterial().kS;
        Color color = Color.BLACK;

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(gp.point);
                color = color.add(lightIntensity.scale(calcDiffusive(kd, l, n, lightIntensity)),
                lightIntensity.scale(calcSpecular(ks, l, n, v, nShininess, lightIntensity)));
            }
        }
        return color;
    }

    private Double3 calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n)));
        return ks.scale(Math.pow(Math.max(0, -v.dotProduct(r)), nShininess));
    }

    private Double3 calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = l.dotProduct(n);
        return kd.scale(Math.abs(ln));
    }
}