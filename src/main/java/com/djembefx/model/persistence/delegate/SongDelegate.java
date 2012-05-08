package com.djembefx.model.persistence.delegate;

import com.djembefx.model.Loop;
import com.djembefx.model.Song;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.util.List;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 17:29
 */
public class SongDelegate extends PersistenceDelegate {
    @Override
    protected Expression instantiate(Object o, Encoder encoder) {
        Song song = (Song) o;
        return new Expression(o,
                this.getClass(),
                "createSongV1",
                new Object[]{song.getTitle(), song.getPath(), song.getLoops()});
    }

    public static Song createSongV1(String title, String path, List<Loop> loops) {
        Song song = new Song();
        song.setTitle(title);
        song.setPath(path);
        song.getLoops().setAll(loops);
        return song;
    }
}
