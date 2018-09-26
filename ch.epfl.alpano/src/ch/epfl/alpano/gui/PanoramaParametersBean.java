package ch.epfl.alpano.gui;

import static javafx.application.Platform.runLater;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import static java.util.Objects.requireNonNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import static ch.epfl.alpano.gui.UserParameter.CENTER_AZIMUTH;
import static ch.epfl.alpano.gui.UserParameter.HEIGHT;
import static ch.epfl.alpano.gui.UserParameter.HORIZONTAL_FIELD_OF_VIEW;
import static ch.epfl.alpano.gui.UserParameter.MAX_DISTANCE;
import static ch.epfl.alpano.gui.UserParameter.OBSERVER_ELEVATION;
import static ch.epfl.alpano.gui.UserParameter.OBSERVER_LATITUDE;
import static ch.epfl.alpano.gui.UserParameter.OBSERVER_LONGITUDE;
import static ch.epfl.alpano.gui.UserParameter.SUPER_SAMPLING_EXPONENT;
import static ch.epfl.alpano.gui.UserParameter.WIDTH;

/**
 * @author Walid Ben Naceur (273765), Ridha Chahed (270618) This public final (but not immutable) class is
 *         a JavaFX bean which contain the PanoramaUserParameters. 
 * 
 */
public final class PanoramaParametersBean {
    private ObjectProperty<PanoramaUserParameters> parametersProperty;
    private Map<UserParameter, ObjectProperty<Integer>> map = new EnumMap<>(
            UserParameter.class);

    
    /**
     * This Constructor for the PanoramaParametersBean takes as argument an
     * instance of PanoramaUserParameters and builds the bean
     *
     * 
     * @param parametersProperty
     *            an instance of PanoramaUserParameters Throws
     *            NullPointerException if parametersProperty is null;
     *            Initializes the composite class attribute parametersProperty
     *            with the value passed in the constructor, and the map
     *            containing the individual properties through the values of
     *            parametersProperty
     */
    public PanoramaParametersBean(PanoramaUserParameters parameters) {
        requireNonNull(parameters);
        this.parametersProperty = new SimpleObjectProperty<PanoramaUserParameters>(
                parameters);
        for (UserParameter p : UserParameter.values()) {
            map.put(p, new SimpleObjectProperty<Integer>(
                    parameters.get(p)));
            map.get(p).addListener((b, oldValue,
                    newValue) -> runLater(this::synchronizeParameters));
        }

    }

    /**
     * A getter for parametersProperty (does not have a setter, because
     * parametersProperty is a ReadOnlyObjectProperty)
     * 
     * @return the ReadOnlyObjectProperty class attribute parametersProperty
     */
    public ReadOnlyObjectProperty<PanoramaUserParameters> parametersProperty() {
        return parametersProperty;
    }

    /**
     * A getter for observerLongitudeProperty
     * 
     * @return the ObjectProperty observerLongitudeProperty
     */
    public ObjectProperty<Integer> observerLongitudeProperty() {
        return map.get(OBSERVER_LONGITUDE);
    }

   

    /**
     * A getter for the observerLatitudeProperty
     * 
     * @return the ObjectProperty observerLatitudeProperty
     */
    public ObjectProperty<Integer> observerLatitudeProperty() {
        return map.get(OBSERVER_LATITUDE);
    }

   

    /**
     * A getter for the observerElevationProperty
     * 
     * @return the ObjectProperty observerElevationProperty
     */
    public ObjectProperty<Integer> observerElevationProperty() {
        return map.get(OBSERVER_ELEVATION);
    }

  

    /**
     * A getter for the centerAzimuthProperty
     * 
     * @return the ObjectProperty centerAzimuthProperty
     */
    public ObjectProperty<Integer> centerAzimuthProperty() {
        return map.get(CENTER_AZIMUTH);
    }

    

    /**
     * A getter for the horizontalFieldOfViewProperty
     * 
     * @return The ObjectProperty horizontalFieldOfViewProperty
     */
    public ObjectProperty<Integer> horizontalFieldOfViewProperty() {
        return map.get(HORIZONTAL_FIELD_OF_VIEW);
    }

   

    /**
     * A getter for the maxDistanceProperty
     * 
     * @return The ObjectProperty maxDistanceProperty
     */
    public ObjectProperty<Integer> maxDistanceProperty() {
        return map.get(MAX_DISTANCE);
    }

   
    /**
     * A getter for the widthProperty
     * 
     * @return The ObjectProperty widthProperty
     */
    public ObjectProperty<Integer> widthProperty() {
        return map.get(WIDTH);
    }

  
    /**
     * A getter for the heightProperty
     * 
     * @return the ObjectProperty heightProperty
     */
    public ObjectProperty<Integer> heightProperty() {
        return map.get(HEIGHT);
    }

    
    /**
     * A getter for the superSamplingExponentProperty
     * 
     * @return The ObjectProperty superSamplingExponentProperty
     */
    public ObjectProperty<Integer> superSamplingExponentProperty() {
        return map.get(SUPER_SAMPLING_EXPONENT);

    }

  

    /**
     * Because an instance of PanoramaParametersBean has 2 copies of each
     * property of PanoramaParameters (contained in map and in
     * parametersProperty) Those two versions of all the properties need to be
     * synchronized We create a new instance of PanoramaUserParameters in which
     * all the values of the current individual properties from map are put Then
     * we use this instance of PanoramaUserParameters as the new value contained
     * in parametersProperty, which are now sanitized And then, we set all the
     * values from the composite parametersProperty to all the individual
     * properties to guarantee the synchronization
     * 
     */
    private void synchronizeParameters() {
        Map<UserParameter, Integer> updatedMap = new EnumMap<>(
                UserParameter.class);

        for (Entry<UserParameter, ObjectProperty<Integer>> entry : map
                .entrySet()) {
            updatedMap.put(entry.getKey(), entry.getValue().get());
        }
        PanoramaUserParameters panoramaUserParameters = new PanoramaUserParameters(
                updatedMap);

        parametersProperty.set(panoramaUserParameters);

        for (UserParameter parameter : map.keySet()) {
            map.get(parameter).set(panoramaUserParameters.get(parameter));
        }
    }

}