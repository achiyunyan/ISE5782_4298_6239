package lighting;

import primitives.*;

public class SpotLight extends PointLight {

    private Vector direction;

    /**
     * Ctor for SpotLight
     * 
     * @param intensity
     * @param position
     * @param direction
     */
    protected SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(Math.max(0, getL(p).dotProduct(direction)));
    }

}
