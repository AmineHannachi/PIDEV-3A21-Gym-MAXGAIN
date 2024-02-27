package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Personne;
import services.PersonneService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherPersonnesController {
    private final PersonneService ps = new PersonneService();
    @FXML
    private TableColumn<Personne, Integer> ageCol;
    @FXML
    private TableColumn<Personne, String> nomCol;
    @FXML
    private TableColumn<Personne, String> prenomCol;
    @FXML
    private TableView<Personne> tableView;

    @FXML
    void initialize() {
        try {
            List<Personne> personnes = ps.recuperer();
            ObservableList<Personne> observableList = FXCollections.observableList(personnes);
            tableView.setItems(observableList);

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }














    public void naviguezVersAjouter(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonnes.fxml"));
            Parent root = loader.load();

            // If you need to access the controller of the AjouterPersonnes.fxml, you can do it like this:
            // AjouterPersonnesController ajouterPersonnesController = loader.getController();
            // ajouterPersonnesController.someMethod();

            Scene currentScene = tableView.getScene();
            currentScene.setRoot(root);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error loading AjouterPersonnes.fxml: " + e.getMessage());
            alert.showAndWait();
        }
    }
}

