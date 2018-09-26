
package ch.epfl.alpano.gui;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import static ch.epfl.alpano.Preconditions.checkArgument;

import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.PanoramaParameters;

/**
 * @author Walid Ben Naceur (273765), Ridha Chahed (270618)
 *
 *         This immutable class is here to represent panorama parameters in such
 *         a way that they are more convenient for the user of the program (for
 *         example, longitudes and latitudes would be entered in degrees and not
 *         in radians, the maxDistance would be entered in kilometers instead of
 *         meters and so on...)
 * 
 */

public final class PanoramaUserParameters {

    /**
     * This map is used to map each parameter from the Enumeration UserParameter
     * to an Integer that corresponds to the value that we put for it
     */
    private final Map<UserParameter, Integer> userParameter;
    public final static int CONVERSION_M_KM = 1000;
    private final static double CONVERSION_COORDINATE_FORMAT=10000;
    private final static int VERTICAL_LIMIT = 170; 

    /**
     * A public constructor for an instance of PanoramaUserParameters that takes
     * a map of UserParameter to Integer as argument, and sanitizes the values
     * of the Integers, with a special way for the Height
     * 
     * @param userParameter
     *            an EnumMap of UserParameter to Integer
     */
    
    public PanoramaUserParameters(Map<UserParameter, Integer> userParameter) {
        requireNonNull(userParameter);
        checkArgument(userParameter.entrySet().size() == 9);
      
        Map<UserParameter, Integer> userParameterSanitized = new EnumMap<>(
                UserParameter.class);
        
        
        for (Entry<UserParameter, Integer> entry : userParameter.entrySet()) {
            
            UserParameter parameter = entry.getKey();
            if (parameter == UserParameter.HEIGHT) {
             
                
                
                int limit = (int) (VERTICAL_LIMIT
                        *  ((float) userParameter.get(UserParameter.WIDTH) - 1f)
                        / ((float) userParameter
                                .get(UserParameter.HORIZONTAL_FIELD_OF_VIEW)))
                        + 1; 
                
                
                
                userParameterSanitized.put(parameter,
                        entry.getKey().sanitize(Math.min(limit, entry.getValue())));
                
            } else {
     
            userParameterSanitized.put(parameter,
                    parameter.sanitize(entry.getValue())); }
       
       }
        this.userParameter = unmodifiableMap(
                new EnumMap<>(userParameterSanitized));

    }

    /**
     * This constructor takes as argument all the int values for the panorama
     * parameters and builds a map, before creating an instance of PanoramaUserParameters through the first constructor above
     * (This constructor will be useful to define predefined panoramas especially)
     * 
     * @param observerLongitude
     *            the observer's longitude (in ten thousands of degrees)
     * @param observerLatitude
     *            the observer's latitude (in ten thousands of degrees)
     * @param observerElevation
     *            the observer's elevation (in meters)
     * @param centerAzimuth
     *            the center azimuth's (in degrees)
     * @param horizontalFieldOfView
     *            the horizontal field of view (in degrees)
     * @param maxDistance
     *            the maximal distance of view for the panorama (in kilometers)
     * @param width
     *            the width of the panorama
     * @param height
     *            the height of the panorama
     * @param superSamplingExponent
     *            the sampling exponent to have a clearer image at the end
     * 
     
     * 
     *            Finally, after setting those values to an EnumMap, we call the
     *            first constructor to have our instance of
     *            PanoramaUserParameters
     */
    public PanoramaUserParameters(int observerLongitude, int observerLatitude,
            int observerElevation, int centerAzimuth, int horizontalFieldOfView,
            int maxDistance, int width, int height, int superSamplingExponent) {

        this(toMap(observerLongitude, observerLatitude, observerElevation,
                centerAzimuth, horizontalFieldOfView, maxDistance, width,
                height, superSamplingExponent));

    }

    /**
     * Charges to a map of <UserParameter,Integer> a variable number of int in
     * order to avoid duplicating code in the second constructor
     * 
     * @param args
     *            a variable number of int
     * @return a map of <UserParameter,Integer>
     */
    private static Map<UserParameter, Integer> toMap(int... args) {
        Map<UserParameter, Integer> userParameter2 = new EnumMap<>(
                UserParameter.class);
        int i = 0;
        for (int x : args) {
            userParameter2.put(UserParameter.values()[i], x);
            i++;
        }
        return userParameter2;

    }

    /**
     * A general getter to get a userParameter
     * 
     * @param up
     *            a UserParameter
     * @return the int corresponding to the UserParameter for which we want its
     *         value entered
     */
    public int get(UserParameter up) {
        requireNonNull(up);
        return userParameter.get(up);
    }

    

    /**
     * A getter for the observer's longitude
     * 
     * @return the int corresponding to the observer's longitude (in ten
     *         thousands of degrees, between 60_000 and 120_000)
     */
    public int observerLongitude() {
        return get(UserParameter.OBSERVER_LONGITUDE);
    }

    /**
     * A getter for the observer's latitude
     * 
     * @return the int corresponding to the observer's latitude (in ten
     *         thousands of degrees, between 450_000 and 480_000)
     */
    public int observerLatitude() {
        return get(UserParameter.OBSERVER_LATITUDE);
    }

