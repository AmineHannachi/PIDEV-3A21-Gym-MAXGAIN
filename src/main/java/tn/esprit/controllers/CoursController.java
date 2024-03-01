package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tn.esprit.entities.Salle;
import tn.esprit.entities.cours;
import tn.esprit.services.CoursService;
import tn.esprit.services.SalleService;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CoursController implements Initializable {

    @FXML
    private TableColumn<cours, String> TableView_nom;

    @FXML
    private TableColumn<cours, String> TableView_type;

    @FXML
    private Button addCours_btn;

    @FXML
    private Button close;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Button home_form_addBtn;

    @FXML
    private Button home_form_deleteBtn;

    @FXML
    private Button home_form_updateBtn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private AnchorPane nom;

    @FXML
    private Label nomSA;

    @FXML
    private ComboBox<String> nomSalle;

    @FXML
    private TextField search;

    @FXML
    private TableView<cours> table;

    @FXML
    private TextField type;

    @FXML
    private Label typeCours;

    @FXML
    private Label username;

    @FXML
    private TextField idC;

    Alert alert;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableReadAll();
        nomSalle.setItems(FXCollections.observableArrayList("Max Gain Lac", "Max Gain Naser", "Max Gain Boumhal", "Max Gain Centre Urbain", "Max Gain Ariana"));

    }


    public void tableReadAll() {
        CoursService connector = new CoursService();
        TableView_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableView_nom.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getSalle().getNom()));

        // table.setItems(connector.readAll());
        table.setItems(connector.readAll2());
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cours selectedUser = newSelection;

                idC.setText(String.valueOf(selectedUser.getId()));
                type.setText(selectedUser.getType());

            }
        });
    }


    @FXML
    void ajouterC(ActionEvent event) {

        String nomSalleText = nomSalle.getSelectionModel().getSelectedItem().toString();
        CoursService c = new CoursService();
        SalleService ss = new SalleService();
        if (nomSalleText != null && !nomSalleText.isEmpty()) {
            try {

                int idSalle = ss.obtenirIdSalle(nomSalleText);
                c.add(new cours(type.getText(), idSalle));
                System.out.println("Cours ajouté avec succès !");
            } catch (Exception e) {
                System.err.println("Erreur lors de l'ajout du cours : " + e.getMessage());
                // Affichez un message d'erreur à l'utilisateur s'il y a un problème
            }
        } else {
            System.err.println("Le champ de texte de la salle est vide !");
            // Indiquez à l'utilisateur qu'il doit remplir le champ de texte de la salle
        }
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully Added!");
        alert.showAndWait();
        tableReadAll();
        clearTextFields();
    }

    @FXML
    void clearTextFields() {
        idC.setText("");
        type.setText("");
        nomSalle.setItems(FXCollections.observableArrayList("Max Gain Lac", "Max Gain Naser", "Max Gain Boumhal", "Max Gain Centre Urbain", "Max Gain Ariana"));

    }

    @FXML
    void deleteC(ActionEvent event) {

        CoursService connector = new CoursService();
        connector.delete(Integer.parseInt(idC.getText()));
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully deleted!");
        alert.showAndWait();
        tableReadAll();
        clearTextFields();
    }

    @FXML
    void updateC(ActionEvent event) {

        String nomSalleText = nomSalle.getSelectionModel().getSelectedItem().toString();
        CoursService c = new CoursService();
        SalleService ss = new SalleService();

            try {

                int idSalle = ss.obtenirIdSalle(nomSalleText);
                c.Update(new cours(Integer.parseInt(idC.getText()),type.getText(), idSalle));
                System.out.println("modification ajouté avec succès !");
            } catch (Exception e) {
                System.err.println("Erreur lors de modification du cours : " + e.getMessage());
                // Affichez un message d'erreur à l'utilisateur s'il y a un problème
            }

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully updated!");
        alert.showAndWait();
        tableReadAll();
        clearTextFields();
    }

    @FXML
    void searchC(ActionEvent event) {

            String searchText = search.getText().trim();
            if (searchText.isEmpty()) {
                tableReadAll(); // Si le champ de recherche est vide, charger toutes les données
            } else {
                try {
                    ObservableList<cours> filteredCoursList = FXCollections.observableArrayList();
                    for (cours cours : table.getItems()) {
                        String salleNom = cours.getSalle().getNom().toLowerCase();
                        if (salleNom.contains(searchText.toLowerCase())) {
                            filteredCoursList.add(cours);
                        }
                    }
                    table.setItems(filteredCoursList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }}
