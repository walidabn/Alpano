package ch.epfl.alpano.gui;

import java.util.ArrayList;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;
import static java.util.Collections.unmodifiableList;
import static java.util.Arrays.asList;

import javafx.util.StringConverter;

/**
 * This class here, which extends StringConverter<Integer> , is here to ease the
 * conversion between Strings and Integers. The conversion is made through a
 * list of Strings passed to the constructor in the form of a variable number of
 * arguments : a String at the position n is converted to the Integer n, and
 * vice versa (we chose an ArrayList, but since the Strings are added at the end
 * of the list, an ArrayList does not take a lot of time to do the addings, and
 * to use a get method it is more efficient with an ArrayList than with a
 * LinkedList)
 * 
 * 
 * 
 * @author Walid Ben Naceur (273765) Ridha Chahed (270618)
 *
 */
public final class LabeledListStringConverter extends StringConverter<Integer> {

    /**
     * The List of Strings which is here for the correspondances between the
     * String at position n and the integer n
     */
    private final List<String> lbsc;

    /**
     * A Constructor that takes a variable number of Strings as argument and
     * builds an immutable ArrayList corresponding the a list of all the Strings
     * 
     * @param args The Strings of the list in the order that we want them to be in the List
     * 
     * Throws NullPointerException if one of the Strings is empty
     */
    public LabeledListStringConverter(String... args) {

        List<String> copyList = new ArrayList<String>();

        
        copyList=asList(args);

        this.lbsc = unmodifiableList(new ArrayList<String>(copyList));

    }

    /* 
     * An overriding of the fromString method inherited from StringConverter<Integer>
     * @param string : the String for which we want the index in our List lbsc
     * Throws NullPointerException if string is null
     * Throws NoSuchElementException if the string does not exist in our List lbsc
     * Returns the Integer corresponding to the index of string in our List if List contains string
     */
    @Override
    public Integer fromString(String string) {
        requireNonNull(string);

        if (lbsc.contains(string)) {
            return lbsc.indexOf(string);
        } else {
            throw new NoSuchElementException();
        }
    }

    /* 
     * An Overriding of the toString method inherited from StringConverter <Integer> : 
     * @param numberDigit : the index of the element in our List for which we want the corresponding String 
     * Returns the empty String if numberDigit is null
     * Returns the String of lbsc at the index of the Integer entered 
     * Throws NoSuchElementException if the index is invalid 
     */
    @Override
    public String toString(Integer numberDigit) {
        if  (numberDigit==null){
            return "";
        }
         else if (numberDigit < 0 || numberDigit >= lbsc.size()) {
            throw new NoSuchElementException();
        }
        else {
            return lbsc.get(numberDigit);
        }
    
    }
}
