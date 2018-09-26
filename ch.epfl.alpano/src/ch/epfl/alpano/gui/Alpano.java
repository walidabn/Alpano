package ch.epfl.alpano.gui;

import static ch.epfl.alpano.Azimuth.toOctantString;
import static ch.epfl.alpano.summit.GazetteerParser.readSummitsFrom;
import static java.util.Objects.requireNonNull;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import ch.epfl.alpano.dem.ContinuousElevationModel;
import ch.epfl.alpano.dem.DiscreteElevationModel;
import ch.epfl.alpano.dem.HgtDiscreteElevationModel;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import static javafx.scene.paint.Color.color;
import static javafx.scene.text.TextAlignment.CENTER;
import static javafx.geometry.Pos.CENTER_RIGHT;
import static javafx.scene.layout.GridPane.setHalignment;
import static javafx.geometry.HPos.RIGHT;


/**
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618) This final class is
 *         the main heart of the Alpano Program : it contains the principal
 *         program of the project. It builds the graphical interface, then
 *         leaves the control to JavaFX, which will manage the events and inform
 *         the listeners attached to the interface until the moment when the
 *         user closes the application. It extends Application, like all JavaFX
 *         Applications, and thus, must override the start method inherited from
 *         Application.
 *
 */
public final class Alpano extends Application {

    private final PanoramaComputerBean panoramaComputerBean;
    private final PanoramaParametersBean panoramaParametersBean;
    private ObjectProperty<String> text;
    private final static int DECIMAL_OF_COORDINATE = 4;

    /**
     * This constructor initializes the panoramaComputerBean, the
     * panoramaParametersBean,and the text attributes
     * 
     * 
     * 
     * @throws IOException
     * @throws Exception
     *             Throws Exceptions regarding the use of discretMNTFormation
     *             and readSummitsFrom
     */
    public Alpano() throws IOException, Exception {
        this.panoramaComputerBean = new PanoramaComputerBean(
                discretMNTFormation(), readSummitsFrom(new File("alps.txt")));
        this.panoramaParametersBean = new PanoramaParametersBean(
                PredefinedPanoramas.ALPES_DU_JURA);
        text = new SimpleObjectProperty<String>();
    }

    /**
     * The usual method main when we deal with a JavaFX Application
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /*
     * An overriding of the start method inherited from Application
     *  We could have shortened the length of the start method, however we felt that it
     * was important to showcase all the elements of the Scene Graph of this
     * Application inside of this start method
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        new Alpano();
        ImageView panoView = imageViewManagement();
        Pane labelsPane = labelsPaneManagement();
        Text updateText = updateTextManagement();
        StackPane updateNotice = updateNoticeManagement(updateText);
        ScrollPane panoScrollPane = new ScrollPane();
        StackPane panoGroup = new StackPane(panoView, labelsPane);
        StackPane panoPane = new StackPane(panoScrollPane, updateNotice);
        labelsPane.setMouseTransparent(true);
        panoScrollPane.setContent(panoGroup);
        GridPane paramsGrid = paramsGridManagement();
        BorderPane root = new BorderPane(panoPane, null, null, paramsGrid,
                null);
        Scene scene = new Scene(root);

        primaryStage.setTitle("Alpano");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * In order to have a more readable code in the start method, the code
     * related to updateNotice is left in this method.
     * 
     * @return the stackPane which have the behavior expected for updateNotice
     */
    private StackPane updateNoticeManagement(Text updateText) {
        requireNonNull(updateText);
        StackPane updateNotice = new StackPane(updateText);
        updateNotice.setBackground(new Background(new BackgroundFill(
                color(1, 1, 1, 0.9), CornerRadii.EMPTY, Insets.EMPTY)));
        BooleanExpression ComputerNotEqParameters = panoramaComputerBean
                .parametersProperty()
                .isNotEqualTo(panoramaParametersBean.parametersProperty());
        updateNotice.visibleProperty().bind(ComputerNotEqParameters);
        updateNotice.setOnMouseClicked(e -> {
            panoramaComputerBean.setParameters(
                    panoramaParametersBean.parametersProperty().get());
        });
        return updateNotice;
    }

    /**
     * In order to have a more readable code in the start method, the code
     * related to labelsPane is left in this method.
     * 
     * @return the Pane which have the behavior expected for labelsPane
     */
    private Pane labelsPaneManagement() {

        Pane labelsPane = new Pane();
        labelsPane.prefHeightProperty()
                .bind(panoramaParametersBean.heightProperty());
        labelsPane.prefWidthProperty()
                .bind(panoramaParametersBean.widthProperty());
        Bindings.bindContent(labelsPane.getChildren(),
                panoramaComputerBean.getLabels());
        return labelsPane;
    }

    /**
     * This method is responsible for the creation of updateText
     * 
     * @return The text updateText
     */
    private Text updateTextManagement() {
        Text updateText = new Text(
                "Les paramètres du panorama ont changé.\n Cliquez ici pour mettre le dessin à jour.");
        updateText.setFont(new Font(40));
        updateText.setTextAlignment(CENTER);
        return updateText;

    }

