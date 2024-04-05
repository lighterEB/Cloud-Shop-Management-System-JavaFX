module com.lightereb.cloudshopmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lightereb.cloudshopmanagementsystem to javafx.fxml;
    exports com.lightereb.cloudshopmanagementsystem;
}