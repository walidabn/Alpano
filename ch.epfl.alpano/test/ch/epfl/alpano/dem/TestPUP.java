package ch.epfl.alpano.dem;

import static org.junit.Assert.*;
import ch.epfl.alpano.gui.*;

import org.junit.Test;

public class TestPUP {

    @Test
    public void test() {
        PanoramaUserParameters niesen = PredefinedPanoramas.NIESEN; 
        
       int value=  niesen.get(UserParameter.OBSERVER_LONGITUDE);
        int value2 = niesen.observerLongitude();
        assertEquals(value,value2,0);
        
        
    }
    
    @Test
    public void testequals() {
        PanoramaUserParameters niesen = PredefinedPanoramas.NIESEN; 
        
      PanoramaUserParameters niesen2 = new PanoramaUserParameters(76500,467300,600, 180,110,300,2500,800,0);
        assertTrue(niesen.equals(niesen2));
        
        
    }

}
