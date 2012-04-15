package com.djembefx;

import com.djembefx.model.*;
import com.djembefx.model.ioc.ModelModule;
import com.djembefx.view.control.LoopPane;
import com.djembefx.view.ioc.ViewModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 18:13
 */
public class DjembeFX extends Application {

    @java.lang.Override
    public void start(Stage stage) throws Exception {
        Injector injector = Guice.createInjector(new ViewModule(), new ModelModule());
        stage.setScene(injector.getInstance(Scene.class));
        stage.setTitle("DjembeFX");
        stage.show();

        LoopPlayer player = injector.getInstance(LoopPlayer.class);
        Loop loop1 = new Loop();
        Loop loop2 = new Loop();
        loop1.setLength(new TimePosition(128));
        loop2.setLength(new TimePosition(128));
        loop1.getNotes().put(new TimePosition(0), new Note(DjembeType.SLAP));
        loop1.getNotes().put(new TimePosition(48), new Note(DjembeType.SLAP));
        loop1.getNotes().put(new TimePosition(64), new Note(DjembeType.SLAP));
        loop1.getNotes().put(new TimePosition(96), new Note(DjembeType.TONE));
        loop1.getNotes().put(new TimePosition(112), new Note(DjembeType.TONE));
        loop2.getNotes().put(new TimePosition(0), new Note(DjembeType.OPEN));
        loop2.getNotes().put(new TimePosition(32), new Note(DjembeType.TONE));
        loop2.getNotes().put(new TimePosition(48), new Note(DjembeType.TONE));
        loop2.getNotes().put(new TimePosition(96), new Note(DjembeType.SLAP));
        player.getLoops().addAll(loop1, loop2);
        LoopPane loopPane = injector.getInstance(LoopPane.class);
        loopPane.getLoops().addAll(loop1, loop2);
        player.play();
    }

    public static void main(String... args) {
        Application.launch(args);
    }

}
