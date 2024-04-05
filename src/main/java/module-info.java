module com.lightereb.cloudshopmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.oracle.database.jdbc;


    opens com.lightereb.cloudshopmanagementsystem to javafx.fxml;
    exports com.lightereb.cloudshopmanagementsystem;
}