package geometries;

import primitives.*;

public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;

    /**
     * Getter for emission
     * @return
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Setter for emission (Builder type)
     * @param emission
     * @return
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public abstract Vector getNormal(Point point);
}