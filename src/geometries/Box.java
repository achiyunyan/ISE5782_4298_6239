package geometries;

import primitives.Double3;
import primitives.Ray;

public class Box {
    Double3 p0;
    Double3 p1;

    public Box(Double3 p0, Double3 p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    Boolean isIntersection(Ray ray) {
        
    }
}