package ch.epfl.alpano.gui;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import static ch.epfl.alpano.gui.PredefinedPanoramas.*;

import ch.epfl.alpano.gui.PanoramaParametersBean;

/*
public final class BeansUse extends Application {

    public BeansUse() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        // TODO Auto-generated method stub
        PanoramaParametersBean bean =
                new PanoramaParametersBean(NIESEN);
              ObjectProperty<Integer> prop =
                bean.observerLatitudeProperty();

              prop.addListener((o, oV, nV) ->
                System.out.printf("  %d -> %d (%s)%n", oV, nV, o));
              System.out.println("set to 1");
              prop.set(1);
              System.out.println("set to 2");
              prop.set(2);

              Platform.exit();
            }
          }
        
        
        
        
        
        
        
        
    


*/