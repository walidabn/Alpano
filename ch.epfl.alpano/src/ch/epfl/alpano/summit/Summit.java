package ch.epfl.alpano.summit;

import ch.epfl.alpano.GeoPoint;

import static java.util.Objects.requireNonNull;

/**
 * This class is here to represent summits
 * 
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */
public final class Summit {
    private final String name;
    private final GeoPoint position;
    private final int elevation;

    /**
     * A constructor of Summit that constructs it with its name, its position
     * and its elevation
     * 
     * @param name
     *            The name of the summit
     * @param position
     *            The geographic position of the summit
     * @param elevation
     *            The elevation of the summit
     */
    public Summit(String name, GeoPoint position, int elevation) {

        this.name = requireNonNull(name);
        this.position = requireNonNull(position);
        this.elevation = elevation;

    }

    /**
     * A getter for the name of the summit
     * 
     * @return The name of the summit used during the construction of the object
     */
    public String name() {
        return name;
    }

    /**
     * A getter for the position of the summit
     * 
     * @return The GeoPoint of the summit used during the construction of the
     *         object
     */
    public GeoPoint position() {
        return position;
    }

    /**
     * A getter for the elevation of the summit
     * 
     * @return The name of the summit used during the construction of the object
     */
    public int elevation() {
        return elevation;
    }

    /**
     * A redefinition of toString for Summit
     * 
     * @return a string composed of the name,the position and the elevation of
     *         the summit for example, to the object representing Eiger, it
     *         would be : EIGER (8.0053,46.5775) 3970
     */

    @Override
    public String toString() {
        return (name + " " + position.toString() + " " + elevation);
    }

}
