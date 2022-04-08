package renderer;

import primitives.*;
import scene.Scene;
/**
 * Ray tracer base class 
 */
public abstract class RayTracerBasic {
    protected Scene scene;
    /**
     * Ctor for 'RayTracerBasic'
     * 
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        this.scene = scene;
    }
    
    public abstract Color traceRay (Ray ray);
}
