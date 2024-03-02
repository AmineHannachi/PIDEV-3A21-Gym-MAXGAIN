module com.example.maxgain_store {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.maxgain_store to javafx.fxml;
    exports com.example.maxgain_store;
}