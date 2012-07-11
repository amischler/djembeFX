package com.djembefx.view.control.skin;

import com.djembefx.model.Loop;
import com.djembefx.view.control.LoopControl;
import com.djembefx.view.control.LoopPane;
import com.djembefx.view.control.LoopPaneLayout;
import com.google.inject.Inject;
import com.google.inject.Provider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 21:11
 */
public class LoopPaneSkin implements Skin<LoopPane> {

    private static class LoopPaneNode extends Region {

        private ObservableList<LoopControl> loopControls = FXCollections.observableList(new LinkedList<LoopControl>());

        private ObjectProperty<LoopPaneLayout> loopPaneLayout = new SimpleObjectProperty<LoopPaneLayout>();

        LoopPaneNode() {
            Circle center = new Circle();
            center.setFill(Color.RED);
            center.setRadius(5);
            getChildren().add(center);
            setPrefSize(4000, 4000);
            loopPaneLayout.addListener(new ChangeListener<LoopPaneLayout>() {
                @Override
                public void changed(ObservableValue<? extends LoopPaneLayout> observableValue, LoopPaneLayout loopPaneLayout, LoopPaneLayout loopPaneLayout1) {
                    requestLayout();
                }
            });
        }

        @Override
        protected void layoutChildren() {
            loopPaneLayoutProperty().get().layout(loopControls);
        }

        public void addLoopControl(final LoopControl loopControl) {
            getChildren().add(loopControl);
            loopControls.add(loopControl);
            /**loopControl.hoverProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean1) {
                    loopControl.toFront();
                }
            }); */
        }

        public void removeLoopControl(LoopControl loopControl) {
            getChildren().remove(loopControl);
            loopControls.remove(loopControl);
        }

        public ObjectProperty<LoopPaneLayout> loopPaneLayoutProperty() {
            return loopPaneLayout;
        }

    }

    private final LoopPane loopPane;

    private LoopPaneNode node;

    final Provider<LoopPaneNode> loopPaneNodeProvider;

    final Provider<LoopControl> loopControlProvider;

    private ListChangeListener<Loop> loopsChangeListener;

    private ChangeListener<ObservableList<Loop>> loopsPropertyListener;

    private Map<Loop, LoopControl> loopControlMap = new HashMap<Loop, LoopControl>();

    final ObjectProperty<SelectionModel<Loop>> loopSelectionModel;

    final ChangeListener<Loop> selectedLoopListener = new ChangeListener<Loop>() {
        @Override
        public void changed(ObservableValue<? extends Loop> observableValue, Loop loop, Loop loop1) {
            if (loopControlMap.containsKey(loop)) {
                LoopControl control = loopControlMap.get(loop);
                control.getStyleClass().remove(LoopControlSkin.Css.SELECTED);
            }
            if (loopControlMap.containsKey(loop1)) {
                LoopControl control = loopControlMap.get(loop1);
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
            node.removeLoopControl(loopControlMap.get(loop));
            loopControlMap.remove(loop);
        }
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
            node.addLoopControl(loopControl);
        }
    }

    private LoopPaneNode createNode() {
        LoopPaneNode loopPaneNode = loopPaneNodeProvider.get();
        loopPaneNode.loopPaneLayoutProperty().bind(loopPane.loopPanelLayoutProperty());
        return loopPaneNode;
    }

    @Override
    public void dispose() {
        loopPane.getLoops().removeListener(loopsChangeListener);
        loopPane.loopsProperty().removeListener(loopsPropertyListener);
        loopPane.loopPanelLayoutProperty().unbind();
    }
}
