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
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.entities.Role;

import tn.esprit.services.JWTUtil;
import tn.esprit.services.SessionManager;
import tn.esprit.services.UserService;
import tn.esprit.utilis.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import static tn.esprit.entities.Role.*;

public class LoginController implements Initializable {
    @FXML
    private TextField login_username;

    @FXML
    private PasswordField login_password;
    @FXML
    private Hyperlink pass_forget;

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
    @FXML
    private CheckBox pass_show;

    private AlertMessage alert = new AlertMessage();
    private UserService userService = new UserService();
    @FXML
    private Label MG;
    @FXML
    private Label mg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Appliquer l'effet DropShadow à MG lors de l'initialisation
        DropShadow shadow = new DropShadow(20, Color.valueOf("#509dea"));
        MG.setEffect(shadow);

        // Définir le comportement lorsque la souris entre dans le nœud MG
        MG.setOnMouseEntered(event -> {
            if (event.getSource() == MG) {
                DropShadow shadow1 = new DropShadow(40, Color.valueOf("#509dea"));
                shadow1.setRadius(50);
                MG.setEffect(shadow1);
                MG.setStyle("-fx-text-fill: #509dea");
            }
        });
        MG.setOnMouseExited(event -> {
            if (event.getSource() == MG) {
                DropShadow shadow1 = new DropShadow(30, Color.valueOf("#509dea"));
                shadow1.setRadius(20);
                MG.setEffect(shadow1);
                MG.setStyle("-fx-text-fill: #141E30");
            }
        });
        DropShadow shadow2 = new DropShadow(20, Color.valueOf("#509dea"));
        mg.setEffect(shadow2);

        // Définir le comportement lorsque la souris entre dans le nœud MG
        mg.setOnMouseEntered(event -> {
            if (event.getSource() == mg) {
                DropShadow shadow1 = new DropShadow(40, Color.valueOf("#509dea"));
                shadow1.setRadius(50);
                mg.setEffect(shadow1);
                mg.setStyle("-fx-text-fill: #509dea");
            }
        });
        mg.setOnMouseExited(event -> {
            if (event.getSource() == mg) {
                DropShadow shadow1 = new DropShadow(30, Color.valueOf("#509dea"));
                shadow1.setRadius(20);
                mg.setEffect(shadow1);
                mg.setStyle("-fx-text-fill: #141E30");
            }
        });
        // Vérifier si role n'est pas null avant de le configurer
        if (role != null) {
            role.setItems(FXCollections.observableArrayList(Role.values()));
        }

        // Vérifier si birthDatePicker n'est pas null avant de le configurer
        if (birthDatePicker != null) {
            birthDatePicker.setValue(LocalDate.now().minusYears(18));
        }
    }


    @FXML
    public void loginAccount() {
        String username = login_username.getText();
        String password = login_password.getText();

        if (username.isEmpty() || password.isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
            return;
        }

        if (userService.authenticateUser(username, password)) {
            String jwt = JWTUtil.generateJWT(username);
            SessionManager.setJWT(jwt);

            Role userRole = userService.getUserRole(username);
            redirectToView(userRole);
        } else {
            alert.errorMessage("Incorrect Username/Password");
        }
    }

    private void redirectToView(Role role) {
        if (role != null) {
            switch (role) {
                case ADMIN:
                    loadView("/Home.fxml");
                    break;
                case CLIENT:
                    loadView("/EspaceClient.fxml");
                    break;
                case COACH:
                    loadView("/EspaceCoach.fxml");
                    break;
                default:
                    alert.errorMessage("User role not recognized: " + role);
            }
        } else {
            alert.errorMessage("User role is null");
        }
    }


    private void loadView(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btn_login.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            alert.errorMessage("Error loading view");
        }
    }


    @FXML
    public void showPassword() {
        if (pass_show.isSelected()) {
            // Afficher le mot de passe
            login_password.setPromptText(login_password.getText());
            login_password.setText("");
        } else {
            // Masquer le mot de passe
            login_password.setText(login_password.getPromptText());
            login_password.setPromptText("");
        }
    }

    @FXML
    public void redirectToRegisterView() {
        loadView("/Register.fxml");
    }

    @FXML
    public void redirectToLoginView() {
        loadView("/Login.fxml");
    }

    @FXML
    public void redirectToPasswordResetView() {
        loadView("/PasswordReset.fxml");
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

        try (Connection connect = DataSource.getInstance().getCnx()) {
            String insertData = "INSERT INTO user (username, password, email, phone, gender, birthdate, role) " +
                    "VALUES ('" + username.getText() + "', '" + password.getText() +
                    "', '" + email.getText() + "', '" + phone.getText() + "', '" +
                    (male.isSelected() ? "Male" : "Female") + "', '" + java.sql.Date.valueOf(birthDatePicker.getValue()) +
                    "', '" + role.getValue().toString() + "')";

            try (Statement statement = connect.createStatement()) {
                statement.executeUpdate(insertData);
                alert.successMessage("Registered successfully!");
            }
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









}