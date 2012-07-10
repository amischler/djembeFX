package com.djembefx.application.init;

import com.google.inject.Inject;
import javafx.scene.Scene;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 10/07/12
 * Time: 06:42
 */
public class CssLoaderInitializer implements Initializer{

    @Inject
    Scene scene;

    @Override
    public void init() {
        scene.getStylesheets().add("com/djembefx/css/main.css");
    }

}
