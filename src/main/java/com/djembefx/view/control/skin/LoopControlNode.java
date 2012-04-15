package com.djembefx.view.control.skin;

import com.djembefx.model.DjembeType;
import com.djembefx.model.Note;
import com.djembefx.model.NoteKind;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 20:26
 */
public class LoopControlNode extends Pane {

    private Map<Note, Node> noteMap = new HashMap<Note, Node>();

    private final Circle circle;

    private Map<NoteKind, Color> colors = new HashMap<NoteKind, Color>();

    public LoopControlNode() {
        colors.put(DjembeType.TONE, Color.OLIVE);
        colors.put(DjembeType.OPEN, Color.BLUEVIOLET);
        colors.put(DjembeType.SLAP, Color.ORANGE);
        this.circle = new Circle();
        circle.setStroke(Color.GRAY);
        circle.setStrokeWidth(2.0);
        circle.setFill(Color.TRANSPARENT);
        getChildren().add(circle);
    }

    public Circle getCircle() {
        return circle;
    }

    public void addNote(Note note, final DoubleProperty angle) {
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
    }

    public void removeNote(Note note) {
        Node noteNode = noteMap.get(note);
        if (noteNode != null) {
            noteNode.translateXProperty().unbind();
            noteNode.translateYProperty().unbind();
            getChildren().remove(noteNode);
            noteMap.remove(note);
        }
    }

}
