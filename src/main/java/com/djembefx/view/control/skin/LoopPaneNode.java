package com.djembefx.view.control.skin;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 21:12
 */
public class LoopPaneNode extends Pane {

    public LoopPaneNode() {
        setTranslateX(200);
        setTranslateY(200);
        Circle center = new Circle();
        center.setFill(Color.GREEN);
        center.setRadius(5);
        getChildren().add(center);
    }
}
