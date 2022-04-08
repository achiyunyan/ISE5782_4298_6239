package renderer;

import java.util.MissingResourceException;

import primitives.*;

public class Camera {
    // Camera Fields
    private Point p0;
    private Vector vUp;
    private Vector vRight;
    private Vector vTo;

    // View Plane Fields
    private double distance;
    private double height;
    private double width;

    // Rendering fields
    private ImageWriter iWriter;
    private RayTracerBasic rTracerBasic;

    /**
     * Constructs a camera with location, to and up vectors.
     * The right vector is being calculated by the to and up vectors.
     *
     * @param p0  The camera's location.
     * @param vUp The direction of the camera's upper direction.
     * @param vTo The direction to where the camera is looking.
     * @throws IllegalArgumentException When to and up vectors aren't orthogonal.
     */
    public Camera(Point p0, Vector vUp, Vector vTo) {
        if (vUp.dotProduct(vTo) != 0)
            throw new IllegalArgumentException("Vectors are not vertical!");

        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Chaining method for setting the view plane's size.
     *
     * @param width  The new view plane's width.
     * @param height The new view plane's height.
     * @return The camera itself.
     * @throws IllegalArgumentException When distance illegal.
     */
    public Camera setVPSize(double width, double height) {
        if (width <= 0)
            throw new IllegalArgumentException("Illegal width");
        this.width = width;

        if (height <= 0)
            throw new IllegalArgumentException("Illegal height!");
        this.height = height;

        return this;
    }

    /**
     * Chaining method for setting the distance between the camera and the view
     * plane.
     *
     * @param distance The new distance between the camera and the view plane.
     * @return The camera itself.
     * @throws IllegalArgumentException When distance illegal.
     */
    public Camera setVPDistance(double distance) {
        if (distance <= 0)
            throw new IllegalArgumentException("Illegal distance!");

        this.distance = distance;
        return this;
    }

    /**
     * Chaining method for setting the imageWriter of the camera
     *
     * @param imageWriter
     * @return the camera itself
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.iWriter = imageWriter;
        return this;
    }

    /**
     * Chaining method for setting the rayTracer of the camera
     *
     * @param rayTracerBasic
     * @return the camera itself
     */
    public Camera setRayTracer(RayTracerBasic rayTracerBasic) {
        this.rTracerBasic = rayTracerBasic;
        return this;
    }

    /**
     * Render an image to the buffered image
     */
    public void renderImage() {
        if (iWriter == null)
            throw new MissingResourceException("Missing ImageWriter!", "ImageWriter", null);
        if (rTracerBasic == null)
            throw new MissingResourceException("Missing rayTracer!", "RayTracer", null);

        for (int i = 0; i < iWriter.getNx(); i++)
            for (int j = 0; j < iWriter.getNy(); j++)
                iWriter.writePixel(i, j, castRay(i, j));
    }

    /**
     * returns the color of the ray
     * @param i
     * @param j
     * @return the color of the ray
     */
    public Color castRay(int i, int j) {
        Ray ray = constructRay(iWriter.getNx(), iWriter.getNy(), i, j);
        return rTracerBasic.traceRay(ray);
    }

    /**
     * @param interval the space between grids
     * @param color    the color of the lines
     */
    public void printGrid(int interval, Color color) {
        if (iWriter == null)
            throw new MissingResourceException("Missing ImageWriter!", "ImageWriter", null);

        for (int i = 0; i < iWriter.getNx(); i++)
            for (int j = 0; j < iWriter.getNy(); j++)
                if (i % interval == 0 || j % interval == 0)
                    iWriter.writePixel(i, j, color);
    }

    /**
     * writes the image
     */
    public void writeToImage() {
        if (iWriter == null)
            throw new MissingResourceException("Missing ImageWriter!", "ImageWriter", null);

        iWriter.writeToImage();
    }

    /**
     * The function calculate the center point of the pixel.
     *
     * @param nX Total number of pixels in the x dimension.
     * @param nY Total number of pixels in the y dimension.
     * @param j  The index of the pixel on the x dimension.
     * @param i  The index of the pixel on the y dimension.
     * @return the center point in the pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point Pc = p0.add(vTo.scale(distance));

        double Rx = Util.alignZero(width / nX);
        double Ry = Util.alignZero(height / nY);

        double xJ = Util.alignZero((j - (double) (nX - 1) / 2) * Rx);
        double yI = Util.alignZero(-(i - (double) (nY - 1) / 2) * Ry);

        Point pIJ = Pc;
        if (xJ != 0)
            pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0)
            pIJ = pIJ.add(vUp.scale(yI));

        Vector vIJ = pIJ.subtract(p0);

        return new Ray(p0, vIJ);
    }

    public double getDistance() {
        return distance;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}