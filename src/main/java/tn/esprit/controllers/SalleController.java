package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;


import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.FileChooser;

import tn.esprit.entities.Salle;
import tn.esprit.entities.Terrain;
import tn.esprit.services.SalleService;
import tn.esprit.services.TerrainService;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.control.textfield.AutoCompletionBinding;


public class SalleController implements Initializable {

    @FXML
    private TableColumn<Salle, String> TableView_adresse;

    @FXML
    private TableColumn<Salle, String> TableView_description;

    @FXML
    private TableColumn<Salle, String> TableView_id;

    @FXML
    private TableColumn<Salle, String>TableView_image;

    @FXML
    private TableColumn<Salle, String>TableView_nom;

    @FXML
    private TableView<Salle> table;



    @FXML
    private Button addSalle_btn;

    @FXML
    private TextField adresse;

    @FXML
    private Label adresseSA;

    @FXML
    private Button close;

    @FXML
    private TextArea descriptionArea;


    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Button home_form_addBtn;

    @FXML
    private Button home_form_clearBtn;

    @FXML
    private Button home_form_deleteBtn;

    @FXML
    private ImageView add_image;

    @FXML
    private Button home_form_importBtn;

    @FXML
    private Button home_form_updateBtn;

    @FXML
    private TextField id;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private TextField nom;



    @FXML
    private Label nomSA;

    @FXML
    private TextField search;

    @FXML
    private Label username;

    private Image imageT;



Alert alert;








    @FXML
    public void searchSalle() {

            String searchText = search.getText().trim();
            if (searchText.isEmpty()) {
                tableReadAll(); // Si le champ de recherche est vide, charger toutes les données
            } else {
                // Créer une FilteredList pour filtrer les données en fonction du texte de recherche
                FilteredList<Salle> filteredList = new FilteredList<>(table.getItems(), salle -> salle.getNom().toLowerCase().contains(searchText.toLowerCase()));
                table.setItems(filteredList); // Mettre à jour la table avec la liste filtrée
            }
        }




    public void tableReadAll() {
        TableView_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableView_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TableView_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        TableView_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableView_image.setCellValueFactory(new PropertyValueFactory<>("image"));
        SalleService connector = new SalleService();
        table.setItems(connector.readAll());
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Salle selectedUser = newSelection;
                // Now you can use selectedUser object
                id.setText(String.valueOf(selectedUser.getId()));
                nom.setText(selectedUser.getNom());
                adresse.setText(selectedUser.getAdresse());
                descriptionArea.setText(String.valueOf(selectedUser.getDescription()));
                imageT = new Image(selectedUser.getImage());
                add_image.setImage(imageT);
            }



        });


    }

    @FXML
    void clearTextFields() {
        id.setText("");
        nom.setText("");
        adresse.setText("");
        descriptionArea.setText("");
        add_image.setImage(null);
    }


    @FXML
    public void ajouter() {
//        SalleService connector = new SalleService();
//        connector.add(new Salle(nom.getText(),adresse.getText(), descriptionArea.getText(), imageT.getUrl()));
//        tableReadAll();
//        clearTextFields();
        SalleService connector = new SalleService();
        Salle salle=new Salle();
        if(nom.getText().isEmpty() || adresse.getText().isEmpty() || descriptionArea.getText().isEmpty() || imageT.getUrl().isEmpty()) {
            Alert emptyFieldsAlert = new Alert(Alert.AlertType.WARNING);
            emptyFieldsAlert.setTitle("erreur ");
            emptyFieldsAlert.setHeaderText(null);
            emptyFieldsAlert.setContentText("Merci de remplir tous les champs");
            emptyFieldsAlert.showAndWait();
            return; // Sortie de la méthode car un champ est vide
        }
        try {

            if (connector.checkSalleExist(nom.getText())) {
                Alert existingNameAlert = new Alert(Alert.AlertType.ERROR);
                existingNameAlert.setTitle("Erreur");
                existingNameAlert.setHeaderText(null);
                existingNameAlert.setContentText("salle déja exist.");
                existingNameAlert.showAndWait();
                clearTextFields();
                return; // Sortie de la méthode car un terrain avec le même nom existe déjà
            }
            connector.add(new Salle(nom.getText(), adresse.getText(), descriptionArea.getText() , imageT.getUrl()));
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message d'information");
            alert.setHeaderText(null);
            alert.setContentText("ajouté avec succès");
            alert.showAndWait();
            tableReadAll();
            clearTextFields();

        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableReadAll();
    }

    public void update() {
        SalleService connector = new SalleService();
        connector.Update(new Salle(Integer.parseInt(id.getText()),nom.getText(),adresse.getText(), descriptionArea.getText(), imageT.getUrl()));
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message d'information");
        alert.setHeaderText(null);
        alert.setContentText("modifier avec succès");
        alert.showAndWait();
        tableReadAll();
        clearTextFields();
    }

    public void delete() {
        SalleService connector = new SalleService();
        connector.delete(Integer.parseInt(id.getText()));
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message d'information");
        alert.setHeaderText(null);
        alert.setContentText("Supprimer avec succès");
        alert.showAndWait();
        tableReadAll();
        clearTextFields();


    }


//    public void addInsertImage() {
//
//        FileChooser open = new FileChooser();
//        File file = open.showOpenDialog(home_form.getScene().getWindow());
//
//        if (file != null) {
//            // Charger l'image dans l'ImageView
//            imageT = new Image(file.toURI().toString());
//            add_image.setImage(imageT);
//        }
//    }


    public void addInsertImage() throws IOException {

        FileChooser open = new FileChooser();
      File file = open.showOpenDialog(home_form.getScene().getWindow());

        if (file != null) {
            String imageName = UUID.randomUUID().toString() + "-" + file.getName();
            // Copier l'image dans le dossier XAMPP
            String destinationFolder = "C:\\xampp\\htdocs\\MaxGain\\images";
            Path sourcePath = file.toPath();
            Path destinationPath = Paths.get(destinationFolder, imageName);
            Files.copy(sourcePath, destinationPath);

            // Charger l'image dans l'ImageView
            imageT = new Image(file.toURI().toString());
            add_image.setImage(imageT);

        }
    }





}








//    @FXML
//    void ajouter(ActionEvent event){
//            ss.add(nomSA.getText(), adresseSA.getText(),descriptionSA.getText(), imageSA.getText());
//        tableReadAll();
//
//    }
//    @FXML
//    void tableReadAll() {
//        addSalle_col_nomSA.setCellValueFactory(new PropertyValueFactory<>("nomSA"));
//        addSalle_col_adresseSA.setCellValueFactory(new PropertyValueFactory<>("adresseSA"));
//        addEmployee_col_descriptionSA.setCellValueFactory(new PropertyValueFactory<>("descriptionSA"));
//
//        SalleService ss = new SalleService();
//        addSalle_tableView.setItems(ss.readAll());
//        addSalle_tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                Salle  selectedUser = newSelection;
//                // Now you can use selectedUser object
//                nomSA.setText(selectedUser.getNom());
//                adresseSA.setText(selectedUser.getAdresse());
//                descriptionSA.setText(selectedUser.getDescription());
//                imageSA.setText(selectedUser.getDescription());
//                idTextField.setText(String.valueOf(selectedUser.getId()));

//        });








