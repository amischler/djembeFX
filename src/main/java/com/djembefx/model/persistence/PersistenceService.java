package com.djembefx.model.persistence;

import com.djembefx.model.Song;
import com.google.inject.ImplementedBy;

import java.io.FileNotFoundException;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 17:13
 */
@ImplementedBy(PersistenceServiceImpl.class)
public interface PersistenceService {

    public void save(Song song, String url);

    public Song load(String url) throws FileNotFoundException;

}
