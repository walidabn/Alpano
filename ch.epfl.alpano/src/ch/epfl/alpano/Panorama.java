package ch.epfl.alpano;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

/**
 * This class represents a Panorama
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */
public final class Panorama {

    private final PanoramaParameters parameters;
    private final float[] distanceArray;
    private final float[] longitudeArray;
    private final float[] latitudeArray;
    private final float[] elevationArray;
    private final float[] slopeArray;

    /**
     * A private constructor of Panorama using PanoramaParameters and 5 arrays
     * of floats for the distances, latitudes,longitudes, elevations and slopes
     * 
     * @param parameters
     *            The parameters desired for the panorama, an instance of
     *            PanoramaParameters
     * @param distanceArray
     *            an Array of floats representing the distances to each point of
     *            the panorama from the Observer's Position
     * @param longitudeArray
     *            an Array of floats representing the longitudes of all the
     *            points in the panorama
     * @param latitudeArray
     *            an Array of floats representing the latitudes of all the
     *            points in the panorama
     * @param elevationArray
     *            an Array of floats representing the elevations of all the
     *            points in the panorama
     * @param slopeArray
     *            an Array of floats representing the slopes of all the points
     *            in the panorama
     */
    private Panorama(PanoramaParameters parameters, float[] distanceArray,
            float[] longitudeArray, float[] latitudeArray,
            float[] elevationArray, float[] slopeArray) {

        this.parameters = requireNonNull(parameters);
        this.distanceArray = requireNonNull(distanceArray);
        this.longitudeArray = requireNonNull(longitudeArray);
        this.latitudeArray = requireNonNull(latitudeArray);
        this.elevationArray = requireNonNull(elevationArray);
        this.slopeArray = requireNonNull(slopeArray);
        

    }

    /**
     * A getter for the PanoramaParameters of this Panorama
     * 
     * @return the parameters of this panorama
     */
    public PanoramaParameters parameters() {

        return parameters;
    }

    /**
     * Gets the distance float value at the linear (x,y) index of the
     * distanceArray
     * 
     * @param x
     *            the width coordinate of the pixel in the image of the panorama
     * @param y
     *            the height coordinate of the pixel in the image of the
     *            panorama Throws IndexOutOfBoundsException if the sample index
     *            (x,y) is not valid
     * @return the distance contained in the Array of distances corresponding to
     *         the index (x,y) (a float)
     */
    public float distanceAt(int x, int y) {

        checkValidityOfParameters( x,  y);
        return distanceArray[parameters.linearSampleIndex(x, y)];

    }

    /**
     * Gets the longitude float value at the linear (x,y) index of the
     * longitudeArray
     * 
     * @param x
     *            the width coordinate of the pixel in the image of the panorama
     * @param y
     *            the height coordinate of the pixel in the image of the
     *            panorama Throws IndexOutOfBoundsException if the sample index
     *            (x,y) is not valid
     * @return the longitude contained in the Array of longitudes corresponding
     *         to the index (x,y) (a float)
     */
    public float longitudeAt(int x, int y) {
        if (!parameters.isValidSampleIndex(x, y)) {
            throw new IndexOutOfBoundsException();
        }

        return longitudeArray[parameters.linearSampleIndex(x, y)];

    }

    /**
     * Gets the latitude float value at the linear (x,y) index of the
     * latitudeArray
     * 
     * @param x
     *            the width coordinate of the pixel in the image of the panorama
     * @param y
     *            the height coordinate of the pixel in the image of the
     *            panorama Throws IndexOutOfBoundsException if the sample index
     *            (x,y) is not valid
     * @return the latitude contained in the Array of latitudes corresponding to
     *         the index (x,y) (a float)
     */
    public float latitudeAt(int x, int y) {
        checkValidityOfParameters(x, y);

        return latitudeArray[parameters.linearSampleIndex(x, y)];
    }

