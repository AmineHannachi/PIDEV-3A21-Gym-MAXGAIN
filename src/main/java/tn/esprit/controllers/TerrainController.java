package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import tn.esprit.entities.Terrain;
import tn.esprit.services.TerrainService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.UUID;


public class TerrainController  implements Initializable {

    public TextField terrain_search;
    @FXML
    private TableView<Terrain> table;

    @FXML
    private TableColumn<Terrain, Integer> tableView_ID;

    @FXML
    private TableColumn<Terrain,String> tableView_adresse;

    @FXML
    private TableColumn<Terrain,String> tableView_image;

    @FXML
    private TableColumn<Terrain,String> tableView_nom;

    @FXML
    private TableColumn<Terrain,Integer> tableView_prix;
    @FXML
    private TableColumn<Terrain,Integer> tableView_Descrip;

    @FXML
    private Label adresse;

    @FXML
    private TextField adresseTextField;

    @FXML
    private Button close;

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
    private TextField idTextField;

    @FXML
    private Label image;


    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prixTextField;
    @FXML
    private TextArea DescripTextArea;
    private Image imageT;



    public void tableReadAll() {
        tableView_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tableView_Descrip.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tableView_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tableView_image.setCellValueFactory(cellData -> {
            String imageName = cellData.getValue().getImage();
            String[] parts = imageName.split("/");
            String fileName = parts[parts.length - 1]; // Récupérer le nom du fichier à partir de l'URL
            return new SimpleStringProperty(fileName);
        });
        TerrainService connector = new TerrainService();
        table.setItems( connector.readAll());
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            if (newSelection != null) {
                Terrain selectedUser = newSelection;
                // Now you can use selectedUser object
                idTextField.setText(String.valueOf(selectedUser.getId()));
                nomTextField.setText(selectedUser.getNom());
                DescripTextArea.setText(selectedUser.getDescription());
                adresseTextField.setText(selectedUser.getAdresse());
                prixTextField.setText(String.valueOf(selectedUser.getPrix()));
                imageT = new Image(selectedUser.getImage());
                add_image.setImage(imageT);
            }
        });

    }
    Alert alert;
    @FXML
    void AddTerrain() {
        TerrainService connector = new TerrainService();
        Terrain terrain=new Terrain();
        if(nomTextField.getText().isEmpty() || DescripTextArea.getText().isEmpty() || adresseTextField.getText().isEmpty() || prixTextField.getText().isEmpty() || imageT.getUrl().isEmpty()) {
            Alert emptyFieldsAlert = new Alert(Alert.AlertType.WARNING);
            emptyFieldsAlert.setTitle("Warning");
            emptyFieldsAlert.setHeaderText(null);
            emptyFieldsAlert.setContentText("Please fill in all fields.");
            emptyFieldsAlert.showAndWait();
            return; // Sortie de la méthode car un champ est vide
        }
        try {

            if (connector.checkTerrainExist(nomTextField.getText())) {
                Alert existingNameAlert = new Alert(Alert.AlertType.ERROR);
                existingNameAlert.setTitle("Error");
                existingNameAlert.setHeaderText(null);
                existingNameAlert.setContentText("A terrain with the same name already exists.");
                existingNameAlert.showAndWait();
                return; // Sortie de la méthode car un terrain avec le même nom existe déjà
            }
            connector.add(new Terrain(nomTextField.getText(), DescripTextArea.getText(), adresseTextField.getText() ,Double.parseDouble(prixTextField.getText()), imageT.getUrl()));
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();
            tableReadAll();
            clearTextFields();

        }
            catch (Exception e) {
            e.printStackTrace();

            }
        }

     public void addInsertImage() throws IOException {

         FileChooser fileChooser = new FileChooser();
         fileChooser.setTitle("Ouvrir une image");
         fileChooser.getExtensionFilters().addAll(
                 new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
         File selectedFile = fileChooser.showOpenDialog(null);

         if (selectedFile != null) {
             String imageName = UUID.randomUUID().toString() + "-" + selectedFile.getName();
             // Copier l'image dans le dossier XAMPP
             String destinationFolder = "C:\\xampp\\htdocs\\image";
             Path sourcePath = selectedFile.toPath();
             Path destinationPath = Paths.get(destinationFolder, imageName);
             Files.copy(sourcePath, destinationPath);

             // Charger l'image dans l'ImageView
             imageT = new Image(selectedFile.toURI().toString());
             add_image.setImage(imageT);

         }
     }
//    public void saveImage(File sourceFile) {
//        Path sourcePath = sourceFile.toPath();
//        Path destinationPath = Paths.get(destinationFolder, sourceFile.getName());
//
//        try {
//            Files.copy(sourcePath, destinationPath);
//            System.out.println("Image sauvegardée avec succès dans le dossier XAMPP.");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Erreur lors de la sauvegarde de l'image dans le dossier XAMPP.");
//        }
//    }
    public void UpdateTerrain() {
        TerrainService connector = new TerrainService();
        connector.Update(new Terrain(Integer.parseInt(idTextField.getText()),nomTextField.getText(), DescripTextArea.getText() ,adresseTextField.getText(), Double.parseDouble(prixTextField.getText()), imageT.getUrl()));
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully updated!");
        alert.showAndWait();
        tableReadAll();
        clearTextFields();
    }
    @FXML
    void clearTextFields() {
        idTextField.setText("");
        nomTextField.setText("");
        DescripTextArea.setText("");

        adresseTextField.setText("");

        prixTextField.setText("");
        add_image.setImage(null);

    }
    public void DeleteTerrain() {
        TerrainService connector = new TerrainService();
        connector.delete(Integer.parseInt(idTextField.getText()));
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully Deleted!");
        alert.showAndWait();
        tableReadAll();
        clearTextFields();

    }


    public void terrainSearch() {

        String searchText = terrain_search.getText().trim();
            if (searchText.isEmpty()) {
                tableReadAll();
            } else {
                FilteredList<Terrain> filteredList = new FilteredList<>(table.getItems(), terrain -> terrain.getNom().toLowerCase().contains(searchText.toLowerCase()));
                table.setItems(filteredList);
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableReadAll();
    }
}

