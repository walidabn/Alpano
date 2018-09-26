package ch.epfl.alpano.gui;


/**
 * @author Walid Ben Naceur (273765), Ridha Chahed (270618)
 *
 *         This Enumeration is here to enumerate all the possible parameters for
 *         the user to build a panorama with several restrictions on each
 *         parameter
 */
public enum UserParameter {

    OBSERVER_LONGITUDE(60_000, 120_000), OBSERVER_LATITUDE(450_000,
            480_000), OBSERVER_ELEVATION(300, 10_000), CENTER_AZIMUTH(0,
                    359), HORIZONTAL_FIELD_OF_VIEW(1, 360), MAX_DISTANCE(10,
                            600), WIDTH(30, 16_000), HEIGHT(10,
                                    4000), SUPER_SAMPLING_EXPONENT(0, 2);

    /**
     * The minimal value each UserParameter can take (an int)
     */
    private final int min;
    /**
     * The maximal value each UserParameter can take (an int)
     */
    private final int max;
    /**
     * A private constructor for each parameter in this Enumeration
     * 
     * @param min
     *            The minimal value each UserParameter can take (an int)
     * @param max
     *            The maximal value each UserParameter can take (an int)
     */
    private UserParameter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * A getter for the min argument
     * 
     * @return the min of a UserParameter
     */
    public int min() {
        return min;
    }

    /**
     * A getter for the max argument
     * 
     * @return the min of a UserParameter
     */
    public int max() {
        return max;
    }

    /**
     * When we will enter a value for each parameter, we will want it to be a
     * "possible" value for the said parameter : this method readjusts the value
     * entered to be in the correct range if it is not
     * 
     * @param s
     *            the int that we want to have sanitized ( have its value
     *            readjusted if it is invalid for a parameter)
     * @return the sanitized value of s if s is not "sane", s otherwise
     */
    public int sanitize(int s) {
        if (s>=min && s<=max) {
            return s;
        } else {
            if (s > max) {
                return max;
            } else {
                return min;
            }
        }
    }
}
