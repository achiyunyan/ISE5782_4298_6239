package geometries;

import java.util.*;

import primitives.*;
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

        // Create Box
        Point p0 = new Point(axisRay.getP0().getX() - radius, axisRay.getP0().getY() - radius, axisRay.getP0().getZ() - radius);
        Point center = axisRay.getP0().add(axisRay.getDir().scale(height));
        Point p1 = new Point(center.getX() + radius, center.getY() + radius, center.getZ() + radius);
        this.box = new Box(p0, p1);
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
        double distanceBase1 = point.distance(axisRay.getP0());
        Vector axisVec = axisRay.getDir();

        // the point is P0
        if (Util.isZero(distanceBase1))
            return axisRay.getDir().scale(-1).normalize();
        Point centerBase2 = axisRay.getP0().add(axisVec.scale(height));
        double distanceBase2 = centerBase2.distance(point);
        // point is on the center of the far side from P0
        if (Util.isZero(distanceBase2))
            return axisVec.normalize();
        // point is on the close side to P0
        else if (distanceBase1 < radius && distanceBase2 > distanceBase1)
            return axisVec.scale(-1).normalize();
        // point is on the far side from P0
        else if (distanceBase2 < radius && distanceBase1 > distanceBase2)
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

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double max) {
        List<GeoPoint> res = new ArrayList<>();
        List<GeoPoint> lst = super.findGeoIntersectionsHelper(ray,max);
        if (lst != null)
            for (GeoPoint geoPoint : lst) {
                double distance = Util.alignZero(geoPoint.point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir()));
                if (distance > 0 && distance <= height)
                    res.add(geoPoint);
            }

        if (res.size() == 0)
            return null;
        return res;
    }
}