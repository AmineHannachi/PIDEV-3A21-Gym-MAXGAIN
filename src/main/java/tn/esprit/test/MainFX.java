package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;


public class MainFX extends Application {
    private GridPane calendarGrid;
    private LocalDate currentDate;
    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loader= new FXMLLoader(getClass().getResource("/Mainform.fxml"));

        try {
            Parent root   = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
//        VBox root = new VBox(10);
//
//        // Sélection de la Date
//        DatePicker datePicker = new DatePicker(LocalDate.now());
//        datePicker.setPromptText("Sélectionnez une date");
//
//        // Sélection du Créneau Horaire
//        ComboBox<String> timeSlotsComboBox = new ComboBox<>();
//        timeSlotsComboBox.getItems().addAll(
//                "10:00 - 11:00",
//                "11:00 - 12:00",
//                "14:00 - 15:00",
//                "15:00 - 16:00"
//        );
//        timeSlotsComboBox.setPromptText("Sélectionnez un créneau horaire");
//
//        root.getChildren().addAll(datePicker, timeSlotsComboBox);
//
//        primaryStage.setScene(new Scene(root, 300, 200));
//        primaryStage.setTitle("Sélection de Date et Créneau Horaire");
//        primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
}
