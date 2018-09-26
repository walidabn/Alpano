package ch.epfl.alpano;

/**
 * This interface is here to represent Azimuths
 * 
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */
public interface Azimuth {

    /**
     * Determines if a an azimuth is canonical or not
     * 
     * @param azimuth
     *            An azimuth that may or may not be canonical
     * @return A boolean saying if the azimuth is canonical
     */
    public static boolean isCanonical(double azimuth) {

        return ((azimuth >= 0) && (azimuth < Math2.PI2));

    }

    /**
     * Canonicalizes an azimuth if it is not
     * 
     * @param azimuth
     *            an azimuth that may or may not be canonical
     * @return the canonical azimuth corresponding to the one in the parameter
     */
    public static double canonicalize(double azimuth) {
       
        return (isCanonical(azimuth)) ? azimuth
                : Math2.floorMod(azimuth, Math2.PI2);

    }

    /**
     * Transforms an azimuth into a mathematical angle
     * 
     * @param azimuth
     *            a canonical azimuth (clockwise)
     * 
     *            throws IllegalArgumentException if the azimuth is not
     *            canonical
     * 
     * @return the canonical angle corresponding to the canonical azimuth (
     *         counter clockwise, following the mathematical conventions)
     * 
     * 
     */
    public static double toMath(double azimuth) {
        Preconditions.checkArgument(isCanonical(azimuth),
                " azimuth is not canonical");
        return (azimuth == 0) ? azimuth : (Math2.PI2 - azimuth);

    }

    /**
     * Transforms a mathematical angle into an azimuth
     * 
     * @param angle
     *            a canonical angle ( counter clockwise, following the
     *            mathematical conventions) throws IllegalArgumentException if
     *            the angle is not canonical
     * @return the canonical azimuth corresponding to the canonical angle (the
     *         clockwise azimuth)
     */
    public static double fromMath(double angle) {
        Preconditions.checkArgument(isCanonical(angle),
                " angle is not canonical");
        // Because 0 is the only point where a canonical angle and a canonical
        // azimuth are equal

        return (angle == 0) ? angle : (Math2.PI2 - angle);

    }

    /**
     * Determines the String corresponding to an octant in which an azimuth
     * could be
     * 
     * @param azimuth
     *            the canonical azimuth of which we want to determine its octant
     *            throws IllegalArgumentException if the azimuth is not
     *            canonical
     * @param n
     *            a string n for north
     * @param e
     *            a string e for east
     * @param s
     *            a string s for south
     * @param w
     *            a string w for west
     * @return the string corresponding to one of the 8 possible octants in
     *         which the azimuth could be
     */
    public static String toOctantString(double azimuth, String n, String e,
            String s, String w) {
        Preconditions.checkArgument(isCanonical(azimuth));
        String octant = "";
        switch ((int) (Math.round(azimuth / (Math2.PI2 / 8d))) % 8) {
        case 0:
            octant = n;
            break;
        case 1:
            octant = n + e;
            break;
        case 2:
            octant = e;
            break;
        case 3:
            octant = s + e;
            break;
        case 4:
            octant = s;
            break;
        case 5:
            octant = s + w;
            break;
        case 6:
            octant = w;
            break;
        case 7:
            octant = n + w;
            break;
        }
        return octant;
    }

}
