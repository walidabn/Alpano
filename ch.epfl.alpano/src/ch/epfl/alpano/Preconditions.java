package ch.epfl.alpano;

/**
 * An Interface used to check the validity of arguments
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */
public interface Preconditions {
    
  
/**
 * Checks if an argument has a certain caracteristic
 * @param b 
 *      the boolean checked to see if an IllegalArgumentException must be thrown
 *  throws IllegalArgumentException
 *  if the boolean b is false
 */
public static void checkArgument(boolean b){
        if(!b){
            throw new IllegalArgumentException();
        }
    }



  /**
   * Checks if the argument has a certain caracteristic, and displays a message
 * @param b
 *      the boolean checked to see if an IllegalArgumentException must be thrown
 * @param message
 *  The message thrown with the IllegalArgumentException
 *  throws IllegalArgumentException
 *  if the boolean b is false
 */
public static void checkArgument(boolean b, String message){
      if(!b){
          throw new IllegalArgumentException(message);
      }
      
      
  }








}
