package com.djembefx.render;

import com.djembefx.model.Instrument;
import com.djembefx.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 12/05/12
 * Time: 20:40
 */
public class LoggerNoteRenderer implements SoundRenderer {

    private final static Logger logger = LoggerFactory.getLogger(LoggerNoteRenderer.class);

    @Override
    public void render(Note note, Instrument instrument) {
        logger.info(note + "@" + instrument);
    }
}
