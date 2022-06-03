package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.*;
import static primitives.Util.*;
import primitives.Vector;

public class Sphere extends Geometry {
    private Point center;
    private double radius;

    /**
     * Constructor for 'Sphere'
     * 
     * @param center
     * @param radius
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;

        // Create Box
        Double3 p0 = new Double3(center.getX() - radius, center.getY() - radius, center.getZ() - radius);
        Double3 p1 = new Double3(center.getX() + radius, center.getY() + radius, center.getZ() + radius);
        this.box = new Box(p0, p1);
    }

    /**
     * Returns the Normal of the Sphere to a given Point.
     * 
     * @param point
     * 
     * @return Vector
     */
    @Override
    public Vector getNormal(Point point) {
        Vector v = point.subtract(center);
        return v.normalize();
    }

    @Override
    public String toString() {
        return "center = " + center + ", radius = " + radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    /**
     * Return the intersections between the sphere and the ray
     * can be 2, 1 , or 0 .
     * 
     * @param ray
     * 
     * @return List<GeoPoint>
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getP0();
        Vector dir = ray.getDir();
        Vector u;

        try {
            u = center.subtract(p0);
            double tm = alignZero(dir.dotProduct(u));
            double d = Math.sqrt(alignZero(u.lengthSquared() - tm * tm));
            if (isZero(d - radius) || d > radius)
                return null;

            double th = Math.sqrt(radius * radius - d * d);
            double t1 = tm + th;
            double t2 = tm - th;
            if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
                return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));
            }
            else if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
                return List.of(new GeoPoint(this,ray.getPoint(t1)));
            }
            else if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
                return List.of(new GeoPoint(this,ray.getPoint(t2)));
            }
            
            return null;

        } catch (IllegalArgumentException exec) {
            return List.of(new GeoPoint(this, center.add(dir.scale(radius))));
        }
    }
}