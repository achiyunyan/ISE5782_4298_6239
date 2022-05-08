package geometries;

import java.util.List;
import primitives.*;
import static primitives.Util.*;

public class Plane extends Geometry {
    private Point q0;
    private Vector normal;

    /**
     * Constructor for 'Plane'. Gets 1 Point and 1 Vector
     * Assumptions : normal is not the zero vector
     * 
     * @param q0
     * @param normal
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    /**
     * Constructor for 'Plane'. Gets 3 Points
     * 
     * @param q0
     * @param q1
     * @param q2
     */
    public Plane(Point q0, Point q1, Point q2) {
        this.q0 = q0;
        Vector v1 = q0.subtract(q1);
        Vector v2 = q0.subtract(q2);
        Vector v3 = v1.crossProduct(v2);
        this.normal = v3.normalize();
    }

    @Override
    public String toString() {
        return "normal = " + normal + ", q0 = " + q0;
    }

    /**
     * Returns the Normal of the Plane to a given Point.
     * 
     * @param point
     * 
     * @return Vector
     */
    public Vector getNormal(Point point) {
        return normal;
    }

    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    /**
     * Return the intersections between the plane and the ray
     * can be 1 , or 0 .
     * 
     * @param ray
     * 
     * @return List<GeoPoint>
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        double nv = normal.dotProduct(ray.getDir());
        if (isZero(nv))
            return null;

        double t = alignZero(normal.dotProduct(q0.subtract(ray.getP0())) / nv);
        if (alignZero(t - maxDistance) > 0)
            return null;
        if (t > 0) {
            Point intersect = ray.getPoint(t);
            List<GeoPoint> inter = List.of(new GeoPoint(this, intersect));
            return inter;
        }
        
        return null;
    }
}