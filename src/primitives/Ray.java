package primitives;

import java.util.List;

public class Ray {
    private Point p0;
    private Vector dir;

    /**
     * Construcor for 'Ray'
     * 
     * @param p0
     * @param dir
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Ray other = (Ray) obj;
        return this.dir.equals(other.dir) && this.p0.equals(other.p0);
    }

    @Override
    public String toString() {
        return "p0= " + p0.toString() + "dir= " + dir.toString();
    }
    /**
     * Return the closest point to the 'p0' of the ray
     * 
     * @param list list of points
     * @return the closest point to the 'p0' of the ray
     */
    public Point findClosestPoint(List<Point> list) {
        if (list == null)
            return null;
        if (list.size() == 1)
            return list.get(0);

        int closest = 0;
        double distanceFromClosest = list.get(0).distance(p0);
        double x;
        for (int i = 1; i < list.size(); i++) {
            x = list.get(i).distance(p0);
            if (x < distanceFromClosest) {
                closest = i;
                distanceFromClosest = x;
            }
        }
        return list.get(closest);
    }

    /**
     * Returns a point on a ray by the given scalar
     * 
     * @param t1
     * @return
     */
    public Point getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }
}