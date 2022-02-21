package primitives;

public class Point {
    final protected Double3 xyz;

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
        other.xyz.equals(xyz);
        return true;
    }

    @Override
    public String toString() {
        return xyz.toString();
    }

    public Point add(Vector vector) {
        xyz.add(vector.xyz);
        return this;
    }

    public Vector subtruct(Point point) {
        return new Vector(point.xyz.subtract(xyz));
    }
}