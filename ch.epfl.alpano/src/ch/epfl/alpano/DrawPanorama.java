package ch.epfl.alpano;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import ch.epfl.alpano.gui.ChannelPainter;
import ch.epfl.alpano.gui.ImagePainter;
import ch.epfl.alpano.gui.PanoramaRenderer;
import ch.epfl.alpano.gui.PanoramaUserParameters;
import ch.epfl.alpano.gui.PredefinedPanoramas;
import ch.epfl.alpano.summit.GazetteerParser;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import static java.awt.image.BufferedImage.*;
import static java.lang.Math.*;

import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.Interval1D;
import ch.epfl.alpano.Interval2D;
import ch.epfl.alpano.dem.*;

/*
final class DrawPanorama {
    /*
    /*final static File HGT_FILE = new File("N46E006.hgt");

    final static int IMAGE_WIDTH = 500;
    final static int IMAGE_HEIGHT = 200;

    final static double ORIGIN_LON = Math.toRadians(7.65);
    final static double ORIGIN_LAT = Math.toRadians(46.73);
    final static int ELEVATION = 600;
    final static double CENTER_AZIMUTH = Math.toRadians(180);
    final static double HORIZONTAL_FOV = Math.toRadians(60);
    final static int MAX_DISTANCE = 100_000;
    
    

    final static PanoramaParameters PARAMS = PredefinedPanoramas.PLAGE_DU_PELICAN.panoramaParameters(); */
  //  final static  PanoramaParameters PARAMS = new PanoramaUserParameters(65000, 460785 ,1000,180,280,150,2500,800,0 ).panoramaDisplayParameters();
 
//    final static PanoramaParameters PARAMS = new PanoramaParameters(
         //   new GeoPoint(ORIGIN_LON, ORIGIN_LAT), ELEVATION, CENTER_AZIMUTH,
           // HORIZONTAL_FOV, MAX_DISTANCE, IMAGE_WIDTH, IMAGE_HEIGHT);
    /*
    final static File HGT_FILE1 = new File("N46E006.hgt");
    final static File HGT_FILE2 = new File("N46E007.hgt");
    final static File HGT_FILE3 = new File("N46E008.hgt");
    final static File HGT_FILE4 = new File("N46E009.hgt");
    final static File HGT_FILE5 = new File("N47E006.hgt");
    final static File HGT_FILE6 = new File("N47E007.hgt");
    final static File HGT_FILE7 = new File("N47E008.hgt");
    final static File HGT_FILE8 = new File("N47E009.hgt");

    final static int IMAGE_WIDTH = 500;
    final static int IMAGE_HEIGHT = 200;

    final static double ORIGIN_LON = toRadians(7.65);
    final static double ORIGIN_LAT = toRadians(46.73);
    final static int ELEVATION = 600;
    final static double CENTER_AZIMUTH = toRadians(180);
    final static double HORIZONTAL_FOV = toRadians(60);
    final static int MAX_DISTANCE = 100_000;

    final static PanoramaParameters PARAMS = PredefinedPanoramas.ALPES_DU_JURA.panoramaParameters();
 
    private static int gray(double v) {
        double clampedV = max(0, min(v, 1));
        int gray = (int) (255.9999 * clampedV);
        return (gray << 16) | (gray << 8) | gray;
      }
    
    public static void main(String[] as) throws Exception {
      
        
     
         ContinuousElevationModel cDEM = discretMNTFormation();
            Panorama p = new PanoramaComputer(cDEM).computePanorama(PARAMS);

            BufferedImage i = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT,
                    TYPE_INT_RGB);
            ChannelPainter gray = ChannelPainter.maxDistanceToNeighbors(p)
                    .sub(500).div(4500).clamped().inverted();

            ChannelPainter h =  p::distanceAt;
           h = h.div(100000).cycling().mul(360);
            ChannelPainter s =  p::distanceAt;
            s = s.div(200000).clamped().inverted();
            ChannelPainter b =  p::slopeAt;
            b = b.mul((float) (2 / Math.PI)).inverted().mul((float) 0.7)
                    .add((float) 0.3);
            ChannelPainter o =  p::distanceAt;
            o=o.map(u -> u == Float.POSITIVE_INFINITY ? 0 : 1);

            
            ChannelPainter distance = p::distanceAt;
            ChannelPainter opacity = distance
                    
                    .map(d -> d == Float.POSITIVE_INFINITY ? 0 : 1);

            ImagePainter l = ImagePainter.gray(gray, opacity);
            ImagePainter hsb= ImagePainter.hsb(h, s, b, o);
            Image e = PanoramaRenderer.renderPanorama(p, l);
            ImageIO.write(SwingFXUtils.fromFXImage(e, null), "png",
                    new File("alpes.png"));

            Image kappa = PanoramaRenderer.renderPanorama(p, hsb);
            ImageIO.write(SwingFXUtils.fromFXImage(kappa, null), "png",
                    new File("AlpesHGT46-6.png"));
        
    }
    

public static ContinuousElevationModel discretMNTFormation() throws Exception {
    DiscreteElevationModel dDEM1 = new HgtDiscreteElevationModel(HGT_FILE1);
        DiscreteElevationModel dDEM2 = new HgtDiscreteElevationModel(HGT_FILE2);
        DiscreteElevationModel dDEM3 = new HgtDiscreteElevationModel(HGT_FILE3);
        DiscreteElevationModel dDEM4 = new HgtDiscreteElevationModel(HGT_FILE4);
        DiscreteElevationModel dDEM5 = new HgtDiscreteElevationModel(HGT_FILE5);
        DiscreteElevationModel dDEM6 = new HgtDiscreteElevationModel(HGT_FILE6);
        DiscreteElevationModel dDEM7 = new HgtDiscreteElevationModel(HGT_FILE7);
        DiscreteElevationModel dDEM8 = new HgtDiscreteElevationModel(HGT_FILE8);
        
        return new ContinuousElevationModel(  (dDEM2.union(dDEM6)).union(dDEM1.union(dDEM5)).union(dDEM3.union(dDEM7)).union(dDEM4.union(dDEM8)) );
             //   . union((dDEM3).union(dDEM4)) .union ((dDEM7).union(dDEM8)) .union ((dDEM5).union(dDEM6)) );
    
    }
}

        
        
        
        
        
    */
 