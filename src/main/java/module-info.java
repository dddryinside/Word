module com.dddryinside.word {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires java.sql;
    requires com.jfoenix;
    requires MaterialFX;

    opens com.dddryinside.word to javafx.fxml;
    exports com.dddryinside.word;
}