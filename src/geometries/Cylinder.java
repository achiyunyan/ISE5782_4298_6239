package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube {
    private double height;

    /**
     * Constructor for 'Cylinder'
     * 
     * @param axisRay 
     * @param radius
     * @param height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + ", height = " + height;
    }

    public double getHeight() {
        return height;
    }
}