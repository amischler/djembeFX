package com.djembefx.view.ioc;

import com.djembefx.view.control.LoopPane;
import com.djembefx.view.control.LoopPaneLayout;
import com.djembefx.view.control.skin.FlowLoopPaneLayout;
import com.djembefx.view.control.skin.OnePointLoopPaneLayout;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 18:29
 */
public class ViewModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    Scene provideScene(@Named("root") Parent root) {
        Scene scene = new Scene(root);
        return scene;
    }

    @Named("root")
    @Provides
    Parent provideParent() throws IOException {
        return FXMLLoader.load(ViewModule.class.getClassLoader().getResource("com/djembefx/view/djembefx.fxml"));
    }

    @Provides
    @Singleton
    LoopPane provideLoopPane(@Named("default") LoopPaneLayout layout) {
        LoopPane loopPane = new LoopPane();
        loopPane.setLoopPaneLayout(layout);
        return loopPane;
    }

    @Provides
    @Named("default")
    LoopPaneLayout providesDefaultLoopPaneLayout(OnePointLoopPaneLayout defaultLoopPaneLayout) {
        return defaultLoopPaneLayout;
    }

    @Provides
    List<LoopPaneLayout> providesAvailableLoopPaneLayouts(
            FlowLoopPaneLayout flowLoopPaneLayout,
            OnePointLoopPaneLayout onePointLoopPaneLayout) {
        List<LoopPaneLayout> list = new LinkedList<LoopPaneLayout>();
        list.add(flowLoopPaneLayout);
        list.add(onePointLoopPaneLayout);
        return list;
    }

}
