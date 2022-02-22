package primitives;

public class Ray {
    private Point p0;
    private Vector dir;

    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize() ;
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
        if (dir == null) {
            if (other.dir != null)
                return false;
        } else if (!dir.equals(other.dir))
            return false;
        if (p0 == null) {
            if (other.p0 != null)
                return false;
        } else if (!p0.equals(other.p0))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return  "p0= " +p0.toString()+"dir= " +dir.toString();
    }

    public Point getP0() {
        return p0;
    }

    public void setP0(Point p0) {
        this.p0 = p0;
    }

    public Vector getDir() {
        return dir;
    }

    public void setDir(Vector dir) {
        this.dir = dir;
    }
    
    
    
    

}
