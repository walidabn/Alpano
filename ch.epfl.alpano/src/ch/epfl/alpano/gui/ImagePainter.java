package ch.epfl.alpano.gui;

import javafx.scene.paint.Color;
import static java.util.Objects.requireNonNull;

/**
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 *  This functional interface represents the way we can paint an image depending on the redefinition of valueAt
 */
@FunctionalInterface
public interface ImagePainter {
    
    /**
     * Depending on our redefinition of what a value is, returns a Color that is the color value at the coordinates (x,y);
     * @param x the width coordinate (an int) of the panorama for which we will want the value of the Color
     * @param y the height coordinate (an int) of the panorama for which we will want the value of the Color
     * @return the Color value at the coordinates (x,y)
     */
    public Color colorAt(int x, int y);
    
    /**
     * This method builds an ImagePainter(a function) to paint an image with the HSB color model
     * @param hue a ChannelPainter defined to correspond to the hue channel for the image
     * @param saturation a ChannelPainter defined to correspond to the saturation for the image
     * @param brightness a ChannelPainter defined to correspond to the brightness for the image
     * @param opacity a ChannelPainter defined to correspond to the opacity for the image
     * Throws NullPointerException if either hue, saturation, opacity or brightness are null
     * @return an ImagePainter (a function to paint an image) with the HSB model
     */
    public static ImagePainter hsb(ChannelPainter hue, ChannelPainter saturation, ChannelPainter brightness, ChannelPainter opacity){
    
        requireNonNull(hue);
        requireNonNull(saturation);
        requireNonNull(brightness);
        requireNonNull(opacity);
        
        
        return (x,y)-> Color.hsb(hue.valueAt(x, y) ,saturation.valueAt(x, y),brightness.valueAt(x, y), opacity.valueAt(x, y));
        
        
    }
    
    /**
     * This method builds an ImagePainter(a function) to paint an image in gray tones
     * @param gray a ChannelPainter defined to correspond to the gray tone for an image in gray
     * @param opacity a ChanelPainter defined to correspond to the opacity of the image
     * Throws NullPointerException if either gray or opacity is null
     * @return an ImagePainter ( a function to paint an image) in gray tones
     */
    public static ImagePainter gray(ChannelPainter gray, ChannelPainter opacity){
        requireNonNull(gray);
        requireNonNull(opacity);
        return (x,y)-> Color.gray(gray.valueAt(x, y), opacity.valueAt(x, y));
        
    }
    

}
