package ch.epfl.alpano;

import static org.junit.Assert.*;

import org.junit.Test;

public class testparameters {

    @Test
    public void testXforAzimuth() {

        GeoPoint position= new GeoPoint(0,0);
        int altitude = 2000;
        double azimuthcenter= Math.toRadians(180);
        double angledevue=Math.toRadians(80);
        int maxDistance=100000;
        int width= 9;
        int height=5;
         PanoramaParameters param= new PanoramaParameters(position,altitude,azimuthcenter,angledevue,maxDistance,width,height);
         
         assertEquals(0,param.xForAzimuth(Math.toRadians(140)),1E-8);
        assertEquals(1,param.xForAzimuth(Math.toRadians(150)),1E-8);
         assertEquals(2,param.xForAzimuth(Math.toRadians(160)),1E-8);
         assertEquals(3,param.xForAzimuth(Math.toRadians(170)),1E-8);
         assertEquals(4,param.xForAzimuth(Math.toRadians(180)),1E-8);
         assertEquals(5,param.xForAzimuth(Math.toRadians(190)),1E-8);
         assertEquals(6,param.xForAzimuth(Math.toRadians(200)),1E-8);
         assertEquals(7,param.xForAzimuth(Math.toRadians(210)),1E-8);
         assertEquals(8,param.xForAzimuth(Math.toRadians(220)),1E-8);
         
        
        
    }
    
    
    @Test
    public void testAzimuthForX() {

        GeoPoint position= new GeoPoint(0,0);
        int altitude = 2000;
        double azimuthcenter= Math.toRadians(180);
        double angledevue=Math.toRadians(80);
        int maxDistance=100000;
        int width= 9;
        int height=5;
         PanoramaParameters param= new PanoramaParameters(position,altitude,azimuthcenter,angledevue,maxDistance,width,height);
        
         assertEquals(Math.toRadians(140), param.azimuthForX(0),0);
         assertEquals(Math.toRadians(150), param.azimuthForX(1),0);
         assertEquals(Math.toRadians(160), param.azimuthForX(2),0);
         assertEquals(Math.toRadians(170), param.azimuthForX(3),0);
         assertEquals(Math.toRadians(180), param.azimuthForX(4),0);
         assertEquals(Math.toRadians(190), param.azimuthForX(5),0);
         assertEquals(Math.toRadians(200), param.azimuthForX(6),0);
         assertEquals(Math.toRadians(210), param.azimuthForX(7),0);
         assertEquals(Math.toRadians(220), param.azimuthForX(8),0);
    }

    @Test
    public void testAltitudeForY() {


        GeoPoint position= new GeoPoint(0,0);
        int altitude = 2000;
        double azimuthcenter= Math.toRadians(180);
        double angledevue=Math.toRadians(80);
        int maxDistance=100000;
        int width= 9;
        int height=5;
         PanoramaParameters param= new PanoramaParameters(position,altitude,azimuthcenter,angledevue,maxDistance,width,height);
        
         assertEquals(Math.toRadians(20),param.altitudeForY(0),0);

         assertEquals(Math.toRadians(10),param.altitudeForY(1),0);

         assertEquals(Math.toRadians(0),param.altitudeForY(2),0);

         assertEquals(Math.toRadians(-10),param.altitudeForY(3),0);

         assertEquals(Math.toRadians(-20),param.altitudeForY(4),0);
         
    }

    @Test
    public void testYforAltitude() {



        GeoPoint position= new GeoPoint(0,0);
        int altitude = 2000;
        double azimuthcenter= Math.toRadians(180);
        double angledevue=Math.toRadians(80);
        int maxDistance=100000;
        int width= 9;
        int height=5;
         PanoramaParameters param= new PanoramaParameters(position,altitude,azimuthcenter,angledevue,maxDistance,width,height);
        

         assertEquals(0,param.yForAltitude(Math.toRadians(20)),0);

         assertEquals(1,param.yForAltitude(Math.toRadians(10)),0);

         assertEquals(2,param.yForAltitude(Math.toRadians(0)),0);

         assertEquals(3,param.yForAltitude(Math.toRadians(-10)),0);

         assertEquals(4,param.yForAltitude(Math.toRadians(-20)),0);
        
    }

