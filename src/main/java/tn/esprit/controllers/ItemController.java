package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import tn.esprit.entities.User;
import tn.esprit.services.ClientService;
import tn.esprit.utilis.DataSource;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

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

    private ClientService clientService;

    private Connection connection;

    private User user;

    private AlertMessage alert = new AlertMessage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientService = new ClientService();
    }

    // Méthode pour mettre à jour les valeurs des labels avec les données de l'utilisateur
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

    @FXML
    void deleteClient(ActionEvent event) {
        if (user != null) {
            String message = "Are you sure you want to delete the client " + user.getUsername() + " ?";
            if (alert.confirmMessage(message)) {
                clientService.deleteClient(user.getUsername());
                refreshVBox(); // Mettre à jour le VBox après la suppression
                alert.successMessage("Client deleted successfully !");
            }
        } else {
            alert.errorMessage("No client selected !");
        }
    }

//    @FXML
//    void addClient(ActionEvent event) {
//        String errorMessage = validateFields();
//        if (errorMessage != null) {
//            alert.errorMessage(errorMessage);
//        } else {
//            LocalDate localDate = birthdate.getValue();
//            if (localDate != null) {
//                java.sql.Date javaDate = java.sql.Date.valueOf(localDate);
//                clientService.addClient(username.getText(), email.getText(), javaDate,
//                        male.isSelected() ? "Male" : "Female", Integer.parseInt(phone.getText()),
//
//                alert.successMessage("Client added successfully!"),
//            } else {
//                alert.errorMessage("Please select a valid birthdate.");
//            }
//        }
//    }

    private String validateFields() {
        StringBuilder errorMessage = new StringBuilder();

        // Validate email format
        String emailPattern = "[a-zA-Z0-9]+@gmail\\.com";
        boolean isValidEmail = email.getText().matches(emailPattern);
        if (!isValidEmail) {
            errorMessage.append("Please enter a valid Gmail address.\n");
        }

        // Validate age (minimum 18 years old)
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = birthdate.getValue();
        long age = ChronoUnit.YEARS.between(birthDate, currentDate);
        boolean isOver18 = age >= 18;
        if (!isOver18) {
            errorMessage.append("You must be at least 18 years old to register.\n");
        }

        // Validate gender selection
        if (!male.isSelected() && !female.isSelected()) {
            errorMessage.append("Please select your gender.\n");
        }

        // Validate phone number format (8 digits)
        String phoneNumber = phone.getText();
        if (phoneNumber.length() != 8 || !phoneNumber.matches("\\d{8}")) {
            errorMessage.append("Please enter a valid phone number with 8 digits.\n");
        }

        // Check if the user exists in the database
        if (isUserInDatabase()) {
            errorMessage.append("User already exists in the database.\n");
        }

        return errorMessage.length() == 0 ? null : errorMessage.toString();
    }

    private boolean isUserInDatabase() {
        boolean userExists = false;
        try {
            if (connection == null || connection.isClosed()) {
                // Get the database connection (you need to implement DataSource class)
                connection = DataSource.getInstance().getCnx();
            }

            String inputUsername = username.getText();
            String query = "SELECT COUNT(*) FROM user WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, inputUsername);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                userExists = count > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userExists;
    }

    // Méthode pour rafraîchir le VBox après la suppression
    private void refreshVBox() {
//        // Effacer les enfants actuels du VBox
//        itemC.getChildren().clear();
//        // Recharger les clients dans le VBox
//        clientService.getAllClients().forEach(client -> {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/item.fxml"));
//                Node node = loader.load();
//                ItemController itemController = loader.getController();
//                itemController.setUser(client);
//                itemC.getChildren().add(node);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }
}