    /**
     * Gets the elevation float value at the linear (x,y) index of the
     * elevationArray
     * 
     * @param x
     *            the width coordinate of the pixel in the image of the panorama
     * @param y
     *            the height coordinate of the pixel in the image of the
     *            panorama Throws IndexOutOfBoundsException if the sample index
     *            (x,y) is not valid
     * @return the elevation contained in the Array of elevations corresponding
     *         to the index (x,y) (a float)
     */
    public float elevationAt(int x, int y) {
        checkValidityOfParameters( x,  y);

        return elevationArray[parameters.linearSampleIndex(x, y)];
    }

    /**
     * Gets the slope float value at the linear (x,y) index of the slopeArray
     * 
     * @param x
     *            the width coordinate of the pixel in the image of the panorama
     * @param y
     *            the height coordinate of the pixel in the image of the
     *            panorama Throws IndexOutOfBoundsException if the sample index
     *            (x,y) is not valid
     * @return the slope contained in the Array of slopes corresponding to the
     *         index (x,y) (a float)
     */
    public float slopeAt(int x, int y) {
        checkValidityOfParameters( x,  y);

        return slopeArray[parameters.linearSampleIndex(x, y)];
    }

    /**
     * Gets the distance float value at the linear (x,y) index of the
     * distanceArray, returns d if the linear (x,y) index is invalid
     * 
     * @param x
     *            the width coordinate of the pixel in the image of the panorama
     * @param y
     *            the height coordinate of the pixel in the image of the
     *            panorama
     * @param d
     *            the value returned if the sample index (x,y) is not valid
     * @return d (a float)
     */
    public float distanceAt(int x, int y, float d) {
        if (!parameters.isValidSampleIndex(x, y)) {
            return d;
        }

        return distanceArray[parameters.linearSampleIndex(x, y)];
    }
    
    private void checkValidityOfParameters(int x, int y){
        if (!parameters.isValidSampleIndex(x, y)) {
            throw new IndexOutOfBoundsException();
        }
         }

    /**
     * Here begins the Builder for the Panorama
     * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
     *
     */
    public final static class Builder {
        private final PanoramaParameters parameters;
        private final int sizeOfArray;
        private float[] distanceArray;

        private float[] longitudeArray;
        private float[] latitudeArray;
        private float[] elevationArray;
        private float[] slopeArray;
        private boolean isBuilt = false;

        /**
         * A constructor of Builder using PanoramaParameters as argument
         * 
         * @param parameters
         *            the parameters of the Panorama (an instance of
         *            PanoramaParameters)
         */
        public Builder(PanoramaParameters parameters) {
            this.parameters = requireNonNull(parameters);
            this.sizeOfArray= parameters.width() * parameters.height();
            distanceArray = new float[sizeOfArray];
            Arrays.fill(distanceArray, Float.POSITIVE_INFINITY);

            longitudeArray = new float[sizeOfArray];

            latitudeArray = new float[sizeOfArray];

            elevationArray = new float[sizeOfArray];

            slopeArray = new float[sizeOfArray];
        }

        /**
         * Sets a distance float value at the linear (x,y) index of the
         * distanceArray throws IllegalStateException if the Panorama was
         * already built once
         * 
         * @param x
         *            the width coordinate of a pixel in the image of the
         *            Panorama
         * @param y
         *            the height coordinate of a pixel in the image of the
         *            Panorama
         * @param distance
         *            a float corresponding to the distance for the point (x,y)
         * @return this Builder with the distance set in distanceArray at the
         *         linear index of (x,y)
         */
        public Builder setDistanceAt(int x, int y, float distance) {
            checkIfBuilt();

            checkIfValidIndex(parameters.isValidSampleIndex(x, y));

            distanceArray[parameters.linearSampleIndex(x, y)] = distance;
            return this;

        }

