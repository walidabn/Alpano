package ch.epfl.alpano.gui;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import ch.epfl.alpano.PanoramaParameters;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.ElevationProfile;

import ch.epfl.alpano.summit.Summit;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import static java.util.Collections.sort;
import static java.util.Objects.requireNonNull;
import static java.util.Collections.unmodifiableList;
import static ch.epfl.alpano.Math2.angularDistance;
import static ch.epfl.alpano.Math2.firstIntervalContainingRoot;
import static ch.epfl.alpano.PanoramaComputer.rayToGroundDistance;

/**
 * @author Walid Ben Naceur (273765), Ridha Chahed (270618) This immutable class
 *         is here to represent a labelizer for the panorama : it is this class
 *         which is responsible for putting the labels on the labelizable
 *         summits.
 */
public final class Labelizer {
    private final ContinuousElevationModel mnt;
    private final List<Summit> list;
    private final static int FIRST_STEP = 64;
    private final static int TOLERANCE_STEP = 200;
    private final static int PIXEL_LIMIT = 170;
    private final static int PIXEL_SPACING=20;
    private final static int LINE_STARTING_DISTANCE=22;
    private final static int ROTATION_CONSTANT = -60;

    /**
     * This constructor of Labelizer takes as arguments a Continuous elevation
     * Model and a Summit List (often obtained through GazetteerParser's
     * readSummitFrom method)
     * 
     * @param mnt
     *            A Continuous Elevation Model
     * @param list
     *            a List of Summits Throws NullPointerException if either of the
     *            arguments is null
     */
    public Labelizer(ContinuousElevationModel mnt, List<Summit> list) {
        this.mnt = requireNonNull(mnt);
        this.list = unmodifiableList(requireNonNull(new ArrayList<>(list)));
    }

    /**
     * This method returns a List of Nodes (which correspond to our labels of
     * the Panorama), given PanoramaParameters
     * 
     * @param parameters
     *            the PanoramaParameters of the Panorama that we want labeled
     * @return A List of labels for the panorama (represented by a List of
     *         Nodes) Throws NullPointerException if parameters is Null
     */
    public List<Node> labels(PanoramaParameters parameters) {
        requireNonNull(parameters);
        List<Node> listOfNode = new ArrayList<>();
        List<VisibleSummit> listVisibleSummit = visibleSummit(parameters);
        Iterator<VisibleSummit> it = listVisibleSummit.iterator();
        while (it.hasNext()) {
            if (it.next().y() <= PIXEL_LIMIT) {
                it.remove();
            }
        }
        sort(listVisibleSummit);
        if(listVisibleSummit.isEmpty()|| parameters.width()<=2*PIXEL_SPACING){
            return Collections.emptyList();
        }
        int yl=listVisibleSummit.get(0).y() - LINE_STARTING_DISTANCE;
        
        BitSet setWidth = new BitSet();
        setWidth.set(PIXEL_SPACING, parameters.width() - PIXEL_SPACING, true);
      
        for (VisibleSummit s : listVisibleSummit) {
      
            if (setWidth.get(s.x(), s.x() + PIXEL_SPACING).cardinality() == PIXEL_SPACING) {
                
                Text text = new Text(s.toString());
                text.getTransforms().addAll(new Translate(s.x(), yl),
                        new Rotate(ROTATION_CONSTANT, 0, 0));
                Line line = new Line();
                line.setStartX(s.x());
                line.setStartY(yl + 2);
                line.setEndX(s.x());
                line.setEndY(s.y());

                listOfNode.add(text);
                listOfNode.add(line);
                setWidth.clear(s.x(), s.x() + PIXEL_SPACING);
                
            }
        }
        return listOfNode;
    }

