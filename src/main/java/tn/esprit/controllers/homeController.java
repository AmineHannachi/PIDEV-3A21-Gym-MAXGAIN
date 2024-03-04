package tn.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.Role;
import tn.esprit.entities.User;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class homeController implements Initializable {
    @FXML
    private Button btnClient;
    @FXML
    private Button btnCoach;
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnPackages;
    @FXML
    private Button btnSalle;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnSignout;
    @FXML
    private JFXButton btn_add;
    @FXML
    private VBox pnItems;
    @FXML
    private Button btnMenus;
    @FXML
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlOverview;
    @FXML
    private Pane pnlMenus;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private DatePicker birthdate;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private TextField phone;

    private User userToUpdate; // Utilisateur à mettre à jour
    private UserService userService; // Service des utilisateurs
    private AlertMessage alert = new AlertMessage(); // Instance de la classe d'alerte

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserService(); // Initialisation du service des utilisateurs
        // Récupération de tous les clients depuis le service
        refreshClientList();
    }

    private void refreshClientList() {
        pnItems.getChildren().clear(); // Effacer la liste actuelle des clients

        // Récupération de tous les clients depuis le service
        ObservableList<User> clients = userService.getAllClients();

        // Affichage des clients dans le VBox
        for (User client : clients) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/item.fxml"));
                Node node = loader.load();
                ItemController itemController = loader.getController();
                itemController.setUser(client);
                pnItems.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) {
        // Gestion des actions des boutons
        if (actionEvent.getSource() == btn_add) {
            // Bouton Ajouter cliqué
            openAlert(null); // Ouvrir l'alerte pour ajouter un nouvel utilisateur
        }
    }

    @FXML
    public void logout(ActionEvent event) {
        // Déconnexion de l'utilisateur
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void addClient(ActionEvent event) {
        // Ajout d'un nouveau client
        openAlert(null); // Ouvrir l'alerte pour ajouter un nouvel utilisateur
    }

    // Méthode pour ouvrir l'alerte pour ajouter ou mettre à jour un utilisateur
    private void openAlert(User userToUpdate) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/alerte.fxml"));
            Parent alertRoot = fxmlLoader.load();

            AlertController alertController = fxmlLoader.getController();
            alertController.setUserService(userService);
            alertController.setUserToUpdate(userToUpdate); // Définir l'utilisateur à mettre à jour

            Stage stage = new Stage();
            stage.setScene(new Scene(alertRoot));
            stage.setTitle("Alert");
            stage.showAndWait(); // Attendre que l'alerte soit fermée

            // Rafraîchir la liste des clients après l'ajout ou la mise à jour
            refreshClientList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
