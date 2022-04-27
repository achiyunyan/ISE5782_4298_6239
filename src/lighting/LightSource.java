package lighting;

import primitives.*;

/**
 * LightSource Interface
 */
public interface LightSource {
    /**
     * Returns the intensity in a given Point
     * @param p The Point
     * @return The intensity
     */
    public Color getIntensity(Point p);

    /**
     * Returns the Vector L
     * @param p The Point
     * @return The Vector L
     */
    public Vector getL(Point p);
}