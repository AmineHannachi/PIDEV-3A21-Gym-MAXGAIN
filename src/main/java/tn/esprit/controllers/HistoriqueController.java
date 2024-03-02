package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.entities.ReservationTerrain;
import tn.esprit.entities.Terrain;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class HistoriqueController {


    @FXML
    private Label Nom_ter;

    @FXML
    private Label date_ter;

    @FXML
    private AnchorPane hist_form;

    @FXML
    private Label prod_name2;

    @FXML
    private VBox salleVbox;

    @FXML
    private HBox terrainHbox;

    @FXML
    private HBox terrainHbox1;

    private MainformController mainFormController;

    public void setController(MainformController mainFormController) {
        this.mainFormController = mainFormController;
    }


    public void setTerrainData(ReservationTerrain reserveterrain) {
        hist_form.setVisible(true);

        int id=reserveterrain.getId_terrain();
        String Nom=;

        java.sql.Date date = (Date) reserveterrain.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = formatter.format(date.toLocalDate()); // Convertir java.sql.Date en LocalDate
        date_ter.setText(formattedDate);


        java.sql.Time time = reserveterrain.getHeure();
        DateTimeFormatter formatterT = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = formatterT.format(time.toLocalTime()); // Convertir java.sql.Time en LocalTime
        prod_name2.setText(formattedTime);
    }
}