package ch.epfl.alpano.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.Panorama;
import ch.epfl.alpano.PanoramaComputer;
import ch.epfl.alpano.PanoramaParameters;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.DiscreteElevationModel;
import ch.epfl.alpano.dem.HgtDiscreteElevationModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.stage.Stage;
import static ch.epfl.alpano.summit.GazetteerParser.readSummitsFrom;
/*

public final class JavaFXMain extends Application {

    final static File HGT_FILE1 = new File("N46E006.hgt");
    final static File HGT_FILE2 = new File("N46E007.hgt");
    final static File HGT_FILE3 = new File("N46E008.hgt");
    final static File HGT_FILE4 = new File("N46E009.hgt");
    final static File HGT_FILE5 = new File("N45E006.hgt");
    final static File HGT_FILE6 = new File("N45E007.hgt");
    final static File HGT_FILE7 = new File("N45E008.hgt");
    final static File HGT_FILE8 = new File("N45E009.hgt");


    final static int IMAGE_WIDTH = 500;
    final static int IMAGE_HEIGHT = 200;

    final static double ORIGIN_LON = Math.toRadians(7.65);
    final static double ORIGIN_LAT = Math.toRadians(46.73);
    final static int ELEVATION = 600;
    final static double CENTER_AZIMUTH = Math.toRadians(180);
    final static double HORIZONTAL_FOV = Math.toRadians(60);
    final static int MAX_DISTANCE = 100_000;

    final static PanoramaParameters PARAMS1 = new PanoramaParameters(
            new GeoPoint(ORIGIN_LON, ORIGIN_LAT), ELEVATION, CENTER_AZIMUTH,
            HORIZONTAL_FOV, MAX_DISTANCE, IMAGE_WIDTH, IMAGE_HEIGHT);
    final static PanoramaParameters ETAPE= PredefinedPanoramas.ALPES_DU_JURA.panoramaParameters();
    final static PanoramaParameters PARAMS = PredefinedPanoramas.NIESEN
            .panoramaDisplayParameters();

    public static void main(String[] args) {
        launch(args);
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

    @Override
    public void start(Stage primaryStage) throws Exception {
            
            ContinuousElevationModel cDEM = discretMNTFormation();
            Panorama p = new PanoramaComputer(cDEM).computePanorama(ETAPE);
            Labelizer labelizer = new Labelizer(cDEM,readSummitsFrom(new File("alps.txt")));

            List<Node> formes = new ArrayList<>();
            formes = labelizer.labels(ETAPE);
            Platform.exit();
        }
    
}
*/