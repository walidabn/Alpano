package ch.epfl.alpano.dem;

import ch.epfl.alpano.Interval2D;
import ch.epfl.alpano.Math2;
import ch.epfl.alpano.Preconditions;

/**
 * This interface is here to represent the behavior that discrete elevation models all have in common
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */
public interface DiscreteElevationModel extends AutoCloseable {

    final static int SAMPLES_PER_DEGREE = 3600;
    final static double SAMPLES_PER_RADIAN = (360d / Math2.PI2)
            * SAMPLES_PER_DEGREE;

    /**
     * Gives the sample index of an angle
     * 
     * @param angle
     *            : an angle given in radians (either a longitude or a latitude)
     * @return the index corresponding to this angle
     */
    public static double sampleIndex(double angle) {
        return angle * SAMPLES_PER_RADIAN;
    }

    /**
     * Gives the extent of a DiscreteElevationModel
     * 
     * @return the Interval2D of points corresponding to this DEM must be
     *         overrided in a class implementing this interface
     */
    public abstract Interval2D extent();

    /**
     * Gives the elevation sample at (x,y)
     * 
     * @param x
     *            the first int coordinate of a sample (different from
     *            geographic coordinates)
     * @param y
     *            the second int coordinate of a sample (different from
     *            geographic coordinates)
     * @return the sample of elevation at the index given (x,y) must be
     *         overrided in a class implementing this interface
     */
    public abstract double elevationSample(int x, int y);

    /**
     * Constructs a CompositeDiscreteElevationModel of two instances of
     * DiscreteElevationModel if they are unionable,
     * 
     * @param that
     *            an other object implementing this interface
     * @return a Composite DEM corresponding to the union of this DEM with that
     *         Throws IllegalArgumentException if those two DEM are not
     *         unionable
     */
    public default DiscreteElevationModel union(DiscreteElevationModel that) {

        Preconditions.checkArgument(
                this.extent().isUnionableWith(that.extent()),
                "Those two DEM are not unionable");
        return new CompositeDiscreteElevationModel(this, that);
    }

}
