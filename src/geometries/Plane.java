package geometries;

import primitives.*;

public class Plane {
    private Point q0;
    private Vector normal;

    /**
     * 
     * Assumptions : normal is not the zero vector
     * 
     * @param q0
     * @param normal
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    public Plane(Point q0, Point q1, Point q2) {
        this.q0 = q0;
        this.normal = null;
    }

    @Override
    public String toString() {
        return "normal= " + normal + ", q0= " + q0;
    }

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