module com.dddryinside.word {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires java.sql;
    requires com.jfoenix;
    requires MaterialFX;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.dddryinside.word to javafx.fxml;
    exports com.dddryinside.word;
}