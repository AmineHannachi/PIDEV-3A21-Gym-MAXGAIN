package tn.esprit.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import tn.esprit.entities.Terrain;
import tn.esprit.services.TerrainService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class HomeTerrainController {
    @FXML
    private AnchorPane card_form;


    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;
    private String prod_image;
    @FXML
    private Button Ter_Button;
    private Image image;

    @FXML
    private HBox terrainHbox;

    @FXML
    private VBox terrainVboxr;
    @FXML
    private Pane mainPane;

    @FXML
    public void initialize() {

    }
    public void setTerrainData(Terrain terrain) {
        card_form.setVisible(true);
        prod_name.setText(terrain.getNom());
        prod_price.setText(String.valueOf(terrain.getPrix()));
        image = new Image(terrain.getImage());
        prod_imageView.setImage(image);
        Ter_Button.setOnAction(this::detaillTer);
        Ter_Button.setUserData(terrain); // Stocker les données du terrain dans userData

    }
    private HomeTerrainController homeTerrainController;

    public void sethomeController(HomeTerrainController homeTerrainController) {
        this.homeTerrainController = homeTerrainController;
    }
    private MainformController mainFormController;

    public void setMainFormController(MainformController mainFormController) {
        this.mainFormController = mainFormController;
    }

    @FXML
    public void detaillTer(ActionEvent actionEvent )  {

        try {
            Button clickedButton = (Button) actionEvent.getSource();
            Terrain terrain = (Terrain) clickedButton.getUserData(); // Récupérer les données du terrain à partir du bouton

            FXMLLoader loader = new FXMLLoader();
            File fxmlFile = new File("src/main/resources/detaillTerrain.fxml");
            URL url = fxmlFile.toURI().toURL();
            loader.setLocation(url);
            Parent root = loader.load();

            DetaillTerrainController controller = loader.getController();
            controller.setController(mainFormController);

            // Passer les données du terrain au contrôleur du détail du terrain
            controller.setdetaillTerrain(terrain);

            Pane menu_gridPane = mainFormController.getDetailPane();
            menu_gridPane.getChildren().clear();
            menu_gridPane.getChildren().add(root);
        }catch (Exception e) {
                e.printStackTrace();
            }


    }
}


