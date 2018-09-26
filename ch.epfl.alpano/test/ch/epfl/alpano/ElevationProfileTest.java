package ch.epfl.alpano;

import static org.junit.Assert.*;
import ch.epfl.alpano.*;
import ch.epfl.alpano.dem.ContinuousElevationModel;

import org.junit.Test;

public class ElevationProfileTest {
    private double bigCircleLong(double x,GeoPoint origin, double azimuth){
        double alpha  = Azimuth.toMath(azimuth);
        double xRad= Distance.toRadians(x);
        double phiO=origin.latitude();
        double lambdaO=origin.longitude();
        double phi= Math.asin(Math.sin(phiO)*Math.cos(xRad) + Math.cos(phiO)*Math.sin(xRad)* Math.cos(alpha));
     return    Math2.floorMod(lambdaO-Math.asin( (Math.sin(alpha)*Math.sin(xRad))/Math.cos(phi) ) 
                + Math.PI , Math2.PI2) - Math.PI;
       
    }
    private double bigCircleLat(double x,GeoPoint origin, double azimuth){
        double alpha  = Azimuth.toMath(azimuth);
        double xRad= Distance.toRadians(x);
        double phiO=origin.latitude();
        double lambdaO=origin.longitude();
       return ( Math.asin(Math.sin(phiO)*Math.cos(xRad) + Math.cos(phiO)*Math.sin(xRad)* Math.cos(alpha)));
   
        
    }
    
    private GeoPoint bigCircle(double x,GeoPoint origin, double azimuth){
        double alpha  = Azimuth.toMath(azimuth);
        double xRad= Distance.toRadians(x);
        double phiO=origin.latitude();
        double lambdaO=origin.longitude();
        double phi= Math.asin(Math.sin(phiO)*Math.cos(xRad) + Math.cos(phiO)*Math.sin(xRad)* Math.cos(alpha));
        double lambda= Math2.floorMod(lambdaO-Math.asin( (Math.sin(alpha)*Math.sin(xRad))/Math.cos(phi) ) 
                + Math.PI , Math2.PI2) - Math.PI;
        return new GeoPoint(lambda,phi);
    }
    
    @Test
    public void test1() {
        double x = 10240;
        GeoPoint origin= new GeoPoint(Math.toRadians(6), Math.toRadians(46));
        double length= 100000;
        
        assertEquals(Math.toDegrees(bigCircleLong(x,origin,Math.PI/4d)), 6.09385, 1 );
        
        
    }

    
    @Test
    public void test2() {
        double x = 10240;
        GeoPoint origin= new GeoPoint(Math.toRadians(6), Math.toRadians(46));
        double length= 100000;
        
        assertEquals(Math.toDegrees(bigCircleLat(x,origin,Math.PI/4d)), 46.06508, 1 );
        
        
    }
    
}
