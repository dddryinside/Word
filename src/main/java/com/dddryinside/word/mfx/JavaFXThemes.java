package com.dddryinside.word.mfx;

import io.github.palexdev.materialfx.MFXResourcesLoader;
import io.github.palexdev.materialfx.theming.base.Theme;
import java.io.InputStream;

public enum JavaFXThemes implements Theme {
    CASPIAN("css/jfx/caspian/caspian.css"),
    CASPIAN_NO_TRANSPARENCY("css/jfx/caspian/caspian-no-transparency.css"),
    CASPIAN_TWO_LEVEL_FOCUS("css/jfx/caspian/two-level-focus.css"),
    MODENA("css/jfx/modena/modena.css"),
    MODENA_NO_TRANSPARENCY("css/jfx/modena/modena-no-transparency.css"),
    MODENA_TOUCH("css/jfx/modena/touch.css"),
    MODENA_TWO_LEVEL_FOCUS("css/jfx/modena/two-level-focus.css"),
    FXVK("css/jfx/caspian/fxvk.css");

    private final String path;

    private JavaFXThemes(String path) {
        this.path = path;
    }

    public String path() {
        return this.path;
    }

    public InputStream assets() {
        String var10000 = this.path();
        int var10002 = this.path().lastIndexOf("/");
        String path = var10000.substring(0, var10002 + 1) + "assets.zip";
        return MFXResourcesLoader.loadStream(path);
    }
}
