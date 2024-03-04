package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.entities.User;
import tn.esprit.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Parent; // Assurez-vous d'importer la classe Parent

public class ItemController implements Initializable {

    @FXML
    private HBox itemC;

    @FXML
    private Label label_birthdate;

    @FXML
    private Label label_email;

    @FXML
    private Label label_gender;

    @FXML
    private Label label_phone;

    @FXML
    private Label label_username;

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

    @FXML
    private TextField password;

    @FXML
    private TextField confirmPass;

    private User user;

    private AlertMessage alert = new AlertMessage();

    private UserService userService;

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            label_username.setText(user.getUsername());
            label_email.setText(user.getEmail());
            label_birthdate.setText(user.getBirthdate().toString());
            label_gender.setText(user.getGender());
            label_phone.setText(String.valueOf(user.getPhone()));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserService();
    }


    @FXML
    void updateClient(ActionEvent event) {
        if (user != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/alert.fxml"));
                Parent alertRoot = fxmlLoader.load(); // Déclarez alertRoot comme Parent
                AlertController alertController = fxmlLoader.getController();
                alertController.setUser(user);
                alertController.setUserService(userService); // Passer l'instance existante de UserService

                Stage stage = new Stage();
                stage.setScene(new Scene(alertRoot));
                stage.setTitle("Edit User");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert.errorMessage("No client selected !");
        }
    }



    @FXML
    private void deleteClient(ActionEvent event) {
        String message = "Are you sure you want to delete the client " + user.getUsername() + " ?";
        if (alert.confirmMessage(message)) {
            userService.delete(user);
            refreshVBox(); // Mettre à jour le VBox après la suppression
            alert.successMessage("Client deleted successfully !");
        }
    }

    private void refreshVBox() {
        // Effacer les enfants actuels du VBox
        itemC.getChildren().clear();
        // Recharger les clients dans le VBox
        userService.getAllClients().forEach(client -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/item.fxml"));
                Node node = loader.load();
                ItemController itemController = loader.getController();
                itemController.setUser(client);
                itemC.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
