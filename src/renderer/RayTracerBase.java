package renderer;

import primitives.*;
import scene.Scene;
/**
 * Ray tracer base class 
 */
public abstract class RayTracerBase {
    protected Scene scene;
    /**
     * Ctor for 'RayTracerBase'
     * 
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * returns the color of the ray
     * @param ray
     * @return the color of the ray
     */
    public abstract Color traceRay (Ray ray);
}