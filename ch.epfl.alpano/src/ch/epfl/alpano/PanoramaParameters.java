package ch.epfl.alpano;

import static java.util.Objects.requireNonNull;

/**
 * This class is here to represent parameters needed for a Panorama
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */
public final class PanoramaParameters {

    private final GeoPoint observerPosition;
    private final int observerElevation;
    private final double centerAzimuth;
    private final double horizontalFieldOfView;
    private final int maxDistance;
    private final int width;
    private final int height;
    private final double verticalFieldOfView;
    private final double delta;

    /**
     * A constructor for PanoramaParameters using as parameters a GeoPoint, 4
     * ints and 2 doubles
     * 
     * @param observerPosition
     *            a GeoPoint corresponding to the observer's position 
     * @param observerElevation
     *            an int corresponding to the observer's elevation in meters
     * @param centerAzimuth
     *            the azimuth of the center of the panorama (in radians)
     * @param horizontalFieldOfView
     *            the horizontal field of view for the panorama (in radians)
     * @param maxDistance
     *            the maximal distance of visibility (in meters)
     * @param width
     *            the width of the panorama 
     * @param height
     *            the height of the panorama
     */
    public PanoramaParameters(GeoPoint observerPosition, int observerElevation,
            double centerAzimuth, double horizontalFieldOfView, int maxDistance,
            int width, int height) {

        this.observerPosition = requireNonNull(observerPosition);
        this.observerElevation = observerElevation;
        this.centerAzimuth = centerAzimuth;
        this.horizontalFieldOfView = horizontalFieldOfView;
        this.maxDistance = maxDistance;
        this.width = width;
        this.height = height;

        Preconditions.checkArgument(Azimuth.isCanonical(centerAzimuth),
                "centerAzimuth is not canonical");
        Preconditions.checkArgument(
                horizontalFieldOfView > 0 && horizontalFieldOfView <= Math2.PI2,
                "horizontalFieldOfView is not in the interval ]0,2*pi[");
        Preconditions.checkArgument(width > 0 && width != 1,
                "width is not strictly positive");
        Preconditions.checkArgument(height > 0 && height != 1,
                "height is not strictly positive");
        Preconditions.checkArgument(maxDistance > 0,
                "maxDistance is not strictly positive");

        verticalFieldOfView = horizontalFieldOfView * (height - 1)
                / (width - 1);

        delta = horizontalFieldOfView() / (width - 1);
    }

    /**
     * A getter for the observerPosition in the parameters
     * 
     * @return the GeoPoint used for the construction of this PanoramaParameters
     */

    public GeoPoint observerPosition() {
        return observerPosition;
    }

    /**
     * A getter for the observerElevation in the parameters
     * 
     * @return the observer's Elevation for the panorama
     */
    public int observerElevation() {
        return observerElevation;
    }

    /**
     * A getter for the centerAzimuth in the parameters
     * 
     * @return the azimuth of the center of the panorama
     */
    public double centerAzimuth() {
        return centerAzimuth;
    }

    /**
     * A getter for the horizontalFieldOfView in the parameters
     * 
     * @return the horizontal angle of view of the panorama (in radians)
     */
    public double horizontalFieldOfView() {
        return horizontalFieldOfView;
    }

    /**
     * A getter for the maxDistance in the parameters
     * 
     * @return the maximal distance of visibility desired for the panorama (in
     *         meters)
     */
    public int maxDistance() {
        return maxDistance;
    }

    /**
     * A getter for the width in the parameters
     * 
     * @return the width desired for the image of the panorama (the width of the
     *         array of pixels)
     */
    public int width() {
        return width;
    }

    /**
     * A getter for the height in the parameters
     * 
     * @return the height desired for the image of the panorama ( the height of
     *         the array of pixels)
     */
    public int height() {
        return height;
    }

    /**
     * A getter for the verticalFieldOfView
     * 
     * @return the verticalFieldOfVue calculated from the horizontalFieldOfView
     *         and the size of the panorama
     */
    public double verticalFieldOfView() {
        return verticalFieldOfView;
    }

    /**
     * Calculates the azimuth corresponding to the index x
     * 
     * @param x
     *            the index of a horizontal pixel in the image
     * @return the azimuth corresponding to the index x throws
     *         IllegalArgumentException if the index for the image is
     *         invalid(negative or bigger than width-1)
     */
    public double azimuthForX(double x) {
        Preconditions.checkArgument(((x >= 0) && (x <= width - 1)),
                "x must be positive and smaller or equal than width-1");

        return Azimuth.canonicalize(centerAzimuth() + ((x - widthCenter())
                * (horizontalFieldOfView() / (width() - 1))));

    }

    /**
     * Calculates the index of the horizontal pixel corresponding to the azimuth
     * a
     * 
     * @param a
     *            an azimuth given in the visible range throws
     *            IllegalArgumentException if the azimuth is not in the visible
     *            range
     * @return the index of the horizontal pixel corresponding to the azimuth a
     */
    public double xForAzimuth(double a) {

        double dist = Math2.angularDistance(a, centerAzimuth());

        Preconditions.checkArgument(
                Math.abs(dist) <= horizontalFieldOfView() / 2d,
                "The azimuth is not in the visible range");
        return (-(dist / delta) + widthCenter());

    }

    /**
     * Calculates the elevation corresponding to the index y
     * 
     * @param y
     *            an index of a vertical pixel in the image Throws
     *            IllegalArgumentException if the index for the image is
     *            invalid(negative or bigger than height-1)
     * @return the elevation corresponding to the index y
     */
    public double altitudeForY(double y) {

        Preconditions.checkArgument((y >= 0 && y <= height() - 1),
                "y must be positive and smaller thant height -1");

        return ((heightCenter() - y) * delta);

    }

    /**
     * Calculates the index of the vertical pixel corresponding to the elevation
     * 
     * 
     * @param a
     *            an elevation given in the visible range Throws
     *            IllegalArgumentException if the elevation is not in the
     *            visible range
     * @return the index of the vertical pixel corresponding to the elevation a
     */
    public double yForAltitude(double a) {

        Preconditions.checkArgument(Math.abs(a) <= verticalFieldOfView() / 2d,
                "the altitude is not in the visible range");

        return (heightCenter() - (a / delta));

    }

    /**
     * Determines if the index (x,y) is valid
     * 
     * @param x
     *            the index corresponding to the row in the image
     * @param y
     *            the index corresponding to the column in the image
     * @return if the index corresponding is in the image [width]X[height]
     */

    boolean isValidSampleIndex(int x, int y) {
        return ((x >= 0 && x < width) && (y >= 0 && y < height));
    }

    /**
     * Gives the linearSampleIndex corresponding to (x,y)
     * 
     * @param x
     *            the index corresponding to the row in the image
     * @param y
     *            the index corresponding to the column in the image
     * @return the index corresponding if the image was a 1-D array
     */
    int linearSampleIndex(int x, int y) {
        Preconditions.checkArgument(isValidSampleIndex(x,y), "Not Valid Sample Index");
        return width * y + x;
    }

    /**
     * @return the pixel's width index corresponding to the horizontal center of
     *         the image
     */
    private double widthCenter() {

        return (width() - 1) / 2d;

    }

    /**
     * @return the pixel's height index corresponding to the vertical center of
     *         the image
     */
    private double heightCenter() {

        return (height() - 1) / 2d;

    }

}
