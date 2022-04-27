package lighting;

import primitives.*;

public class AmbientLight extends Light {
    /**
     * Default Constructor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * Constructor with 2 parameters
     * 
     * @param iA Original Color
     * @param kA Coefficient
     */
    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }
}