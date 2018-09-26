package ch.epfl.alpano;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import ch.epfl.alpano.dem.HgtDiscreteElevationModel;

public class HGTDemTest {

    @Test
    public void test() throws IOException {

        File f = new File("N46E007.hgt");
         HgtDiscreteElevationModel Nhgt= new HgtDiscreteElevationModel(f);
         Interval1D first= new Interval1D(7*3600 , 8*3600);
         Interval1D second = new Interval1D(46*3600 , 47*3600);
         Interval2D third= new Interval2D(first,second);
      
         assertEquals(Nhgt.elevationSample(7*3600 +15 ,47*3600) , 429,0);
        
         
        
    }
    
    @Test 
    public void test2() throws IOException{
        File f = new File("N47E008.hgt");
        HgtDiscreteElevationModel Nhgt= new HgtDiscreteElevationModel(f);
        Interval1D first= new Interval1D(8*3600 , 9*3600);
        Interval1D second = new Interval1D(47*3600 , 48*3600);
        Interval2D third= new Interval2D(first,second);
        assertEquals(third, Nhgt.extent());
    }

}
