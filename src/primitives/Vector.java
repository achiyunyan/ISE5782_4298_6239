package primitives;

public class Vector extends Point {
    public Vector(Double3 x, Double3 y, Double3 z) {
        if (x.IsZero() && y.IsZero() && z.IsZero())
            throw new IlegalArgumentException();
        super(x, y, z);
    }
    
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point other = (Point)obj;
        return super.equals(other);
    }

    @Override
    public String toString() { 
        return "->" + super.toString(); 
    }

    public Vector add(Vector vector) {
        x.add(vector.x);
        y.add(vector.y);
        z.add(vector.z);
        return this;
    }
}