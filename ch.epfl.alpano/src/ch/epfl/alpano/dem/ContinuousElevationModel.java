
package ch.epfl.alpano.dem;

import ch.epfl.alpano.Distance;
import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.Math2;

import static java.util.Objects.requireNonNull;

/**
 * This class is here to represent a continuous elevation model obtained through
 * the interpolation of composite discrete elevation models
 * 
 * @author Walid Ben Naceur (273765), Ridha Chahed (270618)
 *
 */

public final class ContinuousElevationModel {
    private final static double D = Distance
            .toMeters(1.0 / DiscreteElevationModel.SAMPLES_PER_RADIAN);
    private final DiscreteElevationModel dem;

    /**
     * A constructor for a ContinuousElevationModel taking as an argument an
     * instance of a class that implements DiscreteElevationModel
     * 
     * @param dem
     *            a DiscreteElevationModel Throws NullPointerException if dem is
     *            null;
     */
    public ContinuousElevationModel(DiscreteElevationModel dem) {
        this.dem = requireNonNull(dem);
    }

    
    /**
     * Gives the elevation corresponding to the GeoPoint p
     * 
     * @param p
     *            a GeoPoint for which we search its elevation sample
     * @return the elevation sample corresponding to the GeoPoint p in meters by
     *         bilinear interpolation
     */
    public double elevationAt(GeoPoint p) {
        double x = DiscreteElevationModel.sampleIndex(p.longitude());
        double y = DiscreteElevationModel.sampleIndex(p.latitude());
        int a = (int) Math.floor(x);
        int b = (int) Math.floor(y);
        double difX = x - a;
        double difY = y - b;
        double d = Math2.bilerp(sample(a, b), sample(a + 1, b),
                sample(a, b + 1), sample(a + 1, b + 1), difX, difY);
        return d;
        
        // We do the bilinear interpolation  of the elevations of 4 points known to find the elevation at P
    }

    /**
     * Gives the slope at the GeoPoint p
     * 
     * @param x
     *            the longitude of a point
     * @param y
     *            the latitude of a point
     * @return the elevation sample of the extension of this dem
     */
    public double slopeAt(GeoPoint p) {
        double x = DiscreteElevationModel.sampleIndex(p.longitude());
        double y = DiscreteElevationModel.sampleIndex(p.latitude());
        int a = (int) Math.floor(x);
        int b = (int) Math.floor(y);
        double difX = x - a;
        double difY = y - b;
        return Math2.bilerp(slope(a, b), slope(a + 1, b), slope(a, b + 1),
                slope(a + 1, b + 1), difX, difY);
        // We do the bilinear interpolation of the slopes of 4 points known to find the slope at p
    }

    /**
     * Gives the elevation sample corresponding to a GeoPoint (x,y), if it is
     * contained in the elevation model (otherwise it returns 0)
     * 
     * @param x
     *            the longitude of a point
     * @param y
     *            the latitude of a point
     * @return the elevation sample of the extension of this DEM
     */
    private double sample(int x, int y) {
        if (dem.extent().contains(x, y)) {
            return dem.elevationSample(x, y);
        } else {
            return 0;
        }
        
    }
   

    /**
     * Gives the slope corresponding to a GeoPoint (x,y), if it is contained in
     * the elevation model (otherwise it returns 0)
     * 
     * @param x
     *            the longitude of a point
     * @param y
     *            the latitude of a point
     * @return the slope of a point of the DEM of the extension of the DEM, in
     *         radians
     */
    private double slope(int x, int y) {
            double sample = sample(x,y);
            double za = sample - sample(x + 1, y);
            double zb = sample - sample(x, y + 1);
            return Math.acos(
                    D / Math.sqrt(Math2.sq(za) + Math2.sq(zb) + Math2.sq(D)));
      
        // We simply apply the formulas given to calculate a slope
    }

}
