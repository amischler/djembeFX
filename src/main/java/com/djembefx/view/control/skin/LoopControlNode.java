package com.djembefx.view.control.skin;

import com.djembefx.model.DjembeType;
import com.djembefx.model.Note;
import com.djembefx.model.NoteKind;
import com.djembefx.model.event.EventBus;
import com.djembefx.model.event.EventListener;
import com.djembefx.model.event.PlayNoteEvent;
import com.google.inject.Inject;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 20:26
 */
public class LoopControlNode extends Pane {

    @Inject
    EventBus eventBus;

    private Map<Note, Node> noteMap = new HashMap<Note, Node>();

    private final Circle circle;

    private Map<NoteKind, Color> colors = new HashMap<NoteKind, Color>();

    private Map<Note, EventListener> eventListenerMap = new HashMap<Note, EventListener>();

    public LoopControlNode() {
        colors.put(DjembeType.TONE, Color.OLIVE);
        colors.put(DjembeType.OPEN, Color.BLUEVIOLET);
        colors.put(DjembeType.SLAP, Color.ORANGE);
        this.circle = new Circle();
        circle.setStroke(Color.LIGHTGRAY);
        circle.setStrokeWidth(2.0);
        circle.setFill(Color.TRANSPARENT);
        getChildren().add(circle);
    }

    public Circle getCircle() {
        return circle;
    }

    public void addNote(final Note note, final DoubleProperty angle) {
        Circle noteNode = new Circle();
        noteNode.setRadius(5);
        noteNode.setStroke(Color.GRAY);
        noteNode.setFill(colors.get(note.getNoteKind()));
        noteNode.translateXProperty().bind(new DoubleBinding() {
            {
                bind(circle.radiusProperty(), angle);
            }

            @Override
            protected double computeValue() {
                return -Math.sin(angle.getValue()) * circle.getRadius();
            }
        });
        noteNode.translateYProperty().bind(new DoubleBinding() {
            {
                bind(circle.radiusProperty(), angle);
            }

            @Override
            protected double computeValue() {
                return -Math.cos(angle.getValue()) * circle.getRadius();
            }
        });
        noteMap.put(note, noteNode);
        getChildren().add(noteNode);
        final ScaleTransition scaleTransition = ScaleTransitionBuilder.create()
                .toX(3)
                .toY(3)
                .duration(new Duration(50))
                .cycleCount(2)
                .node(noteNode)
                .autoReverse(true)
                .interpolator(Interpolator.EASE_OUT).build();
        EventListener<PlayNoteEvent> eventListener = new EventListener<PlayNoteEvent>() {
            @Override
            public void onEvent(PlayNoteEvent event) {
                if (event.getNote() == note) {
                    scaleTransition.playFromStart();
                }
            }
        };
        eventListenerMap.put(note, eventListener);
        eventBus.addEventListener(PlayNoteEvent.class, eventListener);
    }

    public void removeNote(Note note) {
        Node noteNode = noteMap.get(note);
        if (noteNode != null) {
            noteNode.translateXProperty().unbind();
            noteNode.translateYProperty().unbind();
            getChildren().remove(noteNode);
            eventBus.removeEventListener(PlayNoteEvent.class, eventListenerMap.get(note));
            eventListenerMap.remove(note);
            noteMap.remove(note);
        }
    }

}
