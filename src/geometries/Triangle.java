package geometries;

import primitives.Point;

public class Triangle extends Polygon {
    /**
     * Constructor for the class 'Triangle'
     * Recives 3 points and send them to the ctor of 'Polygon'
     * 
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public String toString() {
        return "P1: " + vertices.get(0).toString() + "P2: " + vertices.get(1).toString() + "P3: "
                + vertices.get(2).toString();
    }
}