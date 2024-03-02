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
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.controllers.ItemController;
import tn.esprit.entities.User;
import tn.esprit.services.ClientService;

import java.io.IOException;
import java.net.URL;
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

    private ClientService clientService; // Déclaration du service client

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        clientService = new ClientService(); // Initialisation du service client
//
//        // Récupération de tous les clients depuis le service
////        ObservableList<User> clients = clientService.getAllClients();
//
//        // Affichage des clients dans le VBox
//        for (User client : clients) {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/item.fxml"));
//                Node node = loader.load();
//                ItemController itemController = loader.getController();
//                itemController.setUser(client);
//                pnItems.getChildren().add(node);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnClient) {
            pnlCustomer.setStyle("-fx-background-color : #ffffff");
            pnlCustomer.toFront();
        }
        if (actionEvent.getSource() == btnMenus) {
            pnlMenus.setStyle("-fx-background-color : #FF69B4F");
            pnlMenus.toFront();
        }
        if (actionEvent.getSource() == btnCoach) {
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();
        }
        if (actionEvent.getSource() == btnSalle) {
            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();
        }
    }

    @FXML
    public void logout(ActionEvent event) {
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
}
