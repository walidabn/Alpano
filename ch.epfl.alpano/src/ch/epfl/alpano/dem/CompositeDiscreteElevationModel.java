package ch.epfl.alpano.dem;

import ch.epfl.alpano.Preconditions;

import static java.util.Objects.requireNonNull;

import ch.epfl.alpano.Interval2D;

/**
 * This class is here to represent a composition of two discrete elevation models
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */
final class CompositeDiscreteElevationModel
        implements DiscreteElevationModel {

    private final DiscreteElevationModel dem1;
    private final DiscreteElevationModel dem2;
    private final Interval2D dem3;

    /**
     * A constructor for a CompositeDiscreteElevationModel that takes for
     * argument two non null instances of DiscreteElevationModel
     * 
     * @param dem1
     *            an object that implements the interface DiscreteElevationModel
     * @param dem2
     *            an other object that implements the interface
     *            DiscreteElevationModel Throws NullPointerException if dem1 or
     *            dem2 are null Throws IllegalArgumentException if dem1's extent
     *            and dem2's extent are not unionable
     */
    public CompositeDiscreteElevationModel(DiscreteElevationModel dem1,
            DiscreteElevationModel dem2) {
        this.dem1 = requireNonNull(dem1);
        this.dem2 = requireNonNull(dem2);
        this.dem3 = dem1.extent().union(dem2.extent());

    }

    /**
     * An overriding of the close() method from the interface AutoClosable
     */

    @Override
    public void close() throws Exception {
        dem1.close();
        dem2.close();
    }

    /**
     * Gives the extent of the union of the extents of the two DEM (when they
     * are unionable)
     * 
     * @return the Interval2D corresponding to the union of the extents of dem1
     *         and dem2
     */
    @Override
    public Interval2D extent() {
        return dem3;

    }

    /**
     * A redefinition of elevationSample from the interface
     * DiscreteElevationModel
     * 
     * @return the elevationSample of the point at the index (x,y)
     */

    @Override
    public double elevationSample(int x, int y) {
        Preconditions.checkArgument(extent().contains(x, y),
                "This index is not in this DEM's extent");
        if (dem1.extent().contains(x, y)) {
            return dem1.elevationSample(x, y);
        } else {
            return dem2.elevationSample(x, y);
        }

    }

}
