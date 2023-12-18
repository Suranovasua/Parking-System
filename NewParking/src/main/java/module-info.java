module com.example.demo18 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demo18 to javafx.fxml;
    exports com.example.demo18;
}