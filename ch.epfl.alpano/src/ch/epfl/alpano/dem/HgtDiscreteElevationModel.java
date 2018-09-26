package ch.epfl.alpano.dem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel.MapMode;

import ch.epfl.alpano.Interval1D;
import ch.epfl.alpano.Interval2D;
import ch.epfl.alpano.Preconditions;

/**
 * This class is here to represent a discrete elevation model for heights
 * 
 * @author Walid Ben Naceur (273765) Ridha Chahed(270618)
 *
 */
public final class HgtDiscreteElevationModel implements DiscreteElevationModel {

    private final Interval2D extent;
    private  ShortBuffer tab;
    private final FileInputStream stream;
    private final static int FILE_LENGTH = 25934402;
    private final static int NAME_LENGTH = 11;

    /**
     * A constructor for a HGTDiscreteElevationModel that takes as an argument a
     * file
     * 
     * @param file
     *            An HGT file that should be read
     * @throws IOException
     *             throws IllegalArgumentException if the file has not the
     *             correct length, or if its name is invalid
     */
    public HgtDiscreteElevationModel(File file) {

        try {
            String name = file.getName();

            Preconditions.checkArgument(name.length() == NAME_LENGTH,
                    " The file has a name that has not 11 characters");
            char ns = name.charAt(0);
            String latitude = name.substring(1, 3);

            int latitudeInt;
            try {
                latitudeInt = Integer.parseInt(latitude);
            } catch (NumberFormatException e) {

                throw new IllegalArgumentException(
                        " The file's name is invalid");
            }
            char we = name.charAt(3);
            String longitude = name.substring(4, 7);

            int longitudeInt;
            try {
                longitudeInt = Integer.parseInt(longitude);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        " The file's name is invalid");
            }

            String extension = name.substring(7, 11);
            Preconditions.checkArgument(ns == 'N' || ns == 'S',
                    "This file does not begin by a S or a N");
            Preconditions.checkArgument(we == 'E' || we == 'W',
                    "The file does not have a E or a W as second letter");
            Preconditions.checkArgument(extension.equals(".hgt"),
                    "The file's extension is incorrect");
            Preconditions.checkArgument(file.length() == FILE_LENGTH,
                    "This file has an incorrect length");
            
            // We check that the file is valid
            stream = new FileInputStream(file);
            
         
            tab = stream.getChannel().map(MapMode.READ_ONLY, 0, FILE_LENGTH)
                    .asShortBuffer();
            Interval1D first;
            Interval1D second;
            if (sign(we) == 1) {
                first = new Interval1D(
                        sign(we) * (longitudeInt) * SAMPLES_PER_DEGREE,
                        sign(we) * (longitudeInt + 1) * SAMPLES_PER_DEGREE);
            } else {
                first = new Interval1D(
                        sign(we) * (longitudeInt + 1) * SAMPLES_PER_DEGREE,
                        sign(we) * (longitudeInt) * SAMPLES_PER_DEGREE);

            }
            
            // depending on the letter (W or E) on the file, we build first differently
            if (sign(ns) == 1) {
                second = new Interval1D(
                        sign(ns) * (latitudeInt) * SAMPLES_PER_DEGREE,
                        sign(ns) * (latitudeInt + 1) * SAMPLES_PER_DEGREE);

            } else {
                second = new Interval1D(
                        sign(ns) * (latitudeInt + 1) * SAMPLES_PER_DEGREE,
                        sign(ns) * (latitudeInt) * SAMPLES_PER_DEGREE);
            }
            // Depending on the letter (N or S) on the file, we build second differently
            extent = new Interval2D(first, second);

        } catch (IOException | NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    /*
     * An redefinition of the method extent from DiscreteElevationModel returns
     * the Interval2D corresponding to the extent covered by all the points in
     * the HGT file
     */

    @Override
    public Interval2D extent() {
        return extent;
    }

    /*
     * An overriding of the method elevationSample from DiscreteElevationModel
     * 
     * @param x the longitude's index point for which we look its height in the
     * HGT file
     * 
     * @param y the latitude's index point for which we look its height in the
     * HGT file
     * 
     * @returns the height of the point of index(x,y) throws
     * IllegalArgumentException if the point of index (x,y) is not covered in
     * the extent of all the points in the HGT file
     */
    @Override
    public double elevationSample(int x, int y) {
        Preconditions.checkArgument(extent().contains(x, y));
        int a = x - extent.iX().includedFrom();
        int b = extent.iY().includedTo() - y;
        return tab.get(a + b * (SAMPLES_PER_DEGREE + 1));
    }

    /**
     * Represents by an integer if, given a certain character, it should be 1 or
     * -1
     * 
     * @param c
     *            a character that determines which int is returned by the
     *            method
     * @return 1 if the character is N or E, -1 if the character is S or W, and
     *         1 otherwise
     */
    private int sign(char c) {
        switch (c) {
        case 'N':
        case 'E':
            return 1;
        case 'S':
        case 'W':
            return -1;
        default:
            throw new IllegalArgumentException();
        }
    }

    /*
     * an overriding of the close method that closes the FileInputStream stream
     * and affects null to the ShortBuffer tab
     */
    @Override
    public void close() throws Exception {
        stream.close();
        tab=null;
    }

}