    /**
     * A getter for the observer's elevation
     * 
     * @return the int corresponding to the observer's elevation (in meters,
     *         between 300 and 10_000)
     */
    public int observerElevation() {
        return get(UserParameter.OBSERVER_ELEVATION);

    }

    /**
     * A getter for the center Azimuth argument
     * 
     * @return the int corresponding to the centerAzimuth (in degrees, between 0
     *         and 359)
     */
    public int centerAzimuth() {
        return get(UserParameter.CENTER_AZIMUTH);
    }

    /**
     * A getter for the horizontal field of view
     * 
     * @return the int corresponding to the horizontal field of view (in
     *         degrees, between 1 and 360)
     */
    public int horizontalFieldOfView() {
        return get(UserParameter.HORIZONTAL_FIELD_OF_VIEW);
    }

    /**
     * A getter for the maxDistance argument
     * 
     * @return the int corresponding to the maximal distance seen by the
     *         observer (in kilometers, between 10 and 600)
     */
    public int maxDistance() {
        return get(UserParameter.MAX_DISTANCE);
    }

    /**
     * A getter for the width of the panorama
     * 
     * @return the int corresponding to the width of the panorama (between 30
     *         and 16_000, in samples)
     */
    public int width() {
        return get(UserParameter.WIDTH);
    }

    /**
     * A getter for the height of the panorama
     * 
     * @return the int corresponding to the height of the panorama (between 10
     *         and 4000, in samples)
     */
    public int height() {
        return get(UserParameter.HEIGHT);
    }

    /**
     * A getter for the super sampling exponent
     * 
     * @return the int corresponding to the super sampling exponent (either 0, 1
     *         or 2, no units)
     */
    public int superSamplingExponent() {
        return get(UserParameter.SUPER_SAMPLING_EXPONENT);
    }

    /**
     * Builds an instance of PanoramaParameters (that has units more
     * inconvenient than the ones from this class) from this, while considering
     * the superSamplingExponent for the width and the height of the parameters
     * 
     * @return an instance of PanoramaParameters that is a correspondence to the
     *         ones entered in this
     */
    public PanoramaParameters panoramaParameters() {

        double obsLonRad = changeUnityOfCoordinateToRad(observerLongitude());
        double obsLatRad = changeUnityOfCoordinateToRad(observerLatitude());

        GeoPoint oP = new GeoPoint(obsLonRad, obsLatRad);
        int superWidth = (int) width()<<superSamplingExponent();
        int superHeight = (int) height()<<superSamplingExponent();

        return new PanoramaParameters(oP, observerElevation(),
                changeUnityForCenterAzimuthToRad(),
                changeUnityForHorizontalFieldOfViewToRad(),
                changeUnityForMaxDistanceToMeters(), superWidth, superHeight);

    }

    /**
     * The same method as the one above, but the superSamplingExponent is not
     * considered here
     * 
     * @return an instance of PanoramaParameters that is a correspondence to the
     *         ones entered in this, with no consideration for the
     *         superSamplingExponent
     */
    public PanoramaParameters panoramaDisplayParameters() {

        double obsLonRad = changeUnityOfCoordinateToRad(observerLongitude());
        double obsLatRad = changeUnityOfCoordinateToRad(observerLatitude());

        GeoPoint oP = new GeoPoint(obsLonRad, obsLatRad);

        return new PanoramaParameters(oP, observerElevation(),
                changeUnityForCenterAzimuthToRad(),
                changeUnityForHorizontalFieldOfViewToRad(),
                changeUnityForMaxDistanceToMeters(), width(), height());

    }

    /**
     * @param i
     *            an int that we have in degrees x10000, that we want in radians
     * @return the double corresponding in radians
     */
    private double changeUnityOfCoordinateToRad(int i) {
        return Math.toRadians((i / CONVERSION_COORDINATE_FORMAT));
    }

    /**
     * @return maxDistance, which was originally in kilometers, in meters
     */
    private int changeUnityForMaxDistanceToMeters() {
        return maxDistance() * CONVERSION_M_KM;
    }

    /**
     * @return the centerAzimuth, which was originally in degrees, in radians
     */
    private double changeUnityForCenterAzimuthToRad() {
        return Math.toRadians(centerAzimuth());
    }

    /**
     * @return the horizontalFieldOfView, which was originally in degrees, in
     *         radians
     */
    private double changeUnityForHorizontalFieldOfViewToRad() {
        return Math.toRadians(horizontalFieldOfView());
    }

    /*
     * An overriding of the equals method : an object and an instance of
     * PanoramaUserParameters are equal if they are both instances of
     * PanoramaUserParameters, and if all of their attributes are equal
     * otherwise, they are not equal
     */
    @Override
    public boolean equals(Object thatO) {
        boolean b = (thatO instanceof PanoramaUserParameters);
        if (!b) {
            return false;
        }

        PanoramaUserParameters other = (PanoramaUserParameters) thatO;

        return (userParameter.equals(other.userParameter));

    }

    /*
     * An overriding of the hashCode method : returns the hash code of this
     * instance of PanoramaUserParameters
     */
    @Override
    public int hashCode() {
        return hash(userParameter);

    }

}
