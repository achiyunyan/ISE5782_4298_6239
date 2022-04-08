package renderer;

import java.util.*;

import primitives.*;
import geometries.*;
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
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null)
            return scene.background;
        return calcColor(ray.findClosestPoint(intersections));
    }

    public Color calcColor(Point p) {
        return scene.ambientLight.getIntensity();
    }
}