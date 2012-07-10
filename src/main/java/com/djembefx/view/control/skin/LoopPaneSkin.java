package com.djembefx.view.control.skin;

import com.djembefx.model.Loop;
import com.djembefx.view.control.LoopControl;
import com.djembefx.view.control.LoopPane;
import com.djembefx.view.control.LoopPaneLayout;
import com.google.inject.Inject;
import com.google.inject.Provider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;

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

    final ObjectProperty<SelectionModel<Loop>> loopSelectionModel;

    final ChangeListener<Loop> selectedLoopListener = new ChangeListener<Loop>() {
        @Override
        public void changed(ObservableValue<? extends Loop> observableValue, Loop loop, Loop loop1) {
            if (loopControlMap.containsKey(loop)) {
                LoopControl control = loopControlMap.get(loop);
                control.getStyleClass().remove(LoopControlSkin.Css.SELECTED);
                control.getStyleClass().add(LoopControlSkin.Css.DEFAULT);
            }
            if (loopControlMap.containsKey(loop1)) {
                LoopControl control = loopControlMap.get(loop1);
                control.getStyleClass().remove(LoopControlSkin.Css.DEFAULT);
                control.getStyleClass().add(LoopControlSkin.Css.SELECTED);
            }
        }
    };

    @Inject
    public LoopPaneSkin(LoopPane loopPane,
                        Provider<LoopPaneNode> loopPaneNodeProvider,
                        Provider<LoopControl> loopControlProvider,
                        ObjectProperty<SelectionModel<Loop>> loopSelectionModel) {
        this.loopPane = loopPane;
        this.loopPaneNodeProvider = loopPaneNodeProvider;
        this.loopControlProvider = loopControlProvider;
        this.loopSelectionModel = loopSelectionModel;
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
        loopSelectionModel.addListener(new ChangeListener<SelectionModel<Loop>>() {
            @Override
            public void changed(ObservableValue<? extends SelectionModel<Loop>> observableValue, SelectionModel<Loop> loopSelectionModel, SelectionModel<Loop> loopSelectionModel1) {
                if (loopSelectionModel != null) {
                    loopSelectionModel.selectedItemProperty().removeListener(selectedLoopListener);
                }
                if (loopSelectionModel1 != null) {
                    loopSelectionModel1.selectedItemProperty().addListener(selectedLoopListener);
                }
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
            node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    loopSelectionModel.get().clearSelection();
                }
            });
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
            final LoopControl loopControl = loopControlProvider.get();
            loopControl.setLoop(loop);
            loopControlMap.put(loop, loopControl);
            loopControl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    loopSelectionModel.get().select(loopControl.getLoop());
                    mouseEvent.consume();
                }
            });
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
