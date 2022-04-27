package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Geometries extends Intersectable {
    List<Geometry> geometries;

    /**
     * Default ctor for geometries
     * 
     */
    public Geometries() {
        this.geometries = new LinkedList<Geometry>();// we only need to implement add so linked list is better;
    }

    /**
     * Ctor for geometries
     * 
     * @param geometries
     */
    public Geometries(Geometry... geometries) {
        this.geometries = new LinkedList<Geometry>();
        for (Geometry geometry : geometries) {
            this.geometries.add(geometry);
        }
    }

    /**
     * Add geometries to the list of geometries
     * 
     * @param geometries
     */
    public void add(Geometry... geometries) {
        for (Geometry geometry : geometries) {
            this.geometries.add(geometry);
        }
    }

    /**
     * 
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (geometries.size() == 0)
        return null;
        boolean intersections = false;
        List<GeoPoint> result = new LinkedList<GeoPoint>();
        for (Geometry geometry : geometries) {
            List<Point> geo = geometry.findIntersections(ray);
            if (geo != null) {
                intersections = true;
                for (Point point : geo) {
                    result.add(new GeoPoint(geometry, point));
                }
            }
        }
        if(intersections)
            return result;
        return null; 
    }
}