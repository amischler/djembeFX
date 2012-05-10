package com.djembefx.view.control.skin;

import com.djembefx.model.Loop;
import com.djembefx.model.TimePosition;
import com.djembefx.view.control.LoopControl;
import com.djembefx.view.control.LoopPane;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.List;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 21:11
 */
public class LoopPaneSkin implements Skin<LoopPane> {

    private final LoopPane loopPane;

    private LoopPaneNode node;

    final ReadOnlyObjectProperty<TimePosition> currentTimePosition;

    final ObjectProperty<TimePosition> currentTimePositionFX = new SimpleObjectProperty<TimePosition>();

    final Provider<LoopPaneNode> loopPaneNodeProvider;

    final Provider<LoopControl> loopControlProvider;

    private ListChangeListener<Loop> loopsChangeListener;

    private ChangeListener<ObservableList<Loop>> loopsPropertyListener;

    @Inject
    public LoopPaneSkin(LoopPane loopPane,
                        @Named("current") ReadOnlyObjectProperty<TimePosition> currentTimePosition,
                        Provider<LoopPaneNode> loopPaneNodeProvider,
                        Provider<LoopControl> loopControlProvider) {
        this.loopPane = loopPane;
        this.currentTimePosition = currentTimePosition;
        this.loopPaneNodeProvider = loopPaneNodeProvider;
        this.loopControlProvider = loopControlProvider;
        currentTimePositionFX.set(currentTimePosition.get());
        currentTimePosition.addListener(new ChangeListener<TimePosition>() {
            @Override
            public void changed(ObservableValue<? extends TimePosition> observableValue, TimePosition timePosition, final TimePosition timePosition1) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        currentTimePositionFX.set(timePosition1);
                    }
                });

            }
        });
        loopPane.loopsProperty().addListener(loopsPropertyListener = new ChangeListener<ObservableList<Loop>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<Loop>> observableValue, ObservableList<Loop> loops, ObservableList<Loop> loops1) {
                loops.removeListener(loopsChangeListener);
                loops1.addListener(loopsChangeListener);
                removeLoop(loops);
                addLoop(loops1);
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
        //To change body of created methods use File | Settings | File Templates.
    }

    private void addLoop(List<? extends Loop> loops) {
        for (final Loop loop : loops) {
            LoopControl loopControl = loopControlProvider.get();
            loopControl.setRadius(100);
            loopControl.setLoop(loop);
            Rotate dynamicRotate = new Rotate();
            Translate translation = new Translate();
            Double angle = Math.random() * Math.PI * 2;
            translation.setX(Math.sin(angle) * loopControl.getRadius());
            translation.setY(-Math.cos(angle) * loopControl.getRadius());
            Rotate staticRotate = new Rotate();
            staticRotate.setAngle((angle * 360 / (2 * Math.PI)) + 180);
            dynamicRotate.angleProperty().bind(new DoubleBinding() {
                {
                    bind(currentTimePositionFX, loop.lengthProperty());
                }

                @Override
                protected double computeValue() {
                    return (currentTimePositionFX.get().getPosition()
                            .remainder(loop.getLength().getPosition())
                            .doubleValue() /
                            loop.getLength().getPosition().doubleValue()) * 360;
                }
            });
            loopControl.getTransforms().addAll(translation, dynamicRotate, staticRotate);
            node.getChildren().add(loopControl);
        }
    }

    private LoopPaneNode createNode() {
        LoopPaneNode loopPaneNode = loopPaneNodeProvider.get();
        return loopPaneNode;
    }

    @Override
    public void dispose() {
        loopPane.getLoops().removeListener(loopsChangeListener);
        loopPane.loopsProperty().removeListener(loopsPropertyListener);
    }
}
