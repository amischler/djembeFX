package com.djembefx.view.control.skin;

import com.djembefx.model.Loop;
import com.djembefx.model.TimePosition;
import com.djembefx.view.control.LoopControl;
import com.djembefx.view.control.LoopPane;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.transform.Rotate;

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

    final Provider<LoopPaneNode> loopPaneNodeProvider;

    final Provider<LoopControl> loopControlProvider;

    private ListChangeListener<Loop> loopsChangeListener;

    @Inject
    public LoopPaneSkin(LoopPane loopPane,
                        @Named("current") ReadOnlyObjectProperty<TimePosition> currentTimePosition,
                        Provider<LoopPaneNode> loopPaneNodeProvider,
                        Provider<LoopControl> loopControlProvider) {
        this.loopPane = loopPane;
        this.currentTimePosition = currentTimePosition;
        this.loopPaneNodeProvider = loopPaneNodeProvider;
        this.loopControlProvider = loopControlProvider;
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
            LoopControl control = loopControlProvider.get();
            control.setRadius(100);
            control.setLoop(loop);
            node.getChildren().add(control);
            Rotate rotate = new Rotate();
            control.getTransforms().add(rotate);
            Double angle = Math.random() * Math.PI;
            control.setTranslateX(Math.cos(angle) * control.getRadius());
            control.setTranslateY(Math.sin(angle) * control.getRadius());
            rotate.angleProperty().bind(new DoubleBinding() {
                {
                    bind(currentTimePosition, loop.lengthProperty());
                }

                @Override
                protected double computeValue() {
                    System.out.println("current time is " + currentTimePosition.get().getPosition());
                    return currentTimePosition.get().getPosition().remainder(loop.getLength().getPosition()).doubleValue() /
                            loop.getLength().getPosition().doubleValue() * 360;
                }
            });
        }
    }

    private LoopPaneNode createNode() {
        LoopPaneNode loopPaneNode = loopPaneNodeProvider.get();
        return loopPaneNode;
    }

    @Override
    public void dispose() {
        loopPane.getLoops().removeListener(loopsChangeListener);
    }
}
