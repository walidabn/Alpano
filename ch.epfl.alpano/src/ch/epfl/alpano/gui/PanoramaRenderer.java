package ch.epfl.alpano.gui;

import ch.epfl.alpano.Panorama;
import static java.util.Objects.requireNonNull;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 *         This interface enables us to render an image for a panorama in a
 *         certain way to we want through a painter, all that through the method
 *         renderPanorama
 */
public interface PanoramaRenderer {

    /**
     * Builds an Image corresponding to the panorama, in the way that we want
     * through the painter (in HSB or gray tones for instance)
     * 
     * @param p
     *            a panorama for which we want an image
     * @param painter
     *            the painter that we want for that image (for example, either
     *            with the HSB model or in gray tones)
     *            
     * Throws NullPointerException if either the panorama or the painter are null;           
     * @return the image corresponding to the panorama with the painter defined
     */
    public static Image renderPanorama(Panorama p, ImagePainter painter) {

        requireNonNull(p);
        requireNonNull(painter);

        WritableImage wi = new WritableImage(p.parameters().width(),
                p.parameters().height());
        for (int x = 0; x < p.parameters().width(); ++x) {
            for (int y = 0; y < p.parameters().height(); ++y) {

                wi.getPixelWriter().setColor(x, y, painter.colorAt(x, y));

            }
        }
        return wi;

    }

}
