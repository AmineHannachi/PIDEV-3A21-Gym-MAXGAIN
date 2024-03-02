package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tn.esprit.entities.Terrain;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.web.WebView;


public class DetaillTerrainController implements Initializable {

    @FXML
    private WebView mapView;
    @FXML
    private Label Description_terrain;

    @FXML
    private ImageView ImageView_terrain;

    @FXML
    private Label Nom_terrain;
    @FXML
    private Label adresse_text;
    @FXML
    private AnchorPane menu_form;
    @FXML
    private Text text;
    @FXML
    private GridPane menu_gridPane;

    @FXML
    private ScrollPane menu_scrollPane;
    private Image image;
    private Terrain terrain;
    @FXML
    private Button Button_Reservation;
    WebView webView;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void setdetaillTerrain(Terrain terrain) throws IOException {
        Nom_terrain.setText(terrain.getNom());
        adresse_text.setText(terrain.getAdresse());

        text.setText(String.valueOf(terrain.getDescription()));
        image = new Image(terrain.getImage());
        ImageView_terrain.setImage(image);
        Button_Reservation.setOnAction(this::reserverTerrain);
        Button_Reservation.setUserData(terrain);


    }
    private MainformController mainFormController;

    public void setController(MainformController mainFormController) {
        this.mainFormController = mainFormController;
    }


    public void reserverTerrain(ActionEvent actionEvent) {
        try{
            Button clickedButton = (Button) actionEvent.getSource();
            Terrain terrain = (Terrain) clickedButton.getUserData(); // Récupérer les données du terrain à partir du bouton


            FXMLLoader loader = new FXMLLoader();
            File fxmlFile = new File("src/main/resources/reservation.fxml");
            URL url = fxmlFile.toURI().toURL();
            loader.setLocation(url);
            Parent root = loader.load();

            RéservationController controller = loader.getController();
            controller.setHomeController(mainFormController);

            // Passer les données du terrain au contrôleur du détail du terrain
            controller.setreserverTerrain(terrain);


            Pane menu_gridPane = mainFormController.getDetailPane();

            menu_gridPane.getChildren().clear();
            menu_gridPane.getChildren().add(root);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}