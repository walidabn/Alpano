package ch.epfl.alpano;

import ch.epfl.alpano.dem.ContinuousElevationModel;
import static ch.epfl.alpano.Panorama.Builder;
import static java.util.Objects.requireNonNull;
import java.util.function.DoubleUnaryOperator;

import ch.epfl.alpano.dem.*;

/**
 * This class is here to compute a Panorama from its parameters
 * 
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */
public final class PanoramaComputer {
    private final ContinuousElevationModel dem;
    private final static double REFRACTION = 0.13;
    private final static int FIRST_STEP = 64;
    private final static int IMPROVE_STEP = 4;
    private final static double REFRACTION_OVER_EARTH_RADIUS = ((1 - REFRACTION) / (2 * Distance.EARTH_RADIUS));

    /**
     * A constructor for PanoramaComputer taking as argument a
     * ContinuousElevationModel
     * 
     * @param dem
     *            a continuous elevation Model used for the computation of the
     *            Panorama
     */
    public PanoramaComputer(ContinuousElevationModel dem) {
        this.dem = requireNonNull(dem);
    }

    /**
     * Computes a Panorama from its parameters
     * 
     * @param parameters
     *            the parameters of the Panorama
     * @return a Panorama computed from its parameters
     */
    public Panorama computePanorama(PanoramaParameters parameters) {
        Builder builder = new Panorama.Builder(parameters);
        ElevationProfile profile;
        DoubleUnaryOperator f;
        double ray = 0;
        double x0 = 0;
        double x;
        for (int i = 0; i < parameters.width(); ++i) {
            profile = new ElevationProfile(dem, parameters.observerPosition(),
                    parameters.azimuthForX(i), parameters.maxDistance());
            // We build an elevation profile for each column of pixels here
            x0=0;

            // For each new column, we do a reset of x0 for the optimization
            
            for (int j = parameters.height() - 1; j >= 0; --j) {

                double rayAngle = parameters.altitudeForY(j);

                f = rayToGroundDistance(profile, parameters.observerElevation(),
                        Math.tan(rayAngle));

                // We shoot a ray on each point of the column, starting from the
                // bottom of it
               

                ray = Math2.firstIntervalContainingRoot(f, x0,
                        parameters.maxDistance(), FIRST_STEP);

                if (ray == Double.POSITIVE_INFINITY) {
                    break;
                }
                // We know that if ray is equal to POSITIVE INFINITY, it means
                // that no root has been found, and no other root will be found
                // higher, so we can go to the next elevation Profile/Column of
                // pixel

                x0 = Math2.improveRoot(f, ray, ray + FIRST_STEP, IMPROVE_STEP);

                x = x0 / Math.cos(rayAngle);
                // Given an angle and a side of a triangle, we can find the
                // hypothenuse of it (which is the distance from the observer's
                // to the root x0) with this trigonometric function
                
                GeoPoint point = profile.positionAt(x0);
                
                builder = builder.setDistanceAt(i, j, (float) x)
                        .setElevationAt(i, j, (float) profile.elevationAt(x0))
                        .setLatitudeAt(i, j,
                                (float) point.latitude())
                        .setLongitudeAt(i, j,
                                (float) point.longitude())
                        .setSlopeAt(i, j, (float) profile.slopeAt(x0));

            }

        }
        // The panorama is fully prepared after shooting a ray on all the points of the future image, we can now build it
        return builder.build();
        
    }

    /**
     * The function corresponding to the ray tracing
     * 
     * @param profile
     *            an ElevationProfil that will be useful to get elevations
     *            required for the computation of the function
     * @param ray0
     *            the initial elevation of the ray
     * @param raySlope
     *            the tangent of the elevation angle needed to create the
     *            function
     * @return the DoubleUnaryOperator function giving the distance between a
     *         ray of initial elevation ray0, of slope raySlope and the profile
     */
    public static DoubleUnaryOperator rayToGroundDistance(
            ElevationProfile profile, double ray0, double raySlope) {
        DoubleUnaryOperator function = x -> ray0 + x * (raySlope)
                - (profile.elevationAt(x))
                + REFRACTION_OVER_EARTH_RADIUS
                        * Math2.sq(x);
        return function;
        
        // We simply apply the formulas given
    }

}
