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

    private Connection connection; // Connexion à la base de données
    private UserService userService; // Service des utilisateurs
    private AlertMessage alert = new AlertMessage(); // Instance de la classe d'alerte

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserService(); // Initialisation du service des utilisateurs
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
        // Ici, nous validons simplement les champs et affichons une alerte, sans effectuer d'actions supplémentaires pour le moment

        String errorMessage = validateFields();
        if (errorMessage != null) {
            alert.errorMessage(errorMessage);
        } else {
            // Afficher l'alerte
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/alerte.fxml"));
                Parent alertRoot = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(alertRoot));
                stage.setTitle("Alert");
                stage.showAndWait(); // Attendre que l'alerte soit fermée
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String validateFields() {
        // Validation des champs du formulaire
        StringBuilder errorMessage = new StringBuilder();

//        // Validation du format de l'email
//        String emailPattern = "[a-zA-Z0-9]+@gmail\\.com";
//        boolean isValidEmail = email.getText().matches(emailPattern);
//        if (!isValidEmail) {
//            errorMessage.append("Please enter a valid Gmail address.\n");
//        }

//        // Validation de l'âge (minimum 18 ans)
//        LocalDate currentDate = LocalDate.now();
//        LocalDate birthDate = birthdate.getValue();
//        long age = ChronoUnit.YEARS.between(birthDate, currentDate);
//        boolean isOver18 = age >= 18;
//        if (!isOver18) {
//            errorMessage.append("You must be at least 18 years old to register.\n");
//        }

//        // Validation de la sélection du genre
//        if (!male.isSelected() && !female.isSelected()) {
//            errorMessage.append("Please select your gender.\n");
//        }

        // Validation du format du numéro de téléphone (8 chiffres)


        // Vérifier si l'utilisateur existe dans la base de données
//        if (isUserInDatabase()) {
//            errorMessage.append("User already exists in the database.\n");
//        }

        return errorMessage.length() == 0 ? null : errorMessage.toString();
    }

//    private boolean isUserInDatabase() {
//        // Vérifier si l'utilisateur existe dans la base de données
//        boolean userExists = false;
//        try {
//            if (connection == null || connection.isClosed()) {
//                // Obtenir la connexion à la base de données
//                connection = DataSource.getInstance().getCnx();
//            }
//
//            String inputUsername = username.getText();
//            String query = "SELECT COUNT(*) FROM user WHERE username = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, inputUsername);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                int count = resultSet.getInt(1);
//                userExists = count > 0;
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return userExists;
//    }
}
