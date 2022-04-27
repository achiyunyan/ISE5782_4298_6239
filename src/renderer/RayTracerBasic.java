package renderer;

import java.util.*;

import primitives.*;
import geometries.Intersectable.GeoPoint;
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
     * @param ray
     * @return the color of the ray
     */
    @Override
    public Color traceRay (Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return scene.background;
        return calcColor(ray.findClosestGeoPoint(intersections));
    }

    public Color calcColor(GeoPoint gp) {
        return scene.ambientLight.getIntensity().add(gp.geometry.getEmission());
    }
}