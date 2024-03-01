package tn.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import tn.esprit.entities.Salle;
import tn.esprit.entities.Terrain;
import javafx.scene.layout.Pane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class detailsSalleController implements Initializable {


    @FXML
    private ImageView ImageView_salle;

    @FXML
    private Label Nom_salle;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private Text text;

    @FXML
    private Label adresse_text ;

    @FXML
    private Button button_Reservation;

    private Image image;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private MainformController mainFormController;




    public void setController(MainformController mainFormController) {
        this.mainFormController = mainFormController;
    }

    public void setdetaillSalle(Salle salle ) {
        Nom_salle.setText(salle.getNom());
        adresse_text.setText(salle.getAdresse());


        text.setText(String.valueOf(salle.getDescription()));
        image = new Image(salle.getImage());
        ImageView_salle.setImage(image);
        button_Reservation.setOnAction(this::reservationSalle);
        button_Reservation.setUserData(salle);
    }

    @FXML
    void reservationSalle(ActionEvent event) {
        try{
            Button clickedButton = (Button) event.getSource();
            Salle salle = (Salle) clickedButton.getUserData(); // Récupérer les données du terrain à partir du bouton


            FXMLLoader loader = new FXMLLoader();
            File fxmlFile = new File("src/main/resources/planningcours.fxml");
            URL url = fxmlFile.toURI().toURL();
            loader.setLocation(url);
            Parent root = loader.load();

            planningcoursController controller = loader.getController();
            controller.setHomeController(mainFormController);

            // Passer les données du terrain au contrôleur du détail du terrain
            controller.setreserverSalle(salle);


            Pane menu_gridPane = mainFormController.getDetailPane();

            menu_gridPane.getChildren().clear();
            menu_gridPane.getChildren().add(root);
        }catch (Exception e) {
            e.printStackTrace();
        }


    }

}

