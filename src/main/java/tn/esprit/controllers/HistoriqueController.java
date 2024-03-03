package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.entities.ReservationCours;
import tn.esprit.entities.ReservationTerrain;
import tn.esprit.entities.Terrain;
import tn.esprit.services.CoursService;
import tn.esprit.services.TerrainService;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class HistoriqueController {


    @FXML
    private Label Nom_sal;

    @FXML
    private Label date_sal;

    @FXML
    private AnchorPane hist_form;

    @FXML
    private Label prod_name2;

    @FXML
    private VBox salleVbox;

    @FXML
    private HBox salleHbox;

    @FXML
    private HBox salleHbox1;

    private MainformController mainFormController;

    public void setController(MainformController mainFormController) {
        this.mainFormController = mainFormController;
    }


    public void setSalleData(ReservationCours reservecours) {
        hist_form.setVisible(true);

        int id=reservecours.getId_cours();
        CoursService coursService =new CoursService();
        String Nom=coursService.obtenirNomSalle(id);
        Nom_sal.setText(Nom);

        java.sql.Date date = (Date) reservecours.getDate_c();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = formatter.format(date.toLocalDate()); // Convertir java.sql.Date en LocalDate
        date_sal.setText(formattedDate);


        java.sql.Time time = reservecours.getHeure();
        DateTimeFormatter formatterT = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = formatterT.format(time.toLocalTime()); // Convertir java.sql.Time en LocalTime
        prod_name2.setText(formattedTime);
    }
}
