package ch.epfl.alpano.summitTest;
import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.summit.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class GazetteTest {

    @Test
    public void testconvert() {
        int deg = 53;
        int min = 45;
        int sec = 34;
      //  assertEquals(Math.toRadians(53.7594), GazetteerParser.convert(deg,min,sec), 1E-5);
    }
    @Test
    public void testreader(){
    String line ="   7:56:53 46:35:33  2472  H1 C02 D0 LAUBERHORN";
  //  Summit s= new Summit("LAUBERHORN", new GeoPoint(GazetteerParser.convert(7,56,53),GazetteerParser.convert(46, 35, 33)), 2472 );
    
   // assertEquals(s.toString(), GazetteerParser.reader(line).toString());
    }
    
    @Test
    public void testreader2(){
    String line = "   8:00:19 46:34:39  3970  H1 C02 E0 EIGER";
    //Summit s= new Summit("EIGER", new GeoPoint(GazetteerParser.convert(8,0,19),GazetteerParser.convert(46, 34, 39)), 3970 );
    //assertEquals(s, GazetteerParser.reader(line));
    }
    
    @Test
    public void testreader3(){
    String line ="   7:59:01 46:34:38  2663  H1 C02 E0 ROTSTOCK";
   // Summit s= new Summit("ROTSTOCK", new GeoPoint(GazetteerParser.convert(7,59,01),GazetteerParser.convert(46, 34, 38)), 2663 );
  //  assertEquals(s, GazetteerParser.reader(line));
    }
    
    @Test
    public void testreader4(){
        String lines ="   7:59:01 46:34:38  2663  H1 C02 E0 ROTSTOCK /n   8:00:19 46:34:39  3970  H1 C02 E0 EIGER ";
      //  Summit s1= new Summit("ROTSTOCK", new GeoPoint(GazetteerParser.convert(7,59,01),GazetteerParser.convert(46, 34, 38)), 2663 );

     //   Summit s= new Summit("EIGER", new GeoPoint(GazetteerParser.convert(8,0,19),GazetteerParser.convert(46, 34, 39)), 3970 );
        for (int i=0; i<2;++i){
            
        }
        
       
        
        
        
    }
    
    
    

}
