package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.esprit.entities.Salle;

import java.net.URL;
import java.util.ResourceBundle;

public class planningcoursController {

    @FXML
    private Button button;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox<String> hourComboBox;

    @FXML
    private TextField idUser;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private Label nom_ter;

    @FXML
    private Label prix_ter;

    @FXML
    private ComboBox<String> nomCours;

    private MainformController mainFormController;




    public void initialize() {

        nomCours.setItems(FXCollections.observableArrayList("zomba", "salsa", "musculation"));

    }

    public void setHomeController(MainformController mainFormController) {
        this.mainFormController = mainFormController;
    }
    public void setreserverSalle(Salle salle ) {
        nom_ter.setText(salle.getNom());


    }


    @FXML
    void Suivant(ActionEvent event) {

    }

}
