package primitives;

public class Vector extends Point {
    
    public Vector(Double3 xyz) {
        super(xyz);
        if (Util.isZero(xyz.d1) && Util.isZero(xyz.d2) && Util.isZero(xyz.d3))
            throw new IllegalArgumentException();
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
        xyz.add(vector.xyz);
        return this;
    }
}