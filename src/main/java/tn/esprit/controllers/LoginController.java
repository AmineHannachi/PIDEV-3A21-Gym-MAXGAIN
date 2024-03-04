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
import tn.esprit.services.EmailService;
import tn.esprit.services.PasswordHashing;
import tn.esprit.utilis.DataSource;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.ResourceBundle;

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
    private PasswordHashing  hashedPassword = new PasswordHashing();

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
                DropShadow shadow1 =new DropShadow(40,Color.valueOf("#509dea"));
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
            }});
        DropShadow shadow2 = new DropShadow(20, Color.valueOf("#509dea"));
        mg.setEffect(shadow2);

        // Définir le comportement lorsque la souris entre dans le nœud MG
        mg.setOnMouseEntered(event -> {
            if (event.getSource() == mg) {
                DropShadow shadow1 =new DropShadow(40,Color.valueOf("#509dea"));
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
            }});
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
        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            String selectData = "SELECT * FROM user WHERE username = '" + login_username.getText() + "' AND password = '" + login_password.getText() + "'";
            try (Connection connect = DataSource.getInstance().getCnx()) {
                if (connect != null) { // Vérifier si la connexion est établie avec succès
                    try (Statement statement = connect.createStatement()) {
                        try (ResultSet result = statement.executeQuery(selectData)) {
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
        loadView("/Home.fxml");
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

        try (Connection connect = DataSource.getInstance().getCnx()) {
            // Générer le sel aléatoire
            byte[] salt = PasswordHashing.generateSalt();

            // Hacher le mot de passe avec le sel
            String hashedPassword;
            try {
                hashedPassword = PasswordHashing.hashPassword(password.getText(), salt);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                alert.errorMessage("Registration failed. Please try again later.");
                return;
            }

            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String insertData = "INSERT INTO user (username, password, salt, email, phone, gender, birthdate, role) " +
                    "VALUES ('" + username.getText() + "', '" + hashedPassword + "', '" +
                    saltBase64 + "', '" + email.getText() + "', '" +
                    phone.getText() + "', '" + (male.isSelected() ? "Male" : "Female") + "', '" +
                    java.sql.Date.valueOf(birthDatePicker.getValue()) + "', '" + role.getValue().toString() + "')";

            try (Statement statement = connect.createStatement()) {
                statement.executeUpdate(insertData);
                alert.successMessage("Registered successfully!");

                // Chemin absolu de l'image à joindre
                String imagePath = "C:\\xampp\\htdocs\\img\\maxgain.png";

                // Message de bienvenue avec image en pièce jointe
                String welcomeMessage = "Cher " + username.getText() + "  Bienvenue dans notre salle Max-Gain !"
                        + "Nous vous remercions pour votre inscription. Votre compte a été créé avec succès.\n\n"
                        + "Nous sommes ravis de vous accueillir parmi nous et nous vous souhaitons plein de réussite dans votre parcours fitness.\n\n"
                        + "Cordialement,\nVotre équipe Max-Gain";

                // Envoyer l'e-mail de confirmation avec l'image en pièce jointe
                EmailService.sendConfirmationEmailWithAttachment(email.getText(), "Confirmation d'inscription", welcomeMessage, imagePath);
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
    @FXML
    public void redirectToPasswordResetView() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/PasswordReset.fxml"));
            Stage stage = (Stage)pass_forget.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}