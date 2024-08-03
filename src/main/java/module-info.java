module com.dddryinside.word {
    requires javafx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires java.sql;
    requires com.jfoenix;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;
    requires java.desktop;

    opens com.dddryinside.word to javafx.fxml;
    exports com.dddryinside.word;
}