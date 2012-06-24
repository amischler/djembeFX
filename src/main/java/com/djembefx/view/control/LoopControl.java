package com.djembefx.view.control;

import com.djembefx.model.Loop;
import com.djembefx.view.control.skin.LoopControlSkin;
import com.google.inject.Inject;
import javafx.beans.property.*;
import javafx.scene.control.Control;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 20:23
 */
public class LoopControl extends Control {

    private final ObjectProperty<Loop> loop = new SimpleObjectProperty<Loop>();

    private final DoubleProperty radius = new SimpleDoubleProperty(150);

    private final LongProperty majorTickUnit = new SimpleLongProperty(32L);

    private final LongProperty minorTickCount = new SimpleLongProperty();

    @Inject
    public LoopControl(LoopControlSkin.Factory factory) {
        setSkin(factory.create(this));
    }

    public Loop getLoop() {
        return loop.get();
    }

    public void setLoop(Loop loop1) {
        loop.set(loop1);
    }

    public ObjectProperty<Loop> loopProperty() {
        return loop;
    }

    public double getRadius() {
        return radius.get();
    }

    public void setRadius(double radius1) {
        radius.set(radius1);
    }

    public DoubleProperty radiusProperty() {
        return radius;
    }

    public Long getMajorTickUnit() {
        return majorTickUnit.get();
    }

    public void setMajorTickUnit(Long majorTickUnit1) {
        this.majorTickUnit.set(majorTickUnit1);
    }

    public LongProperty majorTickUnitProperty() {
        return majorTickUnit;
    }

    public Long getMinorTickCount() {
        return minorTickCount.get();
    }

    public void setMinorTickCount(Long minorTickCount1) {
        this.minorTickCount.set(minorTickCount1);
    }

    public LongProperty minorTickCountProperty() {
        return minorTickCount;
    }

}
