package com.djembefx.model.persistence.delegate;

import com.djembefx.model.*;
import javafx.collections.ObservableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 17:36
 */
public class LoopDelegate extends PersistenceDelegate {

    private final static Logger logger = LoggerFactory.getLogger(LoopDelegate.class);

    @Override
    protected Expression instantiate(Object o, Encoder encoder) {
        Loop loop = (Loop) o;
        return new Expression(o,
                this.getClass(),
                "createLoopV1",
                new Object[]{loop.getName(), loop.getLength(), loop.getNotes(), loop.getInstrument().getClass()});
    }

    public static Loop createLoopV1(String name, Long length, ObservableMap<Long, Note> notes, Class<Instrument> instrument) {
        Loop loop = new Loop();
        loop.setName(name);
        loop.setLength(length);
        loop.getNotes().putAll(notes);
        try {
            loop.setInstrument(instrument.newInstance());
        } catch (InstantiationException e) {
            logger.warn("Could not load instrument " + instrument, e);
        } catch (IllegalAccessException e) {
            logger.warn("Could not load instrument " + instrument, e);
        }
        return loop;
    }

}
