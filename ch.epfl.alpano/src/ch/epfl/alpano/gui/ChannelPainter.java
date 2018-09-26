package ch.epfl.alpano.gui;

import java.util.function.DoubleUnaryOperator;
import static java.util.Objects.requireNonNull;

import static ch.epfl.alpano.Math2.floorMod;
import ch.epfl.alpano.Panorama;

/**
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 * 
 *This functional interface represents the way we can paint a channel (one of the components for an image) depending on the redefinition of valueAt
 */
@FunctionalInterface
public interface ChannelPainter {

    /**
     * Depending on our redefinition of what a value is, returns a float that is the value at the coordinates (x,y);
     * @param x an int that corresponds to the width coordinate of the panorama for which we want the value
     * @param y an int that correspond to the height coordinate of the panorama for which we want the value
     * @return the value at (x,y) depending on the way we define what a value is with our lambda expression
     */
    public float valueAt(int x, int y);
    
    
    /**
     * Determines the maximum distance from the neighbor of a of a panorama or considers it to be 0 if in a certain direction the neighbor does not exist
     * @param p a panorama that we use to determine the distances between the 4 points that surrounds it
     * Throws NullPointerException if p is null 
     * @return a ChannelPainter (a function) that maps to the coordinates (x,y) the maximum distance to one of its neighbor
     */
    public static ChannelPainter maxDistanceToNeighbors(Panorama p){
        requireNonNull(p);
      return (x,y)->  Math.max( 
              Math.max(p.distanceAt(x-1, y, 0), p.distanceAt(x+1, y, 0) ) ,
              Math.max(p.distanceAt(x, y-1, 0), p.distanceAt(x, y+1, 0) ) ) - p.distanceAt(x, y);
      
        
    }
    
   /**
    * This method lets us add the value returned by the ChannelPainter by cte
 * @param cte a float 
 * @return a ChannelPainter with cte added
 */
public default ChannelPainter add(float cte){
        return (x,y)->valueAt(x,y) + cte ;
    }
    
   /**
    * This method lets us substract cte to the value returned by the ChannelPainter 
 * @param cte a float 
 * @return a ChannelPainter with cte substracted
 */
public default ChannelPainter sub(float cte){
        return (x,y)->valueAt(x,y) - cte;
    }
    


/**
* This method lets us multiply the value returned by the ChannelPainter by cte
* @param cte a float 
* @return a ChannelPainter multiplied by cte
*/
public default ChannelPainter mul(float cte){
       return (x,y)->valueAt(x,y) * cte;
   }
   
   /**
    * This method lets divide add the value returned by the ChannelPainter by cte
    * Because of the float values, it cte is 0, we will get an Infinity, which is wanted in that case
 * @param cte a float 
 * @return a ChannelPainter divided by cte
 */
public default ChannelPainter div(float cte){
       return (x,y)->valueAt(x,y) / cte;
   }
   
   /**
    * This method maps, with the help of a DoubleUnaryOperator, the value at (x,y) with the operator we defined
 * @param op a DoubleUnaryOperator that is used to map the value at (x,y) to what we want the ChannelPainter to be
 * throws NullPointerException if op is null
 * @return a ChannelPainter with the operator op applied to it
 */
public default ChannelPainter map(DoubleUnaryOperator op){
       requireNonNull(op);
       return (x,y)-> (float) op.applyAsDouble(valueAt(x,y));
   }
   
   /**
 * @return A ChannelPainter with its value substracted to 1;
 */
public default ChannelPainter inverted(){
       return (x,y)-> 1-valueAt(x,y);
   }
   
   /** 
 * @return A ChannelPainter with the value at (x,y) brought back to the interval[0,1[ (the decimal part of it after a modulus 1)
 */
public default ChannelPainter cycling(){
       return (x,y)-> (float) floorMod(valueAt(x,y), 1);
   }

   /**
 * @return A ChannelPainter with the maximum value between 0, and the minimum value between the value at (x,y) and 1
 */
public default ChannelPainter clamped(){
       return (x,y)->Math.max(0, Math.min(valueAt(x,y),1));
       
       
   }
}
