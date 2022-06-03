package geometries;

import java.util.*;
import primitives.*;


public abstract class Intersectable {
    public static boolean isBox = false;
    Box box;

    public Boolean isIntersection(Ray ray){
       return !isBox || box.isIntersection(ray);
    }

    /**
     * Returns the Intersections (List of Points) of the Geometry with the Given Ray.
     * 
     * @param ray
     * @return
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                               : geoList.stream().map(gp -> gp.point).toList();
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return isIntersection(ray)? findGeoIntersections(ray, Double.POSITIVE_INFINITY):null;
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return isIntersection(ray)? findGeoIntersectionsHelper(ray, maxDistance):null;
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /** 
     * A Point with the Geometry that contains it
     */
    public static class GeoPoint {
        /**
         * The Geometry that the Point is on
         */
        public Geometry geometry;

        /**
         * The Point
         */
        public Point point;

        /**
         * Ctor for GeoPoint
         * 
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            GeoPoint other = (GeoPoint) obj;
            if (geometry == null) {
                if (other.geometry != null)
                    return false;
            } else if (!geometry.equals(other.geometry))
                return false;
            if (point == null) {
                if (other.point != null)
                    return false;
            } else if (!point.equals(other.point))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "GeoPoint [geometry=" + geometry + ", point=" + point + "]";
        }
        
    }
}