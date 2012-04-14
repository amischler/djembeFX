package com.djembefx;

import com.djembefx.model.Loop;
import com.djembefx.model.LoopPlayer;
import com.djembefx.model.Note;
import com.djembefx.model.TimePosition;
import com.djembefx.model.ioc.ModelModule;
import com.djembefx.view.LoopPane;
import com.djembefx.view.ioc.ViewModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
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
        loop1.getNotes().put(new TimePosition(1), new Note());
        loop1.getNotes().put(new TimePosition(32), new Note());
        loop1.getNotes().put(new TimePosition(63), new Note());
        loop2.getNotes().put(new TimePosition(1), new Note());
        player.getLoops().add(loop1);
        player.getLoops().add(loop2);
        LoopPane loopPane = injector.getInstance(LoopPane.class);
        loopPane.getLoops().addAll(loop1, loop2);
        player.play();
    }

    public static void main(String... args) {
        Application.launch(args);
    }

}
