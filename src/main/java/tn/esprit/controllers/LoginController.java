package tn.esprit.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.Role;
import tn.esprit.utilis.DataSource;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField login_username;

    @FXML
    private PasswordField login_password;

    @FXML
    private Button btn_login;

    @FXML
    private Hyperlink accountLink;

    @FXML
    private TextField email;

    @FXML
    private JFXRadioButton female;

    @FXML
    private JFXRadioButton male;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phone;

    @FXML
    private JFXComboBox<Role> role;

    @FXML
    private TextField username;

    @FXML
    private PasswordField confirmPass;

    @FXML
    private Button btn_register;

    @FXML
    private Hyperlink Link;

    @FXML
    private DatePicker birthDatePicker;

    private AlertMessage alert = new AlertMessage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (role != null) {
            role.setItems(FXCollections.observableArrayList(Role.values()));
        }
        if ( birthDatePicker != null) {
            birthDatePicker.setValue(LocalDate.now().minusYears(18));
        }
    }

    @FXML
    public void loginAccount() {
        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            String selectData = "SELECT * FROM user WHERE username = ? AND password = ?";
            try (Connection connect = DataSource.getInstance().getCnx()) {
                if (connect != null) { // Vérifier si la connexion est établie avec succès
                    try (PreparedStatement prepare = connect.prepareStatement(selectData)) {
                        prepare.setString(1, login_username.getText());
                        prepare.setString(2, login_password.getText());
                        try (ResultSet result = prepare.executeQuery()) {
                            if (result.next()) {
                                String userRole = result.getString("role");
                                Role role = Role.valueOf(userRole.toUpperCase());
                                switch (role) {
                                    case ADMIN:
                                        redirectToAdminView();
                                        break;
                                    case CLIENT:
                                        redirectToClientView();
                                        break;
                                    case COACH:
                                        redirectToCoachView();
                                        break;
                                    default:
                                        alert.errorMessage("User role not recognized");
                                }
                            } else {
                                alert.errorMessage("Incorrect Username/Password");
                            }
                        }
                    }
                } else {
                    alert.errorMessage("Failed to establish database connection");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void redirectToAdminView() {
        loadView("/EspaceAdmin.fxml");
    }

    private void redirectToClientView() {
        loadView("/EspaceClient.fxml");
    }

    private void redirectToCoachView() {
        loadView("/EspaceCoach.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btn_login.getScene().getWindow(); // Récupérer la fenêtre actuelle
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            alert.errorMessage("Error loading view");
        }
    }

    private boolean validatePasswordsMatch() {
        return password.getText().equals(confirmPass.getText());
    }

    @FXML
    public void registerUser() {
        String errorMessage = validateFields();
        if (errorMessage != null) {
            alert.errorMessage(errorMessage);
            return;
        }

        try {
            Connection connect = DataSource.getInstance().getCnx();
            String insertData = "INSERT INTO user (username, password, email, phone, gender, birthdate, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepare = connect.prepareStatement(insertData);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            prepare.setString(3, email.getText());
            prepare.setString(4, phone.getText());
            prepare.setString(5, male.isSelected() ? "Male" : "Female");
            prepare.setDate(6, java.sql.Date.valueOf(birthDatePicker.getValue()));
            prepare.setString(7, role.getValue().toString());
            prepare.executeUpdate();
            alert.successMessage("Registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            alert.errorMessage("Registration failed. Please try again later.");
        }
    }

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
        LocalDate birthDate = birthDatePicker.getValue();
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

        // Validate passwords match
        if (!validatePasswordsMatch()) {
            errorMessage.append("Passwords do not match.\n");
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
            Connection connection = DataSource.getInstance().getCnx();
            String inputUsername = username.getText(); // Supposons que vous ayez un champ de texte pour le nom d'utilisateur
            String query = "SELECT COUNT(*) FROM user WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, inputUsername); // Utilisez inputUsername ici
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                userExists = count > 0;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
        return userExists;
    }

    @FXML
    public void redirectToRegisterView() {
        // Charger et afficher la vue register.fxml
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Register.fxml"));
            Stage stage = (Stage) accountLink.getScene().getWindow(); // Récupérer la fenêtre actuelle
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void redirectToLoginView() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Stage stage = (Stage) Link.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
