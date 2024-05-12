module com.example.iwardrobefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.iwardrobefx to javafx.fxml;
    exports com.example.iwardrobefx;
}