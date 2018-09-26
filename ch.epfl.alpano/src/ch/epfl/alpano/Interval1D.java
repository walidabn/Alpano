package ch.epfl.alpano;

import java.util.Objects;

/**
 * This class is here to represent 1-dimentionnal Intervals of integers
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */

public final class Interval1D {

    private final int includedFrom;
    private final int includedTo;

    /**
     * A constructor for the Interval1D of ints taking as arguments the lower
     * bound includedFrom and the upper bound includedTo
     * 
     * @param includedFrom
     *            the lower bound of the interval (an int)
     * @param includedTo
     *            the upper bound of the interval (an int)
     * 
     *            throws IllegalArgumentException if includedFrom is bigger than
     *            includedTo
     */
    public Interval1D(int includedFrom, int includedTo) {

        this.includedFrom = includedFrom;
        this.includedTo = includedTo;
        Preconditions.checkArgument(includedFrom <= includedTo,
                "error: includedFrom is bigger than includedTo");

    }

    /**
     * A getter for the lower bound includedFrom
     * 
     * @return the lower bound of the object Interval1D
     */
    public int includedFrom() {
        return includedFrom;
    }

    /**
     * A getter for the upper bound includedTo
     * 
     * @return the upper bound of the object Interval1D
     */
    public int includedTo() {
        return includedTo;
    }

    /**
     * Determines if this Interval1D contains the integer v
     * 
     * @param v
     *            an integer
     * @return if that integer v is in the interval [includedFrom, includedTo]
     *         (the object Interval1D)
     */
    public boolean contains(int v) {
        return ((v <= includedTo()) && (v >= includedFrom()));
    }

    /**
     * Determines the size of this Interval1D
     * 
     * @return the size of the interval [includedFrom, includedTo] (the object
     *         Interval1D)
     */
    public int size() {
        return includedTo() - includedFrom() + 1;
    }

    /**
     * Determines the size of the intersection between this and an other
     * Interval1D object
     * 
     * @param that
     *            an other Interval1D
     * @return the number of elements common between this object and that
     */

    public int sizeOfIntersectionWith(Interval1D that) {
        int a = Math.max(includedFrom(), that.includedFrom());
        int b = Math.min(includedTo(), that.includedTo());
        return (b - a >= 0) ? b - a + 1 : 0;
    }

    /**
     * Constructs the bounding union of this and an other Interval1D object
     * 
     * @param that
     *            an other Interval1D object
     * 
     * @return an Interval1D that has for lower bound the minimum of the lower
     *         bounds between this and that, and for upper bound the maximum of
     *         the upper bounds between this and that
     */
    public Interval1D boundingUnion(Interval1D that) {
        return new Interval1D(Math.min(includedFrom(), that.includedFrom()),
                Math.max(includedTo(), that.includedTo()));
    }

    /**
     * Determines if this is unionable with an other Interval1D object
     * 
     * @param that
     *            an other Interval1D object
     * @return if this Interval1D is unionable with that
     */

    public boolean isUnionableWith(Interval1D that) {

        return ((size() + that.size()
                - sizeOfIntersectionWith(that)) == (boundingUnion(that)
                        .size()));
    }

    /**
     * Constructs the union of this and an other Interval1D if they are
     * unionable
     * 
     * @param that
     *            an other Interval1D object
     * @return the union between this and that throws IllegalArgumentException
     *         if this and that are not unionable
     */

    public Interval1D union(Interval1D that) {
        Preconditions.checkArgument(isUnionableWith(that),
                " Not unionable intervals ");

        return boundingUnion(that);

    }

    /**
     * A redefinition of the method equals for two instances of Interval1D : 2
     * instances of Interval1D are equal if they have the same upper bounds and
     * the same lower bounds, otherwise they aren't equal
     * 
     * @param thatO
     *            an other object to be compared to this
     * @return true if that object is of the same class as this and has the same
     *         lower bound and same upper bound, false in all other cases
     * 
     */
    @Override
    public boolean equals(Object thatO) {
        return ((thatO instanceof Interval1D)
                && includedFrom() == ( ((Interval1D) thatO).includedFrom())
                && includedTo() == (((Interval1D) thatO).includedTo()));
    }

    /**
     * An overriding of the method hashCode
     * @return the hash code of this object
     */

    @Override
    public int hashCode() {
        return Objects.hash(includedFrom(), includedTo());
    }

    /**
     * A redefinition of toString for Interval1D 
     * @return a String in the form [2..4] for the Interval1D going from 2 to 4
     */
    @Override
    public String toString() {
        return "[" + includedFrom() + ".." + includedTo() + "]";
    }

}
