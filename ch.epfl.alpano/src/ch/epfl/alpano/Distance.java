package ch.epfl.alpano;

/**
 * This interface is here to calculate distances in radians or in meters at the Earth's surface 
 * @author Walid Ben Naceur (273765) Ridha Chehed (270618)
 *
 */
public interface Distance {

    public final static double EARTH_RADIUS=6371000;
    
    /**
     * Transforms a distance in meters into a distance in Radians
     * @param distanceInMeters A distance between two points on earth given in meters
     * @return this distance as a distance in radians because the earth is a sphere
     */
    public static double toRadians(double distanceInMeters){
   
        return distanceInMeters/EARTH_RADIUS;
    }
    
    /**
     * Transforms a distance in meters into a distance in radians
     * @param distanceInRadians A distance between two points on earth given in radians
     * 
     * @return this distance as a distance in meters 
     */
    public static double toMeters(double distanceInRadians){
        return distanceInRadians * EARTH_RADIUS;
    }
    
    
}
