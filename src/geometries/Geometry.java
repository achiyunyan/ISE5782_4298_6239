package geometries;

import primitives.*;

public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * Getter for material
     * @return
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Setter for material (Builder type)
     * @param material
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

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