package lighting;

import primitives.Color;

/**
 * Light Class
 */
abstract class Light {
    private Color intensity;

    /**
     * Default Ctor
     * @param intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Getter for intensity
     * @return intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}