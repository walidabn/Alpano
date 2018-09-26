
package ch.epfl.alpano.gui;

import java.util.List;

import ch.epfl.alpano.Panorama;
import ch.epfl.alpano.PanoramaComputer;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.summit.Summit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import static java.util.Objects.requireNonNull;
import static javafx.collections.FXCollections.unmodifiableObservableList;
import static javafx.collections.FXCollections.observableArrayList;


/**
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 * 
 *         This public final (but not immutable) class is a JavaFX bean which
 *         contain the PanoramaComputer. Following the Beans conventions, each
 *         property class attribute has a getter and a setter (except for the
 *         readOnly properties which only have a getter).
 *
 *
 *
 */
public final class PanoramaComputerBean {

    private ObjectProperty<PanoramaUserParameters> parametersProperty;
    private ObjectProperty<Panorama> panoramaProperty;
    private ObjectProperty<Image> imageProperty;
    private ObjectProperty<ObservableList<Node>> labelsProperty;
    private ObservableList<Node> labels;

    private ImagePainter painter;
    private PanoramaComputer panoramaComputer;

    /**
     * A public constructor for PanoramaComputerBean that takes as arguments a
     * List of Summit and a ContinuousElevationModel
     * 
     * @param cdem
     *            The ContinuousElevationModel needed to get the informations to
     *            build the ElevationProfile for the computation of the Panorama
     *            (for this project, it will correspond the
     *            ContinuousElevationModel obtained from the union of 8 HGT
     *            files)
     * @param summitList
     *            A list of Summits, that will be useful in order to have the
     *            correct labels in our Panorama Throws NullPointerException if
     *            either cdem of summitList is null
     */
    public PanoramaComputerBean(ContinuousElevationModel cdem,
            List<Summit> summitList) {

        requireNonNull(summitList);
        requireNonNull(cdem);

        panoramaComputer = new PanoramaComputer(cdem);
        Labelizer labelizer = new Labelizer(cdem, summitList);
        parametersProperty = new SimpleObjectProperty<PanoramaUserParameters>();
        imageProperty = new SimpleObjectProperty<Image>();
        labelsProperty = new SimpleObjectProperty<ObservableList<Node>>(
                observableArrayList());
        this.labels = unmodifiableObservableList(labelsProperty.get());

        panoramaProperty = new SimpleObjectProperty<Panorama>();

        parametersProperty.addListener((e, oldParam, newParam) -> {

            panoramaProperty.set(panoramaComputer
                    .computePanorama(newParam.panoramaParameters()));
            ChannelPainter h = getPanorama()::distanceAt;
            h = h.div(100000).cycling().mul(360);
            ChannelPainter s = getPanorama()::distanceAt;
            s = s.div(200000).clamped().inverted();
            ChannelPainter b = getPanorama()::slopeAt;
            b = b.mul((float) (2 / Math.PI)).inverted().mul((float) 0.7)
                    .add((float) 0.3);
            ChannelPainter o = getPanorama()::distanceAt;
            o = o.map(u -> u == Float.POSITIVE_INFINITY ? 0 : 1);
            painter = ImagePainter.hsb(h, s, b, o);

            imageProperty.set(
                    PanoramaRenderer.renderPanorama(getPanorama(), painter));
            labelsProperty.get().setAll(labelizer.labels(
                    parametersProperty.get().panoramaDisplayParameters()));

        });

    }

    /**
     * A getter for the ObjectProperty parametersProperty
     * 
     * @return the ObjectProperty parametersProperty
     */
    public ObjectProperty<PanoramaUserParameters> parametersProperty() {
        return parametersProperty;
    }

    /**
     * A getter for the PanoramaUserParameters inside of parametersProperty :
     * 
     * @return the instance of PanoramaUserParameters inside of
     *         parametersProperty
     */
    public PanoramaUserParameters getParameters() {
        return parametersProperty().get();
    }

    /**
     * A setter for the parametersProperty
     * 
     * @param newParameters
     *            the new value inside of parametersProperty
     */
    public void setParameters(PanoramaUserParameters newParameters) {
        requireNonNull(newParameters);
        parametersProperty().set(newParameters);

    }

    /**
     * A getter for the panoramaProperty
     * 
     * @return the ReadOnlyObjectProperty panoramaProperty
     */
    public ReadOnlyObjectProperty<Panorama> panoramaProperty() {
        return panoramaProperty;
    }

    /**
     * A getter for the Panorama inside of panoramaProperty
     * 
     * @return the instance of Panorama inside of panoramaProperty
     */
    public Panorama getPanorama() {
        return panoramaProperty().get();
    }

    /**
     * A getter for the imageProperty
     * 
     * @return the ReadOnlyObjectProperty imageProperty
     */
    public ReadOnlyObjectProperty<Image> imageProperty() {
        return imageProperty;
    }

    /**
     * A getter for the Image inside of imageProperty
     * 
     * @return the instance of Image inside of imageProperty
     */
    public Image getImage() {
        return imageProperty().get();
    }

    /**
     * A getter for the labelsProperty represented by a property of an
     * ObservableList of Nodes
     * 
     * @return the ReadOnlyObjectProperty labelsProperty
     */
    public ReadOnlyObjectProperty<ObservableList<Node>> labelsProperty() {
        return labelsProperty;
    }

    /**
     * A getter for the labels, which are unmodifiable labels contained in the
     * labelsProperty
     * 
     * @return the class attribute labels
     */
    public ObservableList<Node> getLabels() {
        return labels;
    }

}