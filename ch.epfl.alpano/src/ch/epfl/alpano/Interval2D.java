package ch.epfl.alpano;

import java.util.Objects;
import static java.util.Objects.requireNonNull;

/**
 * This Class represents BiDimentionnal intervals of integers
 * @author Walid Ben Naceur(273765) Ridha Chahed(270618)
 *
 */

public final class Interval2D {
    private final Interval1D iX;
    private final Interval1D iY;

    /**
     * A constructor for Interval2D that creates it as the cartesian product of
     * two Interval1D : iX and iY
     * 
     * @param iX
     *            The first 1 dimensional interval of our 2 dimensional interval
     * @param iY
     *            The second 1 dimensional interval of our 2 dimensional
     *            interval used for the Cartesian product throws
     *            NullPointerException if either iX or iY is null
     */
    public Interval2D(Interval1D iX, Interval1D iY) {
       
        
        this.iX = requireNonNull(iX);
        this.iY = requireNonNull(iY);

    }

    /**
     * A getter for the Interval1D iX
     * 
     * @return iX, the first interval of the Cartesian product to make this
     *         Interval2D
     */

    public Interval1D iX() {
        return  iX;
    }

    /**
     * A getter for the Interval1D iY
     * 
     * @return iY, the second interval of the Cartesian product to make this
     *         Interval2D
     */
    public Interval1D iY() {
        return iY;
    }

    /**
     * Determines if this Interval2D contains the (x,y) couple
     * 
     * @param x
     *            the first element of the (x,y) couple
     * @param y
     *            the second element of the (x,y) couple
     * @return if the Interval2D contains the (x,y) couple
     */
    public boolean contains(int x, int y) {
        return (iX().contains(x) && iY().contains(y));
    }

    /**
     * Determines the size of this Interval2D
     * 
     * @return the number of couples of integers in this Interval2D
     */
    public int size() {
        return iX().size() * iY().size();
    }

    /**
     * Determines the sizeOfIntersection of this Interval2D with an other
     * instance of Interval2D
     * 
     * @param that
     *            an other Interval2D object
     * @return the number of couples in common between this and that
     */
    public int sizeOfIntersectionWith(Interval2D that) {
        int a = this.iX.sizeOfIntersectionWith(that.iX);
        int b = this.iY.sizeOfIntersectionWith(that.iY);
        return a * b;

    }

    /**
     * Constructs the Interval2D corresponding to the bounding union of this and
     * an other instance of Interval2D
     * 
     * @param that
     *            an other Interval2D object
     * @return the bounding union of this interval and that
     */
    public Interval2D boundingUnion(Interval2D that) {
        return new Interval2D(iX().boundingUnion(that.iX()),
                iY().boundingUnion(that.iY()));
    }

    /**
     * Determines if this is unionable with an other instance of Interval2D
     * 
     * @param that
     *            an other Interval2D object
     * @return a boolean saying if this and that are unionable
     */

    public boolean isUnionableWith(Interval2D that) {
        return ((size() + that.size()
                - sizeOfIntersectionWith(that)) == boundingUnion(that).size());
    }

    /**
     * Constructs the union of this and an other instance of Interval2D if they
     * are unionable
     * 
     * @param that
     *            an other Interval2D
     * @return the union of this and that if they are unionable
     * @throws IllegalArgumentException
     *             if the this and that are not unionable
     */
    public Interval2D union(Interval2D that) {
        Preconditions.checkArgument(this.isUnionableWith(that),
                "Those two intervals are not unionable");
        return this.boundingUnion(that);
    }

    /**
     * A redefinition of equals for Interval2D
     * 
     * @param thatO
     *            an other object
     * @return true if thatO is an Interval2D containing the same elements as
     *         this, false in all other cases
     * 
     */

    @Override
    public boolean equals(Object thatO) {
        return (thatO instanceof Interval2D
                && iX.equals(((Interval2D) thatO).iX()))
                && iY.equals(((Interval2D) thatO).iY());
    }

    /**
     * A redefinition of hashCode for Interval2D
     * 
     * @return the hash code of this Interval2D
     */

    @Override
    public int hashCode() {
        return Objects.hash(iX, iY);
    }

    /**
     * A redefinition of toString for Interval2D
     * 
     * @return a String saying the two intervals used to build this Interval2D
     *         for example, [2..4]×[7..9] for the Cartesian product of the
     *         Interval1D [2..4] and [7..9]
     * 
     */
    @Override

    public String toString() {
        return iX.toString()+"×"+iY.toString();
    }

}
