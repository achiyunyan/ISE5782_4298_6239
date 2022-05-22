package geometries;

import java.util.*;

import primitives.*;
import primitives.Vector;

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

    /*@Override
    public List<Point> findIntersections(Ray ray) {
        // TODO Auto-generated method stub
        return null;
    }*/

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double max) {
        Vector dir = ray.getDir();
        Vector v = axisRay.getDir();
        double dirV = dir.dotProduct(v);

        if (ray.getP0().equals(axisRay.getP0())) { // In case the ray starts on the p0.
            if (Util.isZero(dirV))
                return List.of(new Intersectable.GeoPoint(this, ray.getPoint(radius)) );

            if (dir.equals(v.scale(dir.dotProduct(v))))
                return null;

            return List.of(new Intersectable.GeoPoint(this, ray.getPoint(
                    Math.sqrt(radius * radius / dir.subtract(v.scale(dir.dotProduct(v)))
                            .lengthSquared()))) );
        }

        Vector deltaP = ray.getP0().subtract(axisRay.getP0());
        double dpV = deltaP.dotProduct(v);

        double a = 1 - dirV * dirV;
        double b = 2 * (dir.dotProduct(deltaP) - dirV * dpV);
        double c = deltaP.lengthSquared() - dpV * dpV - radius * radius;

        if (Util.isZero(a)) {
            if (Util.isZero(b)) { // If a constant equation.
                return null;
            }
            return List.of(new Intersectable.GeoPoint(this, ray.getPoint(-c / b))); // if it's linear, there's a solution.
        }

        double discriminant = Util.alignZero(b * b - 4 * a * c);

        if (discriminant < 0) // No real solutions.
            return null;

        double t1 = Util.alignZero(-(b + Math.sqrt(discriminant)) / (2 * a)); // Positive solution.
        double t2 = Util.alignZero(-(b - Math.sqrt(discriminant)) / (2 * a)); // Negative solution.

        if (discriminant <= 0) // No real solutions.
            return null;

        if (t1 > 0 && t2 > 0) {
            List<GeoPoint> _points = new LinkedList<GeoPoint>();
            _points.add(new Intersectable.GeoPoint(this, ray.getPoint(t1)));
            _points.add(new Intersectable.GeoPoint(this, ray.getPoint(t2)));
            return _points;
        }
        else if (t1 > 0) {
            List<GeoPoint> _points = new LinkedList<GeoPoint>();
            _points.add(new Intersectable.GeoPoint(this, ray.getPoint(t1)));
            return  _points;
        }
        else if (t2 > 0) {
            List<GeoPoint> _points = new LinkedList<GeoPoint>();
            _points.add(new Intersectable.GeoPoint(this, ray.getPoint(t2)));
            return _points;
        }
        return null;
    }
}