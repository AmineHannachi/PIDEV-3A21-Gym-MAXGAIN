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
import java.sql.Date;
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

    private User user;
    private UserService userService;

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (user != null) {
            // Remplir les champs avec les informations de l'utilisateur
            usernameField.setText(user.getUsername());
            emailField.setText(user.getEmail());
            // Vérifier si la date de naissance n'est pas null avant de l'assigner
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


    @FXML
    void cancel(ActionEvent event) {
        // Fermer la fenêtre d'alerte
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
        // Récupérer les valeurs des champs
        String oldUsername = user.getUsername(); // Ancien nom d'utilisateur
        String newUsername = usernameField.getText();
        String newEmail = emailField.getText();
        LocalDate newBirthdate = birthdatePicker.getValue();
        String newGender = maleRadio.isSelected() ? "Male" : "Female";
        int newPhone = Integer.parseInt(phoneField.getText());
        String newPassword = ""; // Assumer que le mot de passe n'est pas modifié pour l'instant

        // Créer un nouvel utilisateur avec les valeurs mises à jour et le rôle par défaut "Client"
        java.sql.Date newBirthdateSql = java.sql.Date.valueOf(newBirthdate);
        User newUser = new User(newUsername, newEmail, newBirthdateSql,
                newGender, newPhone, Role.CLIENT);

        // Appeler le service pour mettre à jour l'utilisateur dans la base de données
        userService.update(oldUsername, newUser);

        // Fermer la fenêtre d'alerte
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
