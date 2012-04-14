package com.djembefx.view;

import com.google.inject.Inject;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.layout.BorderPane;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 18:34
 */
public class MainParent extends BorderPane {

    private final LoopPane loopPane;

    @Inject
    public MainParent(LoopPane loopPane, LoopPaneSkin loopPaneSkin) {
        this.loopPane = loopPane;
        loopPane.setSkin(loopPaneSkin);
        create();
    }

    private void create() {
        setPrefSize(640, 480);
        ScrollPane scrollPane = ScrollPaneBuilder.create().pannable(true).content(loopPane).build();
        setCenter(scrollPane);
    }
}
