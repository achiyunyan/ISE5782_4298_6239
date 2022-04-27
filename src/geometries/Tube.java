package geometries;

import java.util.List;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Tube extends Geometry {
    protected Ray axisRay;
    protected double radius;

    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        if (radius < 0)
            throw new IllegalArgumentException("Radius can't be negative");
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }
/**
     * Returns the Normal of the Tube to a given Point.
     * 
     * @param point
     * 
     * @return Vector
     */
    @Override
    public Vector getNormal(Point point) {
        double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));
        Point O;
        if (t != 0)
            O = axisRay.getP0().add(axisRay.getDir().scale(t));
        else
            O = axisRay.getP0();
        Vector v = point.subtract(O);
        return v.normalize();
    }

    @Override
    public String toString() {
        return "axisRay = " + axisRay + ", radius = " + radius;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // TODO Auto-generated method stub
        return null;
    }
}