        /**
         * Sets a longitude float value at the linear (x,y) index of the
         * longitudeArray throws IllegalStateException if the Panorama was
         * already built once
         * 
         * @param x
         *            the width coordinate of a pixel in the image of the
         *            Panorama
         * @param y
         *            the height coordinate of a pixel in the image of the
         *            Panorama
         * @param longitude
         *            a float corresponding to the longitude for the point (x,y)
         * @return this Builder with the longitude set in longitudeArray at the
         *         linear index of (x,y)
         */
        public Builder setLongitudeAt(int x, int y, float longitude) {
            checkIfBuilt();

            checkIfValidIndex(parameters.isValidSampleIndex(x, y));

            longitudeArray[parameters.linearSampleIndex(x, y)] = longitude;
            return this;

        }

        /**
         * Sets a latitude float value at the linear (x,y) index of the
         * latitudeArray throws IllegalStateException if the Panorama was
         * already built once
         * 
         * @param x
         *            the width coordinate of a pixel in the image of the
         *            Panorama
         * @param y
         *            the height coordinate of a pixel in the image of the
         *            Panorama
         * @param latitude
         *            a float corresponding to the latitude for the point (x,y)
         * @return this Builder with the latitude set in latitudeArray at the
         *         linear index of (x,y)
         */
        public Builder setLatitudeAt(int x, int y, float latitude) {
            checkIfBuilt();

            checkIfValidIndex(parameters.isValidSampleIndex(x, y));

            latitudeArray[parameters.linearSampleIndex(x, y)] = latitude;
            return this;

        }

        /**
         * Sets an elevation float value at the linear (x,y) index of the
         * elevationArray throws IllegalStateException if the Panorama was
         * already built once
         * 
         * @param x
         *            the width coordinate of a pixel in the image of the
         *            Panorama
         * @param y
         *            the height coordinate of a pixel in the image of the
         *            Panorama
         * @param elevation
         *            a float corresponding to the elevation for the point (x,y)
         * @return this Builder with the elevation set in elevationArray at the
         *         linear index of (x,y)
         * 
         */
        public Builder setElevationAt(int x, int y, float elevation) {
            checkIfBuilt();

            checkIfValidIndex(parameters.isValidSampleIndex(x, y));

            elevationArray[parameters.linearSampleIndex(x, y)] = elevation;
            return this;
        }

        /**
         * Sets a slope float value at the linear (x,y) index of the slopeArray
         * throws IllegalStateException if the Panorama was already built once
         * 
         * @param x
         *            the width coordinate of a pixel in the image of the
         *            Panorama
         * @param y
         *            the height coordinate of a pixel in the image of the
         *            Panorama
         * @param slope
         *            a float corresponding to the slope for the point (x,y)
         * @return this Builder with the slope set in slopeArray at the linear
         *         index of (x,y)
         * 
         */
        public Builder setSlopeAt(int x, int y, float slope) {

            checkIfBuilt();

            checkIfValidIndex(parameters.isValidSampleIndex(x, y));

            slopeArray[parameters.linearSampleIndex(x, y)] = slope;
            return this;
        }

        /**
         * Builds a Panorama throws IllegalStateException if the Panorama was
         * already built once
         * 
         * @return a Panorama built with parameters and 5 arrays of
         *         distance,longitude,latitude,elevation and slope
         */
        public Panorama build() {
            checkIfBuilt();
            isBuilt = true;
           return new Panorama(parameters, distanceArray, longitudeArray,
                    latitudeArray, elevationArray, slopeArray);
       
                
        }

        /**
         * Checks if the Panorama was built, throws IllegalStateException if it
         * is the case
         */
        private void checkIfBuilt() {
            if (isBuilt) {
                throw new IllegalStateException();
            }
        }

        /**
         * Throws IndexOutOfBoundsException if the boolean passed is false (used
         * to check if a index is valid in this class)
         * 
         * @param b
         *            a boolean to see if the index is valid
         */
        private void checkIfValidIndex(boolean b) {
            if (!b) {
                throw new IndexOutOfBoundsException();
            }
        }

    }

}
