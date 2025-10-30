module com.example.ics321_project1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ics321_project1 to javafx.fxml;
    exports com.example.ics321_project1;
}