package primitives;

public class Material {
    public Double3 kD = Double3.ZERO, kS = Double3.ZERO;
    public Double3 kT = Double3.ZERO, kR = Double3.ZERO;
    /** transperancy factor and reflection factor */
    public int nShininess = 0;

    /**
     * Setter for kD with Double3
     * 
     * @param kD
     * @return
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter for kD with double
     * 
     * @param kD
     * @return
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Setter for kS with Double3
     * 
     * @param kS
     * @return
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for kS with double
     * 
     * @param kS
     * @return
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }
    
    /**
     * Setter for kT with Double3
     * 
     * @param kT
     * @return
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }


    /**
     * Setter for kT with double
     * 
     * @param kT
     * @return
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

        /**
     * Setter for kR with Double3
     * 
     * @param kR
     * @return
     */
    public Material setKr(Double3 kR) {
        this.kT = kR;
        return this;
    }


    /**
     * Setter for kR with double
     * 
     * @param kR
     * @return
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * Setter for nShininess
     * 
     * @param nShininess
     * @return
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}