package geometries;

import primitives.*;

public class Plane implements Geometry {
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
        this.normal = null;
    }

    @Override
    public String toString() {
        return "normal = " + normal + ", q0 = " + q0;
    }

    /**
     * Returns the Normal to a given Point
     */
    public Vector getNormal(Point point) {
        return null;
    }

    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }
}