package ch.epfl.alpano;

import java.util.Locale;

/**
 * This class is here to represent Geographic Points
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */
public final class GeoPoint {
    
    private final double longitude;
    private final double latitude;
    
    /** 
     * A constructor of a GeoPoint through a longitude and a latitude (both are in radians)
     * @param longitude 
     * The longitude of the Geographic Point ( the angular distance between that point and a meridian of reference in the WGS 84 geodesic system)  
     * 
     * @param latitude 
     * The latitude of the Geographic Point (the angular distance of that point to the equator) 
     * 
     * throws IllegalArgumentException if the longitude entered is not in the interval [-pi,pi]
     * throws IllegalArgumentException if the latitude entered is not in the interval [-pi/2.0 , pi/2.0]
     */
    public GeoPoint(double longitude, double latitude){
        
        this.longitude=longitude;
        this.latitude=latitude;
        Preconditions.checkArgument(((longitude>= -Math.PI) && (longitude<=Math.PI)) , "error : longitude is not in the interval [-pi, pi ]");
        Preconditions.checkArgument((latitude>= -Math.PI/2.0) && (latitude<=Math.PI/2.0), "error : latitude is not in the interval [-pi/2 , pi/2]" );
    }
    
    /** A getter for the longitude 
     * @return the longitude of this GeoPoint
     */
    public double longitude(){
        return longitude;
    }
    
    /** A getter for the latitude
     * @return the latitude of this GeoPoint
     */
    public double latitude(){
        return latitude;
    }
    
    /** Determines the distance between this GeoPoint and an other one on Earth's surface
     * @param that an other GeoPoint
     * @return the distance in meters between this and that 
     */
    
    public double distanceTo(GeoPoint that) {
        double cos1 = Math.cos(latitude);
        double cos2 = Math.cos(that.latitude());
        double haversin1 = Math2.haversin(latitude - that.latitude());
        double haversin2 = Math2.haversin(longitude - that.longitude());
        double angle = 2 * Math.asin(Math.sqrt(haversin1 + cos1 * cos2 * haversin2));
        return Distance.toMeters(angle);
        // We simply apply here the formulas for a distance between two points on Earth
    }
    
  

    
    /**
     * @param that an other GeoPoint
     * @return the azimuth angle of that compared to this GeoPoint
     */
    public double azimuthTo(GeoPoint that){
    
        double sin3 = Math.sin(longitude - that.longitude());
        double cos2 = Math.cos(that.latitude());
        double cos1 = Math.cos(latitude);
        double cos3 = Math.cos(longitude - that.longitude());
        double sin1 = Math.sin(latitude);
        double sin2 = Math.sin(that.latitude());
        double angle = Math.atan2((sin3 * cos2),(cos1 * sin2 - sin1 * cos2 * cos3));
        return Azimuth.fromMath(Azimuth.canonicalize(angle));
        // We simply apply here the formulas to get an azimuth between two points on Earth
    }
    
    
    /**
     * @return a String with the longitude and latitude expressed in degrees (with 4 decimals exactly)
     * 
     */
    
    @Override
    public String toString(){
        Locale l=null;
        return (String.format(l, "(%.4f,%.4f)",Math.toDegrees(longitude()),Math.toDegrees(latitude())));
    
    }
    
    
}
