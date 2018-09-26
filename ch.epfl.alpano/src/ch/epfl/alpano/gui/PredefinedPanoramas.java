package ch.epfl.alpano.gui;

/**
 * @author Walid Ben Naceur (273765), Ridha Chahed (270618)
 *
 *         This interface serves as examples for 6 predefined panoramas :
 *         Niesen, Alpes Du Jura,Mont Racine,Finsteraarhorn,tour De Sauvabelin
 *         and plage Du Pelican (pelican beach in english)
 *
 */

public interface PredefinedPanoramas {

    public final static PanoramaUserParameters NIESEN = new PanoramaUserParameters(
            76500, 467300, 600, 180, 110, 300, 2500, 800, 0);
    public final static PanoramaUserParameters ALPES_DU_JURA = new PanoramaUserParameters(
            68087, 470085, 1380, 162, 27, 300, 2500, 800, 0);
    public final static PanoramaUserParameters MONT_RACINE = new PanoramaUserParameters(
            68200, 470200, 1500, 135, 45, 300, 2500, 800, 0);
    public final static PanoramaUserParameters FINSTERAARHORN = new PanoramaUserParameters(
            81260, 465374, 4300, 205, 20, 300, 2500, 800, 0);
    public final static PanoramaUserParameters TOUR_DE_SAUVABELIN = new PanoramaUserParameters(
            66385, 465353, 700, 135, 100, 300, 2500, 800, 0);
    public final static PanoramaUserParameters PELICAN_BEACH = new PanoramaUserParameters(
            65728, 465132, 380, 135, 60, 300, 2500, 800, 0);

}
