package geometries;

import java.util.List;
import primitives.*;

public class Triangle extends Polygon {
    /**
     * Constructor for the class 'Triangle'
     * Recives 3 points and send them to the ctor of 'Polygon'
     * 
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**
     * Returns the Normal of the Triangle to a given Point.
     * 
     * @param point
     * 
     * @return Vector
     */
    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

    @Override
    public String toString() {
        return "P1: " + vertices.get(0).toString() + "P2: " + vertices.get(1).toString() + "P3: "
                + vertices.get(2).toString();
    }
    /**
     * Return the intersections between the triangle and the ray
     * can be 1 , or 0 .
     * @param ray
     * 
     * @return List<GeoPoint>
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> list = super.plane.findGeoIntersections(ray);
        if (list == null)
            return null;

        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray.getP0());

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        Vector v = ray.getDir();
        if (v.dotProduct(n1) > 0 && v.dotProduct(n2) > 0 && v.dotProduct(n3) > 0)
            return list;
        else if (v.dotProduct(n1) < 0 && v.dotProduct(n2) < 0 && v.dotProduct(n3) < 0)
            return list;
            
        return null;
    }
}