package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.Role;
import tn.esprit.entities.User;
import tn.esprit.services.UserService;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class AlertController implements Initializable {

    @FXML
    private DatePicker birthdatePicker;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField emailField;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private RadioButton maleRadio;

    @FXML
    private TextField phoneField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField usernameField;

    private User user; // Utilisateur à mettre à jour
    private UserService userService;

    // Méthode pour initialiser le contrôleur
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Pré-remplir les champs avec les informations de l'utilisateur s'il s'agit d'une mise à jour
        if (user != null) {
            usernameField.setText(user.getUsername());
            emailField.setText(user.getEmail());
            if (user.getBirthdate() != null) {
                LocalDate birthdate = user.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                birthdatePicker.setValue(birthdate);
            }
            if ("Male".equals(user.getGender())) {
                maleRadio.setSelected(true);
            } else {
                femaleRadio.setSelected(true);
            }
            phoneField.setText(String.valueOf(user.getPhone()));
        }
    }

    // Méthode pour définir l'utilisateur à mettre à jour
    public void setUser(User user) {
        this.user = user;
    }

    // Méthode pour définir le service utilisateur
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // Gestionnaire d'événements pour le bouton "Annuler"
    @FXML
    void cancel(ActionEvent event) {
        // Fermer la fenêtre d'alerte
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    // Gestionnaire d'événements pour le bouton "Enregistrer"
    @FXML
    void save(ActionEvent event) {
        // Récupérer les valeurs des champs
        String newUsername = usernameField.getText();
        String newEmail = emailField.getText();
        LocalDate newBirthdate = birthdatePicker.getValue();
        String newGender = maleRadio.isSelected() ? "Male" : "Female";
        int newPhone = Integer.parseInt(phoneField.getText());

        // Créer un nouvel utilisateur avec les valeurs mises à jour
        java.sql.Date newBirthdateSql = java.sql.Date.valueOf(newBirthdate);
        User newUser = new User(newUsername, newEmail, newBirthdateSql, newGender, newPhone, Role.CLIENT);

        // Si l'utilisateur existe, il s'agit d'une mise à jour
        if (user != null) {
            // Appeler le service pour mettre à jour l'utilisateur dans la base de données
            userService.update(user.getUsername(), newUser);
        } else {
            // Sinon, il s'agit d'un nouvel utilisateur, donc ajouter
            userService.add(newUser);
        }

        // Fermer la fenêtre d'alerte
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    // Méthode pour définir l'utilisateur à mettre à jour
    public void setUserToUpdate(User userToUpdate) {
        this.user = userToUpdate;
    }
}
