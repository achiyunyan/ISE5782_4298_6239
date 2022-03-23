package geometries;

import java.util.*;
import primitives.*;

public interface Intersectable {
    /**
     * Returns the Intersections (List of Points) of the Geometry with the Given Ray.
     * 
     * @param ray
     * @return
     */
    public List<Point> findIntersections(Ray ray);
}