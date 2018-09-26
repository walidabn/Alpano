package ch.epfl.alpano.gui;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class LabeledListStringConverterTest {

    
    
    @Test
    public void test() {
     PanoramaUserParameters pup = new PanoramaUserParameters(68087,470085,1380,162,27,300,2500,800,0);
     PanoramaUserParameters aj = PredefinedPanoramas.ALPES_DU_JURA;
     assertEquals(pup,aj);
    }
    
}
