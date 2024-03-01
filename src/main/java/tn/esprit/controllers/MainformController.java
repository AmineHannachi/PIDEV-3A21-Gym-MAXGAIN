package tn.esprit.controllers;

import javafx.collections.ObservableList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import tn.esprit.entities.Salle;
import tn.esprit.entities.Terrain;
import tn.esprit.services.SalleService;
import tn.esprit.services.TerrainService;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainformController {
    @FXML
    private TextField menu_amount;
    @FXML
    private AnchorPane menu_form;
    @FXML
    private GridPane menu_gridPane;
    @FXML
    private Button menu_receiptBtn;
    @FXML
    private ScrollPane menu_scrollPane;
    @FXML
    private Label menu_total;
    @FXML
    private Button menu_btn;
    private ResultSet result;
    private PreparedStatement prepare;
    private Statement statement;
    private Connection connect;

    public Pane getDetailPane() {
        return menu_gridPane;
    }
    public void switchForm(ActionEvent actionEvent)  {
        try {
            menu_form.setVisible(true);
            SalleService connect = new SalleService();
            ObservableList<Salle> salles = connect.readAll();

            // Nettoyer le conteneur avant d'ajouter de nouvelles données
            menu_gridPane.getChildren().clear();

            int row = 0;
            int column = 1;
            for (Salle salle : salles) {

                FXMLLoader loader = new FXMLLoader();
                File fxmlFile = new File("src/main/resources/HomeSalle.fxml");
                URL url = fxmlFile.toURI().toURL();
                loader.setLocation(url);
                AnchorPane homeTerrainPane = loader.load();
                HomeSalleController controller = loader.getController();
                controller.setMainFormController(this);
                controller.setSalleData(salle);
                menu_gridPane.add(homeTerrainPane, column, row);
                GridPane.setMargin(homeTerrainPane, new Insets(10));
                column++;
                if (column == 4) {
                    column = 1;
                    row++;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