    /**
     * Builds the ContinuousElevationModel needed in the computation of the
     * panoramas, created from the union of all the HGT files from N45E006 to
     * N45E009 and N46E006 to N46E009
     * 
     * @return the ContinousElevationModel created with the
     *         DiscreteElevationModel formed by the union of the 8 HGT files
     * @throws Exception
     *             Regarding the lecture of the HGT files
     */
    @SuppressWarnings("resource")
    private static ContinuousElevationModel discretMNTFormation()
            throws Exception {

        DiscreteElevationModel dDEM1 = new HgtDiscreteElevationModel(
                new File("N45E006.hgt"));
        DiscreteElevationModel dDEM2 = new HgtDiscreteElevationModel(
                new File("N45E007.hgt"));
        DiscreteElevationModel dDEM3 = new HgtDiscreteElevationModel(
                new File("N45E008.hgt"));
        DiscreteElevationModel dDEM4 = new HgtDiscreteElevationModel(
                new File("N45E009.hgt"));
        DiscreteElevationModel dDEM5 = new HgtDiscreteElevationModel(
                new File("N46E006.hgt"));
        DiscreteElevationModel dDEM6 = new HgtDiscreteElevationModel(
                new File("N46E007.hgt"));
        DiscreteElevationModel dDEM7 = new HgtDiscreteElevationModel(
                new File("N46E008.hgt"));
        DiscreteElevationModel dDEM8 = new HgtDiscreteElevationModel(
                new File("N46E009.hgt"));

        return new ContinuousElevationModel(
                (dDEM2.union(dDEM6)).union(dDEM1.union(dDEM5))
                        .union(dDEM3.union(dDEM7)).union(dDEM4.union(dDEM8)));
    }

    /**
     * This Method is responsible for the creation of the TextFields in
     * paramsGrid
     * 
     * 
     * @param property
     *            the ObjectProperty needed for the setting
     * 
     * @param colomnCount
     *            the column needed for the setting of the text
     * @param isCoordinate
     *            a boolean used to determine whether the textField will be
     *            needed for a longitude or latitude : in that case, we will use
     *            the FixedPointStringConverter
     * @return a TextField corresponding to the correct ObjectProperties
     */
    private TextField fieldSetting(ObjectProperty<Integer> property,
            int columnCount, boolean isCoordinate) {
        TextField field = new TextField();
        StringConverter<Integer> stringConverter = isCoordinate
                ? new FixedPointStringConverter(DECIMAL_OF_COORDINATE)
                : new IntegerStringConverter();
        TextFormatter<Integer> formatter = new TextFormatter<>(stringConverter);

        field.setTextFormatter(formatter);
        formatter.valueProperty().bindBidirectional(property);
        field.setAlignment(CENTER_RIGHT);
        field.setPrefColumnCount(columnCount);
        return field;
    }

    /**
     * We set the labels for the textFields through this method
     * 
     * @param nameOfLabel
     *            The name of the label
     * @return a Label aligned to the right with its correct name
     */
    private Label labelSetting(String nameOfLabel) {
        Label label = new Label(nameOfLabel);
        setHalignment(label, RIGHT);
        return label;
    }

