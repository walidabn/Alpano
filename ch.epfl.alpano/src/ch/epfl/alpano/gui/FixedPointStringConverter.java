package ch.epfl.alpano.gui;

import javafx.util.StringConverter;
import static ch.epfl.alpano.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static java.math.RoundingMode.HALF_UP;
import java.math.BigDecimal;

/**
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618) This class here,
 *         which extends StringConverter<Integer> , is here to ease the
 *         conversion between Strings and Integers. The conversion from a String
 *         to an Integer is made by interpreting the String as a real number,
 *         and by rounding it Half_Up up with a scale defined through the attribute
 *         fixedPoint, then by deleting the floating point to get an Integer.
 *         To do so, we use the Class BigDecimal to ease the conversions.
 * 
 */
public final class FixedPointStringConverter extends StringConverter<Integer> {
    /**
     * A final attribute for this immutable class corresponding to the number of
     * decimals needed for the scale.
     */
    private final int fixedPoint;

    /**
     * A constructor for FixedPointStringConverter
     * 
     * @param fixedPoint
     *            an int which corresponds to the number of decimals we want for
     *            our conversions (must be strictly positive)
     * Throws IllegalArgumentException if fixedPoint is not stricly positive
     */
    public FixedPointStringConverter(int fixedPoint) {
        checkArgument(fixedPoint > 0, "fixedPoint must be stricly positive! ");
        this.fixedPoint = fixedPoint;
    }

    /*
     * An overriding from the method fromString inherited from StringConverter<Integer>
     * 
     * @param string : the string for which we want a conversion into an Integer
     * Throws NullPointerException if string is Null
     * Throws NumberFormatException if string is invalid during the instantiation of number
     * returns the Integer corresponding to the string without the decimals and with the correct rounding
     * 
     */
    @Override
    public Integer fromString(String string) {
        requireNonNull(string);
        BigDecimal number = new BigDecimal(string);

        return (number.setScale(fixedPoint, HALF_UP).movePointRight(fixedPoint)
                .stripTrailingZeros().intValueExact());

    }

    /* An overriding from the method fromString inherited from StringConverter<Integer>
     * 
     * @param object : the Integer for which we want a conversion into a String representing floating point decimal number
     * Throws NullPointerException if object is null
     * Throws NumberFormatException if object is invalid during the instantiation of number
     * returns the String corresponding to object with the decimals with a correct scale
     * 
     */
    @Override
    public String toString(Integer object) { 
        if (object == null){
            return "";
        }
        BigDecimal number = new BigDecimal(object);
        return (number.movePointLeft(fixedPoint).stripTrailingZeros()
                .toPlainString());

    }

}
