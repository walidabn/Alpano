package ch.epfl.alpano.summit;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;

import ch.epfl.alpano.GeoPoint;

import java.lang.Integer;


/**
 *  This class is here to parse data to get Summits
 * @author Walid Ben Naceur (273765), Ridha Chahed (270618)
 *
 */
public final class GazetteerParser {
   
    /**
     *  A private empty constructor for GazetteerParser
     */
    private  GazetteerParser() {
    }

    /**
     * Reads and builds a list of Summits from a file 
     * @param file a file containing a list of Summits
     * @return a List of all Summits read from the file
     * @throws IOException if there is a problem with the input/output of the file
     */
    public  static List<Summit> readSummitsFrom(File file) throws IOException {
        try (
            BufferedReader stream = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), StandardCharsets.US_ASCII))) {
            List<Summit> list = new ArrayList<Summit>();
            int i = 0;
            String l;
            while ((l = stream.readLine()) != null) {
                list.add(i, reader(l));
                ++i;
            }
            return unmodifiableList(list);
        } catch (Exception e) {
            throw new IOException();
        }
    }

    /**
     * Reads a Summit from a line 
     * @param line a line that is read
     * @return the Summit corresponding to that line
     */
    
    private static Summit reader(String line) {
      
        String longitude = line.substring(0, 10).trim();
        String[] longTab = longitude.split(":");
        double longRadian = convert(Integer.parseInt(longTab[0]),
                Integer.parseInt(longTab[1]), Integer.parseInt(longTab[2]));
        String latitude = line.substring(10, 20).trim();
        String[] latiTab = latitude.split(":");
        double latiRadian = convert(Integer.parseInt(latiTab[0]),
                Integer.parseInt(latiTab[1]), Integer.parseInt(latiTab[2]));
        
       
        int elevation = Integer.parseInt(line.substring(20, 25).trim());
        GeoPoint position = new GeoPoint(longRadian, latiRadian);
        String name = line.substring(36, line.length() ).trim();
        return new Summit(name, position, elevation);
    }

    /**
     * Converts an angle given in degrees*minutes*seconds into an angle in radians
     * @param degree the degrees component of the angle for which we want a conversion in radians
     * @param minute the minute component of the angle for which we want a conversion in radians
     * @param seconde the seconds component of the angle for which we want a conversion in radians
     * @return the conversion of the angle expressed in degrees,minutes,seconds in radians
     */
    
    private static double convert(int degree, int minute, int seconde) {
        return Math.toRadians(degree + minute * Math.pow(60, -1)
                + seconde * Math.pow(60, -2));
    }
    
   

}