package com.djembefx.view.control.skin;

import com.djembefx.model.Loop;
import com.djembefx.model.Note;
import com.djembefx.view.control.LoopControl;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Skin;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 20:24
 */
public class LoopControlSkin implements Skin<LoopControl> {

    public static class Css {

        public final static String DEFAULT = "loop-control";

        public final static String SELECTED = "loop-control-selected";

    }

    @Inject
    Provider<LoopControlNode> loopControlNodeProvider;

    private final LoopControl loopControl;

    private LoopControlNode node;

    private Map<Note, DoubleProperty> noteAngle = new HashMap<Note, DoubleProperty>();

    private MapChangeListener<Long, Note> notesChangeListener;

    private ChangeListener<Loop> loopChangeListener;

    private ChangeListener<Number> majorTickUnitListener;

    private ChangeListener<Number> minorTickCountListener;

    public LoopControlSkin(LoopControl loopControl) {
        this.loopControl = loopControl;
        loopControl.getStyleClass().add(Css.DEFAULT);
    }

    @Override
    public LoopControl getSkinnable() {
        return loopControl;
    }

    @Override
    public Node getNode() {
        if (node == null) {
            node = loopControlNodeProvider.get();
            node.getCircle().radiusProperty().bind(loopControl.radiusProperty());
            loopControl.loopProperty().addListener(loopChangeListener = new ChangeListener<Loop>() {
                @Override
                public void changed(ObservableValue<? extends Loop> observableValue, Loop loop, Loop loop1) {
                    unconfigureLoop(loop);
                    configureLoop(loop1);
                }
            });
            configureLoop(loopControl.getLoop());
        }
        return node;
    }

    private void unconfigureLoop(Loop loop) {
        if (loop == null)
            return;
        for (Long time : loop.getNotes().keySet()) {
            removeNote(time, loop.getNotes().get(time));
        }
        loop.getNotes().remove(notesChangeListener);
        loopControl.majorTickUnitProperty().removeListener(majorTickUnitListener);
        loopControl.minorTickCountProperty().removeListener(minorTickCountListener);
    }

    private void configureLoop(Loop loop) {
        if (loop == null)
            return;
        loop.getNotes().addListener(notesChangeListener = new MapChangeListener<Long, Note>() {
            @Override
            public void onChanged(Change<? extends Long, ? extends Note> change) {
                if (change.wasAdded()) {
                    addNote(change.getKey(), change.getValueAdded());
                }
                if (change.wasRemoved()) {
                    removeNote(change.getKey(), change.getValueRemoved());
                }
            }
        });
        for (Long time : loop.getNotes().keySet()) {
            addNote(time, loop.getNotes().get(time));
        }
        loopControl.majorTickUnitProperty().addListener(majorTickUnitListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number bigInteger, Number bigInteger1) {
                node.buildMajorTicks(bigInteger1.longValue(), loopControl.getLoop().getLength());
            }
        });
        node.buildMajorTicks(loopControl.getMajorTickUnit(), loopControl.getLoop().getLength());
        loopControl.minorTickCountProperty().addListener(minorTickCountListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number bigInteger, Number bigInteger1) {
                node.buildMinorTicks(bigInteger1.longValue());
            }
        });
        node.buildMinorTicks(loopControl.getMinorTickCount());
    }

    private void removeNote(Long key, Note note) {
        noteAngle.get(note).unbind();
        noteAngle.remove(note);
        node.removeNote(note);
    }

    private void addNote(final Long key, Note note) {
        DoubleProperty angle = new SimpleDoubleProperty();
        noteAngle.put(note, angle);
        angle.bind(new DoubleBinding() {

            {
                bind(loopControl.getLoop().lengthProperty());
            }

            @Override
            protected double computeValue() {
                return 2 * Math.PI * key.longValue() / loopControl.getLoop().getLength();
            }
        });
        node.addNote(note, angle);
    }

    @Override
    public void dispose() {
        unconfigureLoop(loopControl.getLoop());
        loopControl.loopProperty().removeListener(loopChangeListener);
    }

    public static class Factory {

        @Inject
        Injector injector;

        public LoopControlSkin create(LoopControl control) {
            LoopControlSkin loopControlSkin = new LoopControlSkin(control);
            injector.injectMembers(loopControlSkin);
            return loopControlSkin;
        }

    }

}