    @Test
    public void testisValidSampleIndex() {
        GeoPoint position= new GeoPoint(0,0);
        int altitude = 2000;
        double azimuthcenter= Math.toRadians(180);
        double angledevue=Math.toRadians(80);
        int maxDistance=100000;
        int width= 5;
        int height=10;
         PanoramaParameters param= new PanoramaParameters(position,altitude,azimuthcenter,angledevue,maxDistance,width,height);
        
       
    
              assertTrue(param.isValidSampleIndex(0,0));

              assertTrue(param.isValidSampleIndex(0,1));

              assertTrue(param.isValidSampleIndex(0,2));

              assertTrue(param.isValidSampleIndex(0,3));

              assertTrue(param.isValidSampleIndex(0,4));

              assertTrue(param.isValidSampleIndex(0,5));

              assertTrue(param.isValidSampleIndex(0,6));

              assertTrue(param.isValidSampleIndex(0,7));

              assertTrue(param.isValidSampleIndex(0,8));

              assertTrue(param.isValidSampleIndex(0,9));
        
              assertFalse(param.isValidSampleIndex(0, 10));
              
              assertTrue(param.isValidSampleIndex(1,0));

              assertTrue(param.isValidSampleIndex(1,1));

              assertTrue(param.isValidSampleIndex(1,2));

              assertTrue(param.isValidSampleIndex(1,3));

              assertTrue(param.isValidSampleIndex(1,4));

              assertTrue(param.isValidSampleIndex(1,5));

              assertTrue(param.isValidSampleIndex(1,6));

              assertTrue(param.isValidSampleIndex(1,7));

              assertTrue(param.isValidSampleIndex(1,8));

              assertTrue(param.isValidSampleIndex(1,9));
        
              assertFalse(param.isValidSampleIndex(1, 10));
              
              assertTrue(param.isValidSampleIndex(2,0));

              assertTrue(param.isValidSampleIndex(2,1));

              assertTrue(param.isValidSampleIndex(2,2));

              assertTrue(param.isValidSampleIndex(2,3));

              assertTrue(param.isValidSampleIndex(2,4));

              assertTrue(param.isValidSampleIndex(2,5));

              assertTrue(param.isValidSampleIndex(2,6));

              assertTrue(param.isValidSampleIndex(2,7));

              assertTrue(param.isValidSampleIndex(2,8));

              assertTrue(param.isValidSampleIndex(2,9));
        
              assertFalse(param.isValidSampleIndex(2, 10));
              
              assertTrue(param.isValidSampleIndex(3,0));

              assertTrue(param.isValidSampleIndex(3,1));

              assertTrue(param.isValidSampleIndex(3,2));

              assertTrue(param.isValidSampleIndex(3,3));

              assertTrue(param.isValidSampleIndex(3,4));

              assertTrue(param.isValidSampleIndex(3,5));

              assertTrue(param.isValidSampleIndex(3,6));

              assertTrue(param.isValidSampleIndex(3,7));

              assertTrue(param.isValidSampleIndex(3,8));

              assertTrue(param.isValidSampleIndex(3,9));
        
              assertFalse(param.isValidSampleIndex(3, 10));
              
              assertTrue(param.isValidSampleIndex(4,0));

              assertTrue(param.isValidSampleIndex(4,1));

              assertTrue(param.isValidSampleIndex(4,2));

              assertTrue(param.isValidSampleIndex(4,3));

              assertTrue(param.isValidSampleIndex(4,4));

              assertTrue(param.isValidSampleIndex(4,5));

              assertTrue(param.isValidSampleIndex(4,6));

              assertTrue(param.isValidSampleIndex(4,7));

              assertTrue(param.isValidSampleIndex(4,8));

              assertTrue(param.isValidSampleIndex(4,9));
        
              assertFalse(param.isValidSampleIndex(4, 10));
              

              assertFalse(param.isValidSampleIndex(5, 0));
              

              
    }

