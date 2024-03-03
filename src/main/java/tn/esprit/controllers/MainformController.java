package tn.esprit.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.esprit.entities.ReservationTerrain;
import tn.esprit.entities.Terrain;
import tn.esprit.services.ReservationService;
import tn.esprit.services.TerrainService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainformController implements Initializable {
    @FXML
    private TextField menu_amount;
    @FXML
    private AnchorPane menu_form;
    @FXML
    private AnchorPane Historique_form;
    @FXML
    private Button Historique_reserve;
    @FXML
    private AnchorPane customers_form;
    @FXML
    private GridPane menu_gridPane;
    @FXML
    private GridPane historique_gridPane;
    @FXML
    private Button menu_receiptBtn;
    @FXML
    private ScrollPane menu_scrollPane;
    @FXML
    private Label menu_total;
    @FXML
    private Button menu_btn;
    @FXML
    private GridPane menu_grid;

    @FXML
    private ScrollPane menu_scroll;

    @FXML
    private GridPane detail_gridPane;
    @FXML
    private ScrollPane detail_scrollPane;

    public Pane getDetailPane() {
        return menu_gridPane;
    }

    public void switchForm(ActionEvent actionEvent)  {
        try {

            menu_form.setVisible(true);
            Historique_form.setVisible(false);


            TerrainService connect = new TerrainService();
            ObservableList<Terrain> terrains = connect.readAll();

            menu_gridPane.getChildren().clear();

            int row = 0;
            int column = 1;
            for (Terrain terrain : terrains) {

                FXMLLoader loader = new FXMLLoader();
                File fxmlFile = new File("src/main/resources/HomeTerrain.fxml");
                URL url = fxmlFile.toURI().toURL();
                loader.setLocation(url);
                AnchorPane homeTerrainPane = loader.load();
                HomeTerrainController controller = loader.getController();
                controller.setMainFormController(this);
                controller.setTerrainData(terrain);
                menu_gridPane.add(homeTerrainPane, column, row);
                GridPane.setMargin(homeTerrainPane, new Insets(10));
                column++;
                if (column == 5) {
                    column = 1;
                    row++;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu_form.setVisible(false);
    }

    public void HistoriqueForm(ActionEvent actionEvent) throws IOException {
        try {
            menu_form.setVisible(false);
            Historique_form.setVisible(true);
            ReservationService connect = new ReservationService();
            ObservableList<ReservationTerrain> terrainR= connect.readAll2();
            historique_gridPane.getChildren().clear();
            int row = 0;
            int column = 1;
            for (ReservationTerrain terrain : terrainR) {

                FXMLLoader loader = new FXMLLoader();
                File fxmlFile = new File("src/main/resources/Historique.fxml");
                URL url = fxmlFile.toURI().toURL();
                loader.setLocation(url);

                AnchorPane TerrainPane = loader.load();
                HistoriqueController controller = loader.getController();
                controller.setController(this);
                controller.setTerrainData(terrain);
                historique_gridPane.add(TerrainPane, column, row);
                GridPane.setMargin(TerrainPane, new Insets(10));
                column++;
                if (column == 3) {
                    column = 1;
                    row++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
