package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import static primitives.Util.*;
import primitives.Vector;

public class Sphere implements Geometry {
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
     * @return List<Point>
     */
    @Override
    public List<Point> findIntersections(Ray ray) {

        Point p0 = ray.getP0();
        Vector dir = ray.getDir();
        Vector u;
        try {
            u = center.subtract(p0);
            double tm = alignZero(dir.dotProduct(u));
            double d = Math.sqrt(u.lengthSquared() - tm * tm);
            if (isZero(d - radius) || d > radius)
                return null;
            double th = Math.sqrt(radius * radius - d * d);
            double t1 = tm + th;
            double t2 = tm - th;
            if (t1 > 0 && t2 > 0) {
                return List.of(p0.add(dir.scale(t1)), p0.add(dir.scale(t2)));
            } else if (t1 > 0) {
                return List.of(p0.add(dir.scale(t1)));
            } else if (t2 > 0) {
                return List.of(p0.add(dir.scale(t2)));
            }
            return null;
        } catch (IllegalArgumentException exec) {
            return List.of(center.add(dir.scale(radius)));
        }
    }
}