    @Test
    public void testlinearSampleIndex() {
        GeoPoint position= new GeoPoint(0,0);
        int altitude = 2000;
        double azimuthcenter= Math.toRadians(180);
        double angledevue=Math.toRadians(80);
        int maxDistance=100000;
        int width= 10;
        int height=5;
         PanoramaParameters param= new PanoramaParameters(position,altitude,azimuthcenter,angledevue,maxDistance,width,height);
        
                 
             
             assertEquals(0,param.linearSampleIndex(0,0),0);

             assertEquals(1,param.linearSampleIndex(1,0),0);
             assertEquals(2,param.linearSampleIndex(2,0),0);
             assertEquals(03,param.linearSampleIndex(3,0),0);
             assertEquals(04,param.linearSampleIndex(4,0),0);
             assertEquals(5,param.linearSampleIndex(5,0),0);
             assertEquals(6,param.linearSampleIndex(6,0),0);
             assertEquals(7,param.linearSampleIndex(7,0),0);
             assertEquals(8,param.linearSampleIndex(8,0),0);
             assertEquals(9,param.linearSampleIndex(9,0),0);

             assertEquals(10,param.linearSampleIndex(0,1),0);
             assertEquals(11,param.linearSampleIndex(1,1),0);
             assertEquals(12,param.linearSampleIndex(2,1),0);
             assertEquals(13,param.linearSampleIndex(3,1),0);
             assertEquals(14,param.linearSampleIndex(4,1),0);

             assertEquals(15,param.linearSampleIndex(5,1),0);
             assertEquals(16,param.linearSampleIndex(6,1),0);
             assertEquals(17,param.linearSampleIndex(7,1),0);
             assertEquals(18,param.linearSampleIndex(8,1),0);
             assertEquals(19,param.linearSampleIndex(9,1),0);

             assertEquals(20,param.linearSampleIndex(0,2),0);
             assertEquals(21,param.linearSampleIndex(1,2),0);
             assertEquals(22,param.linearSampleIndex(2,2),0);
             assertEquals(23,param.linearSampleIndex(3,2),0);
             assertEquals(24,param.linearSampleIndex(4,2),0);
            

             assertEquals(25,param.linearSampleIndex(5,2),0);
             assertEquals(26,param.linearSampleIndex(6,2),0);
             assertEquals(27,param.linearSampleIndex(7,2),0);
             assertEquals(28,param.linearSampleIndex(8,2),0);
             assertEquals(29,param.linearSampleIndex(9,2),0);

             assertEquals(30,param.linearSampleIndex(0,3),0);
             assertEquals(31,param.linearSampleIndex(1,3),0);
             assertEquals(32,param.linearSampleIndex(2,3),0);
             assertEquals(33,param.linearSampleIndex(3,3),0);
             assertEquals(34,param.linearSampleIndex(4,3),0);

             assertEquals(35,param.linearSampleIndex(5,3),0);
             assertEquals(36,param.linearSampleIndex(6,3),0);
             assertEquals(37,param.linearSampleIndex(7,3),0);
             assertEquals(38,param.linearSampleIndex(8,3),0);
             assertEquals(39,param.linearSampleIndex(9,3),0);
             
             assertEquals(40,param.linearSampleIndex(0,4),0);
             assertEquals(41,param.linearSampleIndex(1,4),0);
             assertEquals(42,param.linearSampleIndex(2,4),0);
             assertEquals(43,param.linearSampleIndex(3,4),0);
             assertEquals(44,param.linearSampleIndex(4,4),0);


             assertEquals(45,param.linearSampleIndex(5,4),0);
             assertEquals(46,param.linearSampleIndex(6,4),0);
             assertEquals(47,param.linearSampleIndex(7,4),0);
             assertEquals(48,param.linearSampleIndex(8,4),0);
             assertEquals(49,param.linearSampleIndex(9,4),0);

    
    
    


}
}