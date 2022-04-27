package lighting;

import primitives.*;

public class SpotLight extends PointLight {

    private Vector direction;
    private double narrowness = 1;

    /**
     * Ctor for SpotLight
     * 
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Calculate the color intetnsity the spotlight hits the material
     * New Feature Available! NarrowBeam Option!!!
     * 
     * @param p
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(Math.pow(Math.max(0, getL(p).dotProduct(direction)), narrowness));
    }

    public PointLight setNarrowBeam(double narrowness) {
        this.narrowness = narrowness;
        return this;
    }

}
