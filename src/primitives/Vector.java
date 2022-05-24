package primitives;

public class Vector extends Point {
    /**
     * Constructor for the class Vector.
     * Gets three double cordinates and creates a new vector.
     * Throw exception if the zero vector was inserted,
     * so we wont have any zero vectors from creation
     * 
     * @param x
     * @param y
     * @param z
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (Util.isZero(xyz.d1) && Util.isZero(xyz.d2) && Util.isZero(xyz.d3))
            throw new IllegalArgumentException("the zero vector is illegal!");
    }

    /**
     * Constructor for the inner functions
     * 
     * @param xyz
     */
    private Vector(Double3 xyz) {
        super(xyz.d1, xyz.d2, xyz.d3);
        if (Util.isZero(this.xyz.d1) && Util.isZero(this.xyz.d2) && Util.isZero(this.xyz.d3))
            throw new IllegalArgumentException("the zero vector is illegal!");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector))
            return false;

        Point other = (Point) obj;
        return super.equals(other);
    }

    @Override
    public String toString() {
        return "->" + super.toString();
    }

    /**
     * Returns a new vector which is the subtruct between the calling vector to the
     * param 'vector'.
     * 
     * @param vector
     * @return Vector
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Returns a new vector which is the subtruct between the calling vector to the
     * param 'vector'.
     * 
     * @param vector
     * @return Vector
     */
    public Vector subtract(Vector vector) {
        return new Vector(xyz.subtract(vector.xyz));
    }

    /**
     * Returns a new vector which is the original scaled by the scalar
     * 
     * @param scalar
     * @return Vector
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Returns the dot product between the calling vector to the param 'vector'.
     * Calculated by the summary of the multipications of the cordinates in the
     * vectors respectively
     * 
     * @param vector
     * @return double
     */
    public double dotProduct(Vector vector) {
        return xyz.d1 * vector.xyz.d1 + xyz.d2 * vector.xyz.d2 + xyz.d3 * vector.xyz.d3;

    }

    /**
     * Returns the cross product between the calling vector to the param 'vector'.
     * Calculated by the linear algebra formula
     * 
     * @param vector
     * @return
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                this.xyz.d2 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d2,
                this.xyz.d3 * vector.xyz.d1 - this.xyz.d1 * vector.xyz.d3,
                this.xyz.d1 * vector.xyz.d2 - this.xyz.d2 * vector.xyz.d1);
    }

    /**
     * Returns the squared length of the vector
     * 
     * @return double
     */
    public double lengthSquared() {
        double xDis = xyz.d1 * xyz.d1;
        double yDis = xyz.d2 * xyz.d2;
        double zDis = xyz.d3 * xyz.d3;
        return xDis + yDis + zDis;
    }

    /**
     * Returns the length of the vector
     * Returns the sqare root of the 'lengthSqared' function
     * 
     * @return double
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns a new vector which is the original vector divided by its length
     * 
     * @return Vector
     */
    public Vector normalize() {
        return this.scale(1 / this.length()); // the scale operation will return a new vector
    }

    /**
     * Rotate The Vector around the axis by an angle
     * 
     * @param axis  The axis
     * @param angle The angle of rotation
     * @return The rotated Vector
     */
    public Vector rotate(Vector axis, double angle) {

        angle = angle / 180 * Math.PI;
        double cos = Math.cos(angle), sin = Math.sin(angle);
        double x = axis.xyz.d1, y = axis.xyz.d2, z = axis.xyz.d3;

        Vector v = new Vector(
                (cos + Math.pow(x, 2) * (1 - cos)) * this.xyz.d1 + (x * y * (1 - cos) - z * sin) * this.xyz.d2
                        + (x * z * (1 - cos) + y * sin) * this.xyz.d3,
                (y * x * (1 - cos) + z * sin) * this.xyz.d1 + (cos + Math.pow(y, 2) * (1 - cos)) * this.xyz.d2
                        + (y * z * (1 - cos) - x * sin) * this.xyz.d3,
                (z * x * (1 - cos) - y * sin) * this.xyz.d1 + (z * y * (1 - cos) + x * sin) * this.xyz.d2
                        + (cos + Math.pow(z, 2) * (1 - cos)) * this.xyz.d3)
                .normalize();

        return v;
    }

    /**
     * 
     * @return a normal vector to the caller
     */
    public Vector findNormal() {
        if (Util.alignZero(xyz.d3) != 0) {
            Vector vz = new Vector(1, 0, -xyz.d1 / xyz.d3);
            if (vz.equals(this))
                return new Vector(1, 1, -(xyz.d1 + xyz.d2) / xyz.d3);
            else
                return vz;
        } else {
            if (Util.alignZero(xyz.d2) != 0) {
                Vector v = new Vector(1, -xyz.d1 / xyz.d2, 1);
                if (v.equals(this))
                    return new Vector(1, -xyz.d1 / xyz.d2, 0);
                else
                    return v;
            } else
                return new Vector(0, 1, 0);

        }
    }
}