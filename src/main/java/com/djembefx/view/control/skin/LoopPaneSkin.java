package com.djembefx.view.control.skin;

import com.djembefx.model.Loop;
import com.djembefx.view.control.LoopControl;
import com.djembefx.view.control.LoopPane;
import com.djembefx.view.control.LoopPaneLayout;
import com.google.inject.Inject;
import com.google.inject.Provider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Skin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 21:11
 */
public class LoopPaneSkin implements Skin<LoopPane> {

    private final LoopPane loopPane;

    private LoopPaneNode node;

    final Provider<LoopPaneNode> loopPaneNodeProvider;

    final Provider<LoopControl> loopControlProvider;

    private ListChangeListener<Loop> loopsChangeListener;

    private ChangeListener<ObservableList<Loop>> loopsPropertyListener;

    private Map<Loop, LoopControl> loopControlMap = new HashMap<Loop, LoopControl>();

    private ChangeListener<LoopPaneLayout> loopPaneLayoutChangeListener;

    @Inject
    public LoopPaneSkin(LoopPane loopPane,
                        Provider<LoopPaneNode> loopPaneNodeProvider,
                        Provider<LoopControl> loopControlProvider) {
        this.loopPane = loopPane;
        this.loopPaneNodeProvider = loopPaneNodeProvider;
        this.loopControlProvider = loopControlProvider;
        loopPane.loopsProperty().addListener(loopsPropertyListener = new ChangeListener<ObservableList<Loop>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<Loop>> observableValue, ObservableList<Loop> loops, ObservableList<Loop> loops1) {
                loops.removeListener(loopsChangeListener);
                loops1.addListener(loopsChangeListener);
                removeLoop(loops);
                addLoop(loops1);
            }
        });
        loopPane.loopPanelLayoutProperty().addListener(loopPaneLayoutChangeListener = new ChangeListener<LoopPaneLayout>() {
            @Override
            public void changed(ObservableValue<? extends LoopPaneLayout> observableValue, LoopPaneLayout loopPaneLayout, LoopPaneLayout loopPaneLayout1) {
                loopPaneLayout1.layout(loopControlMap.values());
            }
        });
    }

    @Override
    public LoopPane getSkinnable() {
        return loopPane;
    }

    @Override
    public Node getNode() {
        if (node == null) {
            node = createNode();
            loopPane.getLoops().addListener(loopsChangeListener = new ListChangeListener<Loop>() {

                @Override
                public void onChanged(Change<? extends Loop> change) {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            addLoop(change.getAddedSubList());
                        }
                        if (change.wasRemoved()) {
                            removeLoop(change.getRemoved());
                        }
                    }
                }
            });
            addLoop(loopPane.getLoops());
        }
        return node;
    }

    private void removeLoop(List<? extends Loop> removed) {
        for (Loop loop : removed) {
            node.getChildren().remove(loopControlMap.get(loop));
            loopControlMap.remove(loop);
        }
        loopPane.getLoopPaneLayout().layout(loopControlMap.values());
    }

    private void addLoop(List<? extends Loop> loops) {
        for (Loop loop : loops) {
            LoopControl loopControl = loopControlProvider.get();
            loopControl.setLoop(loop);
            loopControlMap.put(loop, loopControl);
            node.getChildren().add(loopControl);
        }
        loopPane.getLoopPaneLayout().layout(loopControlMap.values());
    }

    private LoopPaneNode createNode() {
        LoopPaneNode loopPaneNode = loopPaneNodeProvider.get();
        return loopPaneNode;
    }

    @Override
    public void dispose() {
        loopPane.getLoops().removeListener(loopsChangeListener);
        loopPane.loopsProperty().removeListener(loopsPropertyListener);
        loopPane.loopPanelLayoutProperty().removeListener(loopPaneLayoutChangeListener);
    }
}
