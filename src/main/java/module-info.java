module tn.esprit {
    exports tn.esprit.controllers;

    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires mysql.connector.j;


    requires java.persistence;
    requires org.controlsfx.controls;

    opens tn.esprit.controllers to javafx.fxml;
    opens tn.esprit.entities to javafx.base;

    exports tn.esprit.test;
}