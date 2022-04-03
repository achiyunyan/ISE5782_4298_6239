package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Geometries implements Intersectable {
    List<Intersectable> geometries;

    /**
     * Default ctor for geometries
     * 
     */
    public Geometries() {
        this.geometries = new LinkedList<Intersectable>();// we only need to implement add so linked list is better;
    }

    /**
     * Ctor for geometries
     * 
     * @param geometries
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = new LinkedList<Intersectable>();
        for (Intersectable geometry : geometries) {
            this.geometries.add(geometry);
        }
    }

    /**
     * Add geometries to the list of geometries
     * 
     * @param geometries
     */
    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries) {
            this.geometries.add(geometry);
        }
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        if (geometries.size() == 0)
            return null;
        boolean intersections = false;
        List<Point> result = new LinkedList<Point>();
        for (Intersectable intersectable : geometries) {
            List<Point> inter = intersectable.findIntersections(ray);
            if (inter != null) {
                intersections = true;
                for (Point point : inter) {
                    result.add(point);
                }
            }
        }
        if(intersections)
            return result;
        return null;    
    }
}
