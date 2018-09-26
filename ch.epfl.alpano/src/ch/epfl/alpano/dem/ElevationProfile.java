package ch.epfl.alpano.dem;

import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.Math2;

import static java.util.Objects.requireNonNull;

import ch.epfl.alpano.Azimuth;
import ch.epfl.alpano.Distance;
import ch.epfl.alpano.Preconditions;

/**
 * This class is here to represent an elevation profile
 * 
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */
public final class ElevationProfile {

    private final ContinuousElevationModel elevationModel;
    private final double length;
    private final double[][] array;
    private final static double STEP = Math.pow(2, 12);

    /**
     * A constructor for ElevationProfile that takes as arguments a
     * ContinuousElevationModel, a GeoPoint, and 2 doubles
     * 
     * @param elevationModel
     *            a ContinuousElevationModel
     * @param origin
     *            the GeoPoint from where our ElevationProfile begins
     * @param azimuth
     *            the direction from which the profile is drawn following the
     *            big circle in that direction
     * @param length
     *            a length in meters corresponding to the length of the drawing
     *            of the elevation profile throws IllegalArgumentException if
     *            the azimuth is not canonical throws IllegalArgumentException
     *            if the length is not strictly positive throws
     *            NullPointerException if elevationModel or the origin are null
     */
    public ElevationProfile(ContinuousElevationModel elevationModel,
            GeoPoint origin, double azimuth, double length) {
        
        Preconditions.checkArgument(Azimuth.isCanonical(azimuth),
                "Azimuth is not canonical");
        this.length = length;
        Preconditions.checkArgument(length > 0,
                "length is not strictly positive");

        this.elevationModel = requireNonNull(elevationModel);
        origin = requireNonNull(origin);

        double number = Math.ceil(length / STEP);
        array = new double[(int) (number + 1)][2];
        for (int i = 0; i <= number; ++i) {
             double index = Math.scalb(i, 12);
             GeoPoint point = bigCircle(index,azimuth,origin);
            array[i][0] = point.longitude();
            array[i][1] = point.latitude();
        }
        // We build an array with : on the first column, the index of each point
        // needed separated by the step
        // on the second column, each the longitudes of the points corresponding
        // to the x's of the first column
        // on the third column, each the latitudes of the points corresponding
        // to the x's of the first column
        // We need it for the optimization, to calculate the positions of some
        // points on a big circle, then to do faster interpolations
    }

    /**
     * Returns the elevation at the point x of the profile
     * 
     * @param x
     *            the distance from the origin in meters in the direction of the
     *            azimuth
     * @return the height of the point obtained by positionAt(x) throws
     *         IllegalArgumentException if x is not in the profile's range
     */
    public double elevationAt(double x) {
        Preconditions.checkArgument((x >= 0 && x <= length),
                "This position is not in the profile's range");
        GeoPoint p = positionAt(x);
        return elevationModel.elevationAt(p);

    }

    /**
     * Returns the position of the point x of the profile
     * 
     * @param x
     *            the distance from the origin in meters in the direction of the
     *            azimuth
     * @return the position of that point (a GeoPoint), by interpolation of two
     *         longitudes and latitudes calculated during the construction of
     *         the profile throws IllegalArgumentException if x is not in the
     *         profile's range
     */
    public GeoPoint positionAt(double x) {
        Preconditions.checkArgument((x >= 0 && x <= length),
                "This position is not in the profile's range");
        int xIndex = (int) Math.floor(x / STEP);
        double minLongitude = array[xIndex][0];
        double maxLongitude = array[xIndex + 1][0];
        double minLatitude = array[xIndex][1];
        double maxLatitude = array[xIndex + 1][1];
        double xInterpol = (x / STEP) - xIndex;

        double longitude = Math2.lerp(minLongitude, maxLongitude, xInterpol);
        double latitude = Math2.lerp(minLatitude, maxLatitude, xInterpol);
        return new GeoPoint(longitude, latitude);
        // We did a linear interpolation for longitudes and latitudes to get the
        // coordinates of our point at position x
    }

    /**
     * Returns the slope of the point x of the profile
     * 
     * @param x
     *            the distance from the origin in meters in the direction of the
     *            azimuth
     * @return the slope of the point obtained with positionAt(x) throws
     *         IllegalArgumentException if x is not in the profile's range
     */
    public double slopeAt(double x) {
        Preconditions.checkArgument((x >= 0 && x <= length),
                "This position is not in the profile's range");
        GeoPoint p = positionAt(x);
        return elevationModel.slopeAt(p);
    }

    /**
     * Given a profile,calculates the position of a point at a distance of x
     * radians of the origin, in the direction of the azimuth on a arc of big
     * circle
     * 
     * @param x
     *            the distance from the origin in meters in the direction of the
     *            azimuth
     * @return the geographic coordinates (a GeoPoint) corresponding to the
     *         point of distance x from the origin in the direction of the
     *         azimuth following the corresponding big circle
     */
    private GeoPoint bigCircle(double x , double azimuth, GeoPoint origin) {
        double alpha = Azimuth.toMath(azimuth);
        double xRad = Distance.toRadians(x);
        double phiO = origin.latitude();
        double lambdaO = origin.longitude();
        double phi = Math.asin(Math.sin(phiO) * Math.cos(xRad)
                + Math.cos(phiO) * Math.sin(xRad) * Math.cos(alpha));
        double lambda = Math2.floorMod(lambdaO
                - Math.asin((Math.sin(alpha) * Math.sin(xRad)) / Math.cos(phi))
                + Math.PI, Math2.PI2) - Math.PI;
        return new GeoPoint(lambda, phi);
        // we simply apply the formulas given here
    }

}
