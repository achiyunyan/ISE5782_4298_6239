package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
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

    /**
     * Returns the Normal of the Cylinder to a given Point.
     * 
     * @param point
     * 
     * @return Vector
     */
    @Override
    public Vector getNormal(Point point) {
        double distance = point.distance(axisRay.getP0());
        Vector axisVec = axisRay.getDir();

        // the point is P0
        if (Util.isZero(distance))
            return axisRay.getDir().scale(-1).normalize();
        Vector toP0 = point.subtract(axisRay.getP0());
        // point is on the center of the far side from P0
        if (Util.isZero(distance - height) && Util.isZero(axisVec.dotProduct(toP0) - axisVec.length() * toP0.length()))
            return axisVec.normalize();
        else if (Util.isZero(axisVec.dotProduct(point.subtract(axisRay.getP0()))))
            return axisVec.scale(-1).normalize();
        else if (Util.isZero(axisVec.dotProduct(point.subtract(axisRay.getP0().add(axisVec.scale(height))))))
            return axisVec.normalize();
        else {
            double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));
            Point O = axisRay.getP0().add(axisRay.getDir().scale(t));
            Vector v = point.subtract(O);
            return v.normalize();
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", height = " + height;
    }

    public double getHeight() {
        return height;
    }
}