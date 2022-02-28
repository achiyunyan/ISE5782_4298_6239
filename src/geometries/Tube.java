package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Tube implements Geometry {
    protected Ray axisRay;
    protected double radius;

    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        if(radius<0)
            throw new IllegalArgumentException("Radius can't be negative");
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point point) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return "axisRay = " + axisRay + ", radius = " + radius;
    }
}