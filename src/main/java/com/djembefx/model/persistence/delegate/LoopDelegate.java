package com.djembefx.model.persistence.delegate;

import com.djembefx.model.Loop;
import com.djembefx.model.Note;
import com.djembefx.model.TimePosition;
import javafx.collections.ObservableMap;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 17:36
 */
public class LoopDelegate extends PersistenceDelegate {

    @Override
    protected Expression instantiate(Object o, Encoder encoder) {
        Loop loop = (Loop) o;
        return new Expression(o,
                this.getClass(),
                "createLoopV1",
                new Object[]{loop.getName(), loop.getLength(), loop.getNotes()});
    }

    public static Loop createLoopV1(String name, TimePosition timePosition, ObservableMap<TimePosition, Note> notes) {
        Loop loop = new Loop();
        loop.setName(name);
        loop.setLength(timePosition);
        loop.getNotes().putAll(notes);
        return loop;
    }

}
