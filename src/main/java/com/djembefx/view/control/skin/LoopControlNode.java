package com.djembefx.view.control.skin;

import com.djembefx.application.event.DeleteNoteEvent;
import com.djembefx.application.event.EventBus;
import com.djembefx.application.event.EventListener;
import com.djembefx.application.event.PlayNoteEvent;
import com.djembefx.model.instrument.DjembeType;
import com.djembefx.model.Loop;
import com.djembefx.model.Note;
import com.djembefx.model.NoteKind;
import com.google.inject.Inject;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

    private final DoubleProperty ghostAngleProperty = new SimpleDoubleProperty();

    private Map<NoteKind, Color> colors = new HashMap<NoteKind, Color>();

    private Map<Note, EventListener> eventListenerMap = new HashMap<Note, EventListener>();

    private Map<Note, ChangeListener<Boolean>> hoverListenerMap = new HashMap<Note, ChangeListener<Boolean>>();

    private List<Node> majorTicks = new LinkedList<Node>();

    private List<Node> minorTicks = new LinkedList<Node>();

    public LoopControlNode() {
        colors.put(DjembeType.TONE, Color.BROWN);
        colors.put(DjembeType.BAS, Color.RED);
        colors.put(DjembeType.SLAP, Color.ORANGE);
        this.circle = new Circle();
        circle.getStyleClass().add("loop");
        circle.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getY() < 0) {
                    ghostAngleProperty.set(Math.atan(mouseEvent.getX() / mouseEvent.getY()));
                } else {
                    ghostAngleProperty.set(Math.PI + Math.atan(mouseEvent.getX() / mouseEvent.getY()));
                }
            }
        });
        circle.setPickOnBounds(false);
        getChildren().add(circle);
        createGhostNode();
    }

    private void createGhostNode() {
        Circle ghostNode = new Circle();
        ghostNode.getStyleClass().add("ghost");
        ghostNode.setRadius(5);
        ghostNode.setMouseTransparent(true);
        ghostNode.visibleProperty().bind(circle.hoverProperty());
        ghostNode.translateXProperty().bind(new DoubleBinding() {
            {
                bind(circle.radiusProperty(), ghostAngleProperty);
            }

            @Override
            protected double computeValue() {
                return -Math.sin(ghostAngleProperty.getValue()) * circle.getRadius();
            }
        });
        ghostNode.translateYProperty().bind(new DoubleBinding() {
            {
                bind(circle.radiusProperty(), ghostAngleProperty);
            }

            @Override
            protected double computeValue() {
                return -Math.cos(ghostAngleProperty.getValue()) * circle.getRadius();
            }
        });
        getChildren().add(ghostNode);
    }

    public Circle getCircle() {
        return circle;
    }

    public Node addNote(final Note note, final DoubleProperty angle, final Loop loop) {
        final Circle noteNode = new Circle();
        noteNode.getStyleClass().add("note");
        noteNode.setRadius(5);
        noteNode.setFill(colors.get(note.getNoteKind()));
        noteNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                eventBus.publish(new DeleteNoteEvent(note, loop));
            }
        });
        final ScaleTransition hoverScaleTransition = ScaleTransitionBuilder.create()
                .node(noteNode)
                .fromX(1.0)
                .fromY(1.0)
                .toY(1.5)
                .toX(1.5)
                .duration(Duration.millis(150))
                .build();
        ChangeListener<Boolean> hoverChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean1) {
                if (aBoolean1) {
                    hoverScaleTransition.setRate(1.0);
                } else {
                    hoverScaleTransition.setRate(-1.0);
                }
                hoverScaleTransition.play();
            }
        };
        noteNode.hoverProperty().addListener(hoverChangeListener);
        hoverListenerMap.put(note, hoverChangeListener);
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
        return noteNode;
    }

    public void removeNote(Note note) {
        Node noteNode = noteMap.get(note);
        if (noteNode != null) {
            noteNode.translateXProperty().unbind();
            noteNode.translateYProperty().unbind();
            getChildren().remove(noteNode);
            eventBus.removeEventListener(PlayNoteEvent.class, eventListenerMap.get(note));
            noteNode.hoverProperty().removeListener(hoverListenerMap.get(note));
            hoverListenerMap.remove(note);
            eventListenerMap.remove(note);
            noteMap.remove(note);
        }
    }

    public void buildMajorTicks(Long majorTickUnit, Long totalSize) {
        getChildren().removeAll(majorTicks);
        majorTicks.clear();
        Long currentValue = 0L;
        while (currentValue < totalSize) {
            Node tick = createTick(currentValue * 2 * Math.PI / totalSize, 3.0);
            tick.getStyleClass().add("major-tick");
            majorTicks.add(tick);
            currentValue = currentValue + majorTickUnit;
        }
        getChildren().addAll(majorTicks);
    }

    public void buildMinorTicks(Long minorTickCount, Long majorTickUnit, Long totalSize) {
        getChildren().removeAll(minorTicks);
        minorTicks.clear();
        Long currentValue = 0L;
        Long minorTickUnit = (long) ((majorTickUnit) / (minorTickCount + 1.0));
        while (currentValue < totalSize) {
            if (currentValue % majorTickUnit != 0) {
                Node tick = createTick(currentValue * 2 * Math.PI / totalSize, 1.0);
                tick.getStyleClass().add("minor-tick");
                minorTicks.add(tick);
            }
            currentValue = currentValue + minorTickUnit;
        }
        getChildren().addAll(minorTicks);
    }

    private Node createTick(final double angle, double length) {
        Line line = LineBuilder.create().startX(-Math.sin(angle) * length)
                .startY(-Math.cos(angle) * length)
                .endX(Math.sin(angle) * length)
                .endY(Math.cos(angle) * length).build();
        line.translateXProperty().bind(new DoubleBinding() {
            {
                bind(circle.radiusProperty());
            }

            @Override
            protected double computeValue() {
                return -Math.sin(angle) * circle.getRadius();
            }
        });
        line.translateYProperty().bind(new DoubleBinding() {
            {
                bind(circle.radiusProperty());
            }

            @Override
            protected double computeValue() {
                return -Math.cos(angle) * circle.getRadius();
            }
        });
        line.setMouseTransparent(true);
        return line;
    }

    public double getGhostAngle() {
        return ghostAngleProperty.get();
    }

    public ReadOnlyDoubleProperty ghostAngleProperty() {
        return ghostAngleProperty;
    }

}
