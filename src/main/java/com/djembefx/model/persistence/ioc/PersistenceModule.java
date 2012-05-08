package com.djembefx.model.persistence.ioc;

import com.djembefx.model.Loop;
import com.djembefx.model.Note;
import com.djembefx.model.Song;
import com.djembefx.model.TimePosition;
import com.djembefx.model.persistence.XMLEncoderFactory;
import com.djembefx.model.persistence.XMLEncoderFactoryImpl;
import com.djembefx.model.persistence.delegate.*;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.sun.javafx.collections.ObservableListWrapper;
import com.sun.javafx.collections.ObservableMapWrapper;

import java.beans.PersistenceDelegate;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 17:46
 */
public class PersistenceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(XMLEncoderFactory.class).to(XMLEncoderFactoryImpl.class);
    }

    @Provides
    Map<Class, PersistenceDelegate> providesDelegates() {
        Map<Class, PersistenceDelegate> delegateMap = new HashMap<Class, PersistenceDelegate>();
        delegateMap.put(Loop.class, new LoopDelegate());
        delegateMap.put(Note.class, new NoteDelegate());
        delegateMap.put(ObservableListWrapper.class, new ObservableListDelegate());
        delegateMap.put(ObservableMapWrapper.class, new ObservableMapDelegate());
        delegateMap.put(Song.class, new SongDelegate());
        delegateMap.put(TimePosition.class, new TimePositionDelegate());
        return delegateMap;
    }

}
