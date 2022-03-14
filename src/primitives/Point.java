package primitives;

public class Point {
    final protected Double3 xyz;

    /**
     * Constructor for the class 'Point'.
     * Gets three double cordinates and create a new Point
     * 
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructor for the class 'Point'.
     * Gets a Double3 parameter and create a new Point
     * 
     * @param xyz
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Point other = (Point) obj;
        return other.xyz.equals(xyz);
    }

    @Override
    public String toString() {
        return xyz.toString();
    }

    /**
     * Returns the result of the addition of the param 'vector' to the calling Point
     * 
     * @param vector
     * @return point
     */
    public Point add(Vector vector) {
        Double3 add = xyz.add(vector.xyz);
        return new Point(add.d1, add.d2, add.d3);
    }

    /**
     * Returns the result of the subtract of the param 'vector' from the calling
     * Point
     * 
     * @param vector
     * @return Vector
     */
    public Vector subtract(Point point) {
        Double3 sub = xyz.subtract(point.xyz);
        return new Vector(sub.d1, sub.d2, sub.d3);
    }

    /**
     * Returns the squared distance between the calling point to the param 'point'.
     * Calculated by the pythagorean sentence
     * 
     * @param point
     * @return
     */
    public double distanceSquared(Point point) {
        double xDis = xyz.d1 - point.xyz.d1;
        double yDis = xyz.d2 - point.xyz.d2;
        double zDis = xyz.d3 - point.xyz.d3;
        return xDis * xDis + yDis * yDis + zDis * zDis;
    }

    /**
     * Returns the distance between the calling point to the param 'point'.
     * Returns the sqare root of the 'distanceSqared' function
     * 
     * @return double
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }
}