package com.djembefx.view;

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
        Circle center = new Circle();
        center.setFill(Color.GREEN);
        center.setRadius(5);
        getChildren().add(center);
    }
}
