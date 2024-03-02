package tn.esprit.controllers;

import com.mysql.cj.jdbc.MysqlSQLXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.esprit.entities.ReservationTerrain;
import tn.esprit.entities.Terrain;
import tn.esprit.services.ReservationService;
import tn.esprit.services.TerrainService;
import tn.esprit.services.UserService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;

public class RéservationController {
    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;
    @FXML
    private TextField idUser;
    @FXML
    private ScrollPane menu_scrollPane;
    @FXML
    private Label nom_ter;
    @FXML
    private Button button;

    @FXML
    private Label prix_ter;
    @FXML
    private ComboBox<String> hourComboBox;
    @FXML
    public void initialize() {
        // Initialisez les ComboBox pour les heures et les minutes avec les valeurs appropriées
        for (int i = 9; i < 24; i++) {
            String formattedHour = String.format("%02d:00", i); // Formatage de l'heure
            hourComboBox.getItems().add(formattedHour);
        }
//        ComboBox<String> timeSlotsComboBox = new ComboBox<>();

    }
    private HomeTerrainController homeTerrainController;

    public void sethomeController(HomeTerrainController homeTerrainController) {
        this.homeTerrainController = homeTerrainController;
    }
    private MainformController mainFormController;

    public void setHomeController(MainformController mainFormController) {
        this.mainFormController = mainFormController;
    }
    public void setreserverTerrain(Terrain terrain) {
        nom_ter.setText(terrain.getNom());
        prix_ter.setText(String.valueOf(terrain.getPrix()));

    }
    @FXML
    private DatePicker date;
    private MainformController homeController;
    private Stage stage;
    public void Suivant(ActionEvent actionEvent) throws SQLException, IOException {
        ReservationService connector = new ReservationService();

        String nom=nom_ter.getText();
        TerrainService terrain =new TerrainService();
        int idTerrain= terrain.obtenirIdTerrain(nom);

        UserService user=new UserService();
        String idUser=user.obtenirNomUser(1);


        double prix = Double.parseDouble(prix_ter.getText());

       Date selectedDate = Date.valueOf(date.getValue());


        String selectedHour = hourComboBox.getValue();
        String[] parts = selectedHour.split(":");

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        // Création d'un objet Time
        Time selectedTime = new Time(hour, minute, 0);

        System.out.println(selectedTime);
        if (connector.checkTerrainExist(idTerrain,selectedDate,selectedTime)) {
            Alert existingNameAlert = new Alert(Alert.AlertType.ERROR);
            existingNameAlert.setTitle("Error");
            existingNameAlert.setHeaderText(null);
            existingNameAlert.setContentText("A terrain with the same name already exists.");
            existingNameAlert.showAndWait();
            return;
        }else {
            connector.add(new ReservationTerrain(selectedDate, selectedTime, idTerrain, 1));

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();


            stage.close();
        }
    }

}