    /**
     * This Private method is here to get a List of the Summits that are
     * considered visible
     * 
     * @param parameter
     *            the parameters of the panorama used to determine which summit
     *            is Visible
     * @return a List of VisibleSummit corresponding to the instance of
     *         PanoramaParameters parameter Throws NullPointerException is
     *         parameter is Null
     */
    private List<VisibleSummit> visibleSummit(PanoramaParameters parameter) {
        requireNonNull(parameter);
        List<VisibleSummit> listVisibleSummit = new ArrayList<>();
        for (Summit s : list) {
            
            double azimuth = parameter.observerPosition()
                    .azimuthTo(s.position());
            if (parameter.horizontalFieldOfView() / 2d < Math.abs(
                    angularDistance(azimuth, parameter.centerAzimuth()) )) {
                continue;
            }
            // We determine here if the Summit is in the limits determined by
            // the center Azimuth and the Horizontal Field Of View

            double distance = parameter.observerPosition()
                    .distanceTo(s.position());
            if (distance > parameter.maxDistance()) {

                continue;
            }

            // we determine here if the Summit is in the limits determined by
            // the maximal distance of Visibility of the Parameters

            ElevationProfile profil = new ElevationProfile(mnt,
                    parameter.observerPosition(), azimuth, distance);

            double ray = -rayToGroundDistance(profil,
                    parameter.observerElevation(), 0).applyAsDouble(distance);

            double teta = Math.atan2(ray, distance);
            if (parameter.verticalFieldOfView() / 2d < Math.abs(teta)) {

                continue;
            }
            // We determine here if the Summit is in the limits determined by
            // the Vertical Field Of View

            DoubleUnaryOperator function = rayToGroundDistance(profil,
                    parameter.observerElevation(), ray / distance);
            double rayon = firstIntervalContainingRoot(function, 0, distance,
                    FIRST_STEP);
            if (rayon < distance - TOLERANCE_STEP) {
                continue;
            }
            // We determine if a ray going from the observer and directed
            // towards the Summit does not reach the the field before going
            // through the horizontal distance that separates the observer's
            // position to the summit's position, with a tolerance of 200 m

            int elevation = s.elevation();
            int x = (int) Math.round(parameter.xForAzimuth(azimuth));
            int y = (int) Math.round(parameter.yForAltitude(teta));
            listVisibleSummit.add(new VisibleSummit(s, x, y, elevation));
            // If the summit has passed all the 4 checks, then it is considered
            // as a VisibleSummit and is added to our List
            
         
            
            
            
            
           
        
            
            
            
        }
        
        
        return listVisibleSummit;

    }

    /**
     * @author Walid Ben Naceur (273765), Ridha Chahed (270618)
     * This static private class
     *         is here to represent visible Summits, in order to simplify the
     *         use of the method visibleSummit from Labelizer
     */
    private final static class VisibleSummit
            implements Comparable<VisibleSummit> {
        private final Summit summit;
        private final int x;
        private final int y;
        private final int elevation;

        /**
         * A VisibleSummit is defined by it's x and y pixel index position on
         * the panorama, by the summit it represents, and the summit's elevation
         * 
         * @param summit
         *            the Summit represented by a VisibleSummit
         * @param x
         *            the width pixel coordinate of the image that defines the
         *            visible summit
         * @param y
         *            the height pixel coordinate of the image that defines the
         *            visible summit
         * @param elevation
         *            the elevation of the summit (useful for sorting we will
         *            do)
         */
        public VisibleSummit(Summit summit, int x, int y, int elevation) {
            this.summit = requireNonNull(summit);
            this.x = x;
            this.y = y;
            this.elevation = elevation;
        }

        /**
         * A private getter for the summit attribute
         * 
         * @return the summit attribute of a VisibleSummit
         */
        private Summit summit() {
            return summit;
        }

        /**
         * A private getter for the x attribute
         * 
         * @return the x attribute of a VisibleSummit
         */
        private int x() {
            return x;
        }

        /**
         * A private getter for the y attribute
         * 
         * @return the y attribute of a VisibleSummit
         */
        private int y() {
            return y;
        }

        /**
         * A private getter for the elevation attribute
         * 
         * @return the elevation attribute of the summit
         */
        private int elevation() {
            return elevation;
        }

        /*
         * An overriding of compareTo inherited from Comparable, where we
         * compare the VisibleSummits through their y coordinate and elevation
         */
        @Override
        public int compareTo(VisibleSummit that) {
            requireNonNull(that);
            if (y() != that.y()) {
                return Integer.compare(y(), that.y());
            } else {
                return Integer.compare(that.elevation(), elevation());
            }
        }

       
        

        /*
         * An overriding of the toString method inherited from Object
         */
        @Override
        public String toString() {
            return summit().name() + " (" + summit().elevation() + "m)";
        }

    }
}