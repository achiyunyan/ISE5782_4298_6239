package lighting;

import primitives.*;

public class AmbientLight {
    Color intensity;

    /**
     * Default Constructor
     */
    public AmbientLight() {
        intensity = Color.BLACK;
    }

    /**
     * Constructor with 2 parameters
     * 
     * @param iA Original Color
     * @param kA 
     */
    public AmbientLight(Color iA, Double3 kA) {
        intensity = iA.scale(kA);
    }

    public Color getIntensity() {
        return intensity;
    }
}