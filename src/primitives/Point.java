package primitives;

public class Point {
    final protected Double3 x;
    final protected Double3 y;
    final protected Double3 z;

    public Point(Double3 x, Double3 y, Double3 z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
        if (x == null) {
            if (other.x != null)
                return false;
        } else if (!x.equals(other.x))
            return false;
        if (y == null) {
            if (other.y != null)
                return false;
        } else if (!y.equals(other.y))
            return false;
        if (z == null) {
            if (other.z != null)
                return false;
        } else if (!z.equals(other.z))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

    public Point add(Vector vector) {
        x.add(vector.x);
        y.add(vector.y);
        z.add(vector.z);
        return this;
    }

    public Vector subtruct(Point point) {
        return new Vector(point.x - x, point.y -y, point.z - z);
    }

    
}