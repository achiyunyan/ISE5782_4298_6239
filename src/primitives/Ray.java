package primitives;

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
        return this.dir.equals(other.dir)&&this.p0.equals(other.p0);
    }

    @Override
    public String toString() {
        return "p0= " + p0.toString() + "dir= " + dir.toString();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }
}