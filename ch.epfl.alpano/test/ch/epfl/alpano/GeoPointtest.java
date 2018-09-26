package ch.epfl.alpano;

import static org.junit.Assert.*;

import org.junit.Test;

public class GeoPointtest {

    @Test
    public void testgetterlongitude() {
       GeoPoint geo1= new GeoPoint(Math.PI,1);
       assertEquals(geo1.longitude(),Math.PI,0.1);
      
    }
    
    @Test
    public void testgetterlatitude() {
        GeoPoint geo1= new GeoPoint(2,-Math.PI /2);
        assertEquals(geo1.latitude(),- Math.PI /2,0);
      
    }
    
    @Test
    public void testAzimuthto(){
        

        GeoPoint geolearn=new GeoPoint(Math.toRadians(6.56729), Math.toRadians(46.51796)); 
        GeoPoint geomount= new GeoPoint(Math.toRadians(8.00423), Math.toRadians(46.57745)); 
        
        assertEquals(Math.toDegrees((geomount.azimuthTo(geolearn)) ),180+86.67,1);
        
    }

    @Test 
    public void testDistanceto(){
        GeoPoint geolearn=new GeoPoint( Math.toRadians(6.56729), Math.toRadians(46.51796));
        GeoPoint geomount= new GeoPoint(Math.toRadians(8.00423), Math.toRadians(46.57745)); 

        GeoPoint geomount2= new GeoPoint(Math.toRadians(8.00423), Math.toRadians(46.57745));
       // GeoPoint geomount2= new GeoPoint(Math.toRadians(46.57745), Math.toRadians(8.00423));
           assertEquals(geolearn.distanceTo(geomount),110049,40);
        assertEquals(geomount.distanceTo(geomount2), 0,00);
        
        
        
    }

    @Test
    public void toStringtest(){

       GeoPoint geo1= new GeoPoint(Math.toRadians(-7.6),Math.toRadians(54.3));
       assertEquals(geo1.toString(),"(-7.6000,54.3000)");
        
        
    }
}
