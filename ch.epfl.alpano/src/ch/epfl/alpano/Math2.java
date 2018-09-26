package ch.epfl.alpano;

import java.lang.Math;
import java.util.function.DoubleUnaryOperator;

/**
 * This interface is here for us to use mathematical methods that we do not find with Java.Math 
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */
public interface Math2 {

    public final static double PI2 = 2 * Math.PI;

    /**
     * Calculates the square of the double x
     * 
     * @param x
     *            a decimal number
     * @return the square of this decimal number
     */
    public static double sq(double x) {
        return x * x;
    }

    /**
     * Calculates the floor of the mod of x by y
     * 
     * @param x
     *            a decimal number that will be divided by y
     * @param y
     *            the decimal number that will divide x throws
     *            IllegalAgumentException if y is equal to 0
     * @return the positive rest of the modular division of x by y
     */
    public static double floorMod(double x, double y) {
       Preconditions.checkArgument(y != 0, "division error y=0");
        return x - y * (Math.floor(x / y));
    }

    /**
     * Calculates the haversin of x
     * 
     * @param x
     *            a decimal number
     * 
     * @return the haversin of x
     */
    public static double haversin(double x) {

        return sq(Math.sin(x / 2));

    }

    /**
     * Calculates the angular distance between a1 and a2
     * 
     * @param a1
     *            an angle
     * @param a2
     *            a second angle
     * @return the angular distance between a1 and a2 (counter clockwise,
     *         following the mathematical conventions)
     */
    public static double angularDistance(double a1, double a2) {
        return floorMod((a2 - a1 + Math.PI), PI2) - Math.PI;

    }

    /**
     * Does a linear interpolation of y0 and y1 to calculate the image of x
     * 
     * @param y0
     *            the image of 0
     * @param y1
     *            the image of 1
     * @param x
     *            the point of which we search an image by interpolation
     * @return the image of x by interpolation
     */
    public static double lerp(double y0, double y1, double x) {
        return (y1 - y0) * x + y0;
    }

    /**
     * Does a bilinear interpolation to calculate the image of (x,y)
     * 
     * @param z00
     *            the image of (0,0)
     * @param z10
     *            the image of (1,0)
     * @param z01
     *            the image of (0,1)
     * @param z11
     *            the image of (1,1)
     * @param x
     *            along with y, we search for its image
     * @param y
     *            along with y, we search for its image
     * @return the image of (x,y)
     */
    public static double bilerp(double z00, double z10, double z01, double z11,
            double x, double y) {
        double z1 = lerp(z00, z10, x);
        double z2 = lerp(z01, z11, x);
        return lerp(z1, z2, y);
// To do a bilinear interpolation, we need to do 3 linear interpolations that way
    }

    /**
     * Search for the lower bound of an interval of size dX containing a root of
     * the function f between minX and maxX
     * 
     * @param f
     *            a function that we can apply to minX or maxX and for which we
     *            search the lower bound of an interval of size dX containing a
     *            root
     * @param minX
     *            the lower bound in our search
     * @param maxX
     *            the upper bound in our search
     * @param dX
     *            the size of the interval containing the root of f
     * @return the lower bound of the interval containing the root throws
     *         IllegalArgumentException if dX is not positive throws
     *         IllegalArgumentException if minX is not bigger than maxX
     */
    public static double firstIntervalContainingRoot(DoubleUnaryOperator f,
            double minX, double maxX, double dX) {

        Preconditions.checkArgument(dX > 0, "dX must be positive");

        Preconditions.checkArgument(minX <= maxX,
                "minX must be smaller than maxX");
        double sign= Math.signum(f.applyAsDouble(minX));
        
        for (double i = minX; i <= maxX - dX; i += dX) {
            if (Math.signum(f.applyAsDouble(i + dX)) != sign) {
                return i;
            }
        } 
        
        
        // We check if there is a root between minX and maxX, otherwise we return POSITIVE INFINITY to say that there was not any root
        return Double.POSITIVE_INFINITY;

    }

    /** Determines the lower bound of an interval of size epsilon or less containing
     *         a root for f 
     * 
     * @param f
     *            a function that we can apply to x1 or x2 and for which we
     *            search the lower bound of an interval smaller than epsilon
     *            containing a root
     * @param x1
     *            the lower bound in our search
     * @param x2
     *            the upper bound in our search
     * @param epsilon
     *            the size of the interval of precision of our search
     * @return the lower bound of an interval of size epsilon or less containing
     *         a root for f throws IllegalArgumentException if epsilon is not
     *         positive throws IllegalArgumentException if f(x1) and f(x2) do
     *         not have the same sign throws IllegalArgumentException if x1 is
     *         not smaller than x2
     */
    public static double improveRoot(DoubleUnaryOperator f, double x1,
            double x2, double epsilon) {

        Preconditions.checkArgument(epsilon > 0, "epsilon must be positive ! ");

        Preconditions.checkArgument(x1 <= x2, " x2 must be bigger than x1");

        Preconditions.checkArgument(
                f.applyAsDouble(x1) * f.applyAsDouble(x2) <= 0,
                " f(x1) and f(x2) have the same sign! ");
        
        // We check that there is at least a root to improve our search

        while (x2 - x1 > epsilon) {
            if (f.applyAsDouble(x1) == 0) {
                return x1;
            }

            if(f.applyAsDouble(x2) * f.applyAsDouble(x1) == 0){
                return x2-epsilon;
            }
            
            // if x1 is the root, we return it directly
            if (f.applyAsDouble(x2) * f.applyAsDouble(x1) < 0) {
                if (f.applyAsDouble(x2) * f.applyAsDouble((x1 + x2) / 2) < 0) {
                    x1 = (x1 + x2) / 2;
                } else {
                    x2 = (x1 + x2) / 2;
                }
                
                
            }
            if (f.applyAsDouble(x2) * f.applyAsDouble((x1 + x2) / 2) == 0){
                return x2-epsilon;
            }
        }
        // We do a search by dichotomy
        return x1;

    }

}
