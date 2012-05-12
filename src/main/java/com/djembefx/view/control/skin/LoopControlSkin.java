package com.djembefx.view.control.skin;

import com.djembefx.model.Loop;
import com.djembefx.model.Note;
import com.djembefx.model.TimePosition;
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

    @Inject
    Provider<LoopControlNode> loopControlNodeProvider;

    private final LoopControl loopControl;

    private LoopControlNode node;

    private Map<Note, DoubleProperty> noteAngle = new HashMap<Note, DoubleProperty>();

    private MapChangeListener<TimePosition, Note> notesChangeListener;

    private ChangeListener<Loop> loopChangeListener;

    public LoopControlSkin(LoopControl loopControl) {
        this.loopControl = loopControl;
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
        for (TimePosition timePosition : loop.getNotes().keySet()) {
            removeNote(timePosition, loop.getNotes().get(timePosition));
        }
        loop.getNotes().remove(notesChangeListener);
    }

    private void configureLoop(Loop loop) {
        if (loop == null)
            return;
        loop.getNotes().addListener(notesChangeListener = new MapChangeListener<TimePosition, Note>() {
            @Override
            public void onChanged(Change<? extends TimePosition, ? extends Note> change) {
                if (change.wasAdded()) {
                    addNote(change.getKey(), change.getValueAdded());
                }
                if (change.wasRemoved()) {
                    removeNote(change.getKey(), change.getValueRemoved());
                }
            }
        });
        for (TimePosition timePosition : loop.getNotes().keySet()) {
            addNote(timePosition, loop.getNotes().get(timePosition));
        }
    }

    private void removeNote(TimePosition key, Note note) {
        noteAngle.get(note).unbind();
        noteAngle.remove(note);
        node.removeNote(note);
    }

    private void addNote(final TimePosition key, Note note) {
        DoubleProperty angle = new SimpleDoubleProperty();
        noteAngle.put(note, angle);
        angle.bind(new DoubleBinding() {

            {
                bind(loopControl.getLoop().lengthProperty());
            }

            @Override
            protected double computeValue() {
                return 2 * Math.PI * key.getPosition().doubleValue() / loopControl.getLoop().getLength().getPosition().doubleValue();
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
