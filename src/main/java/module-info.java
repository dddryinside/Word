module com.dddryinside.word {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dddryinside.word to javafx.fxml;
    exports com.dddryinside.word;
}