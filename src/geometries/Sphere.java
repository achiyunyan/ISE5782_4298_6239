package geometries;

import primitives.Point;
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
}