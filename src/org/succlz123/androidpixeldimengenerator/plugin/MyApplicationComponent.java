package org.succlz123.androidpixeldimengenerator.plugin;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by succlz123 on 16/6/14.
 */
public class MyApplicationComponent implements ApplicationComponent {

    public MyApplicationComponent() {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "MyApplicationComponent";
    }
}