    /**
     * In order to have a more readable code in the start method, the code
     * related to paramsGrid is left in this method.
     * 
     * @return the GridPane which have the behavior expected for paramsGrid
     */
    private GridPane paramsGridManagement() {
        GridPane paramsGrid = new GridPane();
        Label labelOfLatitude = labelSetting("Latitude (°):");

        TextField fieldOfLatitude = fieldSetting(
                panoramaParametersBean.observerLatitudeProperty(), 7, true);
        Label labelOfLongitude = labelSetting("Longitude (°):");

        TextField fieldOfLongitude = fieldSetting(
                panoramaParametersBean.observerLongitudeProperty(), 7, true);
        Label labelOfElevation = labelSetting("Altitude (m):");

        TextField fieldOfElevation = fieldSetting(
                panoramaParametersBean.observerElevationProperty(), 4, false);
        Label labelOfAzimuth = labelSetting("Azimut (°):");

        TextField fieldOfAzimuth = fieldSetting(
                panoramaParametersBean.centerAzimuthProperty(), 3, false);
        Label labelOfHorizontalFieldOfView = labelSetting("Angle de vue (°):");

        TextField fieldOfHorizontalFieldOfView = fieldSetting(
                panoramaParametersBean.horizontalFieldOfViewProperty(), 3,
                false);
        Label labelOfMaxDistance = labelSetting("Visibilité (km):");
        TextField fieldOfMaxDistance = fieldSetting(
                panoramaParametersBean.maxDistanceProperty(), 3, false);
        Label labelOfWidth = labelSetting("Largeur (px):");
        TextField fieldOfWidth = fieldSetting(
                panoramaParametersBean.widthProperty(), 4, false);
        Label labelOfHeight = labelSetting("Hauteur (px):");
        TextField fieldOfHeight = fieldSetting(
                panoramaParametersBean.heightProperty(), 4, false);
        Label labelOfExponent = labelSetting("Suréchantillonnage:");
        ChoiceBox<Integer> cb = new ChoiceBox<Integer>(
                FXCollections.observableArrayList(0, 1, 2));
        StringConverter<Integer> stringConverter = new LabeledListStringConverter(
                "non", "2×", "4×");
        cb.setConverter(stringConverter);
        cb.valueProperty().bindBidirectional(
                panoramaParametersBean.superSamplingExponentProperty());

        TextArea pointerInfo = new TextArea(text.get());
        pointerInfo.textProperty().bind(text);
        pointerInfo.setEditable(false);
        pointerInfo.setPrefRowCount(2);

        paramsGrid.addRow(0, labelOfLatitude, fieldOfLatitude, labelOfLongitude,
                fieldOfLongitude, labelOfElevation, fieldOfElevation);
        paramsGrid.addRow(1, labelOfAzimuth, fieldOfAzimuth,
                labelOfHorizontalFieldOfView, fieldOfHorizontalFieldOfView,
                labelOfMaxDistance, fieldOfMaxDistance);
        paramsGrid.addRow(2, labelOfWidth, fieldOfWidth, labelOfHeight,
                fieldOfHeight, labelOfExponent, cb);
        paramsGrid.add(pointerInfo, 7, 0, 1, 3);
        paramsGrid.setAlignment(Pos.CENTER);
        paramsGrid.setHgap(10);
        paramsGrid.setVgap(3);
        paramsGrid.setPadding(new Insets(7, 5, 5, 5));
        return paramsGrid;

    }

    /**
     * In order to have a more readable code in the start method, the code
     * related to imageView is left in this method.
     * 
     * @return the ImageView which have the behavior expected for imageView
     */
    private ImageView imageViewManagement() {
        ImageView panoView = new ImageView();
        panoView.setPreserveRatio(true);
        panoView.setSmooth(true);
        panoView.fitWidthProperty()
                .bind(panoramaParametersBean.widthProperty());
        panoView.imageProperty().bind(panoramaComputerBean.imageProperty());

        panoView.setOnMouseMoved(e -> {
            int sse = panoramaComputerBean.getParameters()
                    .superSamplingExponent();

            int x = (int) Math.scalb(e.getX(), sse);
            int y = (int) Math.scalb(e.getY(), sse);

            float latitude = (float) Math.toDegrees(
                    panoramaComputerBean.getPanorama().latitudeAt(x, y));
            float longitude = (float) Math.toDegrees(
                    panoramaComputerBean.getPanorama().longitudeAt(x, y));
            String latitudeString = (latitude > 0) ? "N" : "S";
            String longitudeString = (longitude > 0) ? "E" : "W";
            float azimuth = (float) panoramaComputerBean.getParameters()
                    .panoramaParameters().azimuthForX(x);

            String positionString = String.format((Locale) null,
                    "Position : %.4f°%s %.4f°%s%n", latitude, latitudeString,
                    longitude, longitudeString);
            String distanceString = String.format((Locale) null,
                    "Distance : %.1f km%n",
                    panoramaComputerBean.getPanorama().distanceAt(x, y)
                            / PanoramaUserParameters.CONVERSION_M_KM);
            String elevationString = String.format((Locale) null,
                    "Altitude : %.0f m%n",
                    panoramaComputerBean.getPanorama().elevationAt(x, y));
            String azimutString = String.format((Locale) null,
                    "Azimut : %.1f° (%s)  Elévation : %.1f°",
                    Math.toDegrees(azimuth),
                    toOctantString(azimuth, "N", "E", "S", "W"),
                    (float) Math.toDegrees(panoramaComputerBean.getParameters()
                            .panoramaParameters().altitudeForY(y)));

            text.set(positionString + distanceString + elevationString
                    + azimutString);

        });

        panoView.setOnMouseClicked(e -> {
            int sse = panoramaComputerBean.getParameters()
                    .superSamplingExponent();

            int x = (int) Math.scalb(e.getX(), sse);
            int y = (int) Math.scalb(e.getY(), sse);
            double latitude = Math.toDegrees(
                    panoramaComputerBean.getPanorama().latitudeAt(x, y));
            double longitude = Math.toDegrees(
                    panoramaComputerBean.getPanorama().longitudeAt(x, y));

            String qy = String.format((Locale) null, "mlat=%.5f&mlon=%.5f",
                    latitude, longitude);
            String fg = String.format((Locale) null, "map=15/%.5f/%.5f",
                    latitude, longitude);

            URI osmURI;
            try {
                osmURI = new URI("http", "www.openstreetmap.org", "/", qy, fg);
                java.awt.Desktop.getDesktop().browse(osmURI);
            } catch (IOException | URISyntaxException ex) {
                throw new Error(ex);
            }
        });
        return panoView;

    }

}