package tn.esprit.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.Salle;
import tn.esprit.entities.Salle;
import tn.esprit.services.SalleService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HomeSalleController {

    @FXML
    private AnchorPane card_form;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private Button sal_Button;

    @FXML
    private HBox salleHbox;

    @FXML
    private VBox salleVbox;

    private MainformController mainFormController;

    private Image image;

    public void setMainFormController(MainformController mainFormController) {
        this.mainFormController = mainFormController;
    }
    @FXML
    void detaillerSal(ActionEvent event) {


        try {

            Button clickedButton = (Button) event.getSource();
            Salle salle = (Salle) clickedButton.getUserData();

            SalleService connect = new SalleService();
            ObservableList<Salle> salles = connect.readAll();




                FXMLLoader loader = new FXMLLoader();
                File fxmlFile = new File("src/main/resources/detailsSalle.fxml");
                URL url = fxmlFile.toURI().toURL();
                loader.setLocation(url);
                Parent root = loader.load();
                detailsSalleController controller = loader.getController();
                controller.setController(mainFormController);

                // Passer les données du terrain au contrôleur du détail du terrain

                controller.setdetaillSalle(salle);
                Pane menu_gridPane = mainFormController.getDetailPane();

                menu_gridPane.getChildren().clear();
                menu_gridPane.getChildren().add(root);


        }catch (Exception e) {
            e.printStackTrace();
        }
    }





    @FXML
    public void initialize() {

    }

    public void setSalleData(Salle salle) {
        prod_name.setText(salle.getNom());

        image = new Image(salle.getImage());
        prod_imageView.setImage(image);

        sal_Button.setOnAction(this::detaillerSal);
       sal_Button.setUserData(salle);


    }
}