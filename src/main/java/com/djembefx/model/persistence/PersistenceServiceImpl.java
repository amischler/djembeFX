package com.djembefx.model.persistence;

import com.djembefx.model.Song;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 17:14
 */
public class PersistenceServiceImpl implements PersistenceService {

    Logger logger = LoggerFactory.getLogger(PersistenceServiceImpl.class);

    @Inject
    XMLEncoderFactory xmlEncoderFactory;

    @Override
    public void save(Song song, String url) {
        try {
            XMLEncoder e = xmlEncoderFactory.create(url);
            song.setPath(url);
            e.writeObject(song);
            e.close();
        } catch (FileNotFoundException e1) {
            logger.warn(e1.getMessage(), e1);
        }
    }

    @Override
    public Song load(String url) throws FileNotFoundException {
        XMLDecoder d = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(url)));
        Object result = d.readObject();
        d.close();
        return (Song) result;
    }
}
