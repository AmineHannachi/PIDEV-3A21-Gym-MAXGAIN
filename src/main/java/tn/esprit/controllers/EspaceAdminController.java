package tn.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.entities.User;
import tn.esprit.services.ClientService;
import tn.esprit.utilis.DataSource;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class EspaceAdminController implements Initializable {
    private ClientService clientService = new ClientService();
    private Connection connection;

    @FXML
    private Button addBtn;
    @FXML
    private TextField confirmPass;
    @FXML
    private TableView<User> addClient_tableView;
    @FXML
    private TextField addClientsearch;
    @FXML
    private DatePicker birthdate;

    @FXML
    private JFXButton btn_client;
    @FXML
    private JFXButton btn_coach;
    @FXML
    private JFXButton btn_event;
    @FXML
    private JFXButton btn_home;
    @FXML
    private JFXButton btn_offre;
    @FXML
    private JFXButton btn_salle;
    @FXML
    private JFXButton btn_terr;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, Date> colBirthdate;
    @FXML
    private TableColumn<User, String> colGender;
    @FXML
    private TableColumn<User, Integer> colPhone;
    @FXML
    private TableColumn<User, String> colConfirmPass;
    @FXML
    private TableColumn<User, String> colPassword;
    @FXML
    private TextField email;
    @FXML
    private JFXRadioButton female;
    @FXML
    private ImageView home_form_image;
    @FXML
    private JFXRadioButton male;
    @FXML
    private TextField password;
    @FXML
    private TextField phone;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField username;

    private AlertMessage alert = new AlertMessage();
    private final ToggleGroup genderToggleGroup = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        male.setToggleGroup(genderToggleGroup);
        female.setToggleGroup(genderToggleGroup);
        birthdate.setValue(LocalDate.now());
        initializeTableView();

        addClient_tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0) {
                User selectedClient = addClient_tableView.getSelectionModel().getSelectedItem();
                if (selectedClient != null) {
                    displaySelectedUserDetails(selectedClient);
                }
            }
        });
    }

    private void initializeTableView() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colConfirmPass.setCellValueFactory(new PropertyValueFactory<>("confirmPass"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

//        refreshTableView();
    }

//    private void refreshTableView() {
//        addClient_tableView.setItems(clientService.getAllClients());
//    }

    @FXML
    void addClient(ActionEvent event) {
        String errorMessage = validateFields();
        if (errorMessage != null) {
            alert.errorMessage(errorMessage);

        }

//        LocalDate localDate = birthdate.getValue();
//        if (localDate != null) {
//            Date javaDate = Date.valueOf(localDate);
//            clientService.addClient(username.getText(), email.getText(), javaDate,
//                    male.isSelected() ? "Male" : "Female", Integer.parseInt(phone.getText()),
//                    password.getText(), confirmPass.getText());
//            alert.successMessage("Client added successfully!");
//            refreshTableView();
//        } else {
//            alert.errorMessage("Please select a valid birthdate.");
//        }
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

    private boolean validatePasswordsMatch() {
        return password.getText().equals(confirmPass.getText());
    }

    private boolean isUserInDatabase() {
        boolean userExists = false;
        try {
            if (connection == null || connection.isClosed()) {
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

    private void displaySelectedUserDetails(User selectedUser) {
        if (selectedUser != null) {
            username.setText(selectedUser.getUsername());
            email.setText(selectedUser.getEmail());
            java.util.Date utilDate = selectedUser.getBirthdate();
            java.sql.Date birthDate = new java.sql.Date(utilDate.getTime());
            LocalDate localDate = birthDate.toLocalDate();
            birthdate.setValue(localDate);
            if (selectedUser.getGender().equalsIgnoreCase("Male")) {
                male.setSelected(true);
            } else {
                female.setSelected(true);
            }
            phone.setText(String.valueOf(selectedUser.getPhone()));
            password.setText(selectedUser.getPassword());
            confirmPass.setText(selectedUser.getConfirmPass());
        } else {
            clearFields();
        }
    }

    @FXML
    void deleteClient(ActionEvent event) {
        User selectedClient = addClient_tableView.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            String message = "Are you sure you want to delete the client " + selectedClient.getUsername() + " ?";
            if (alert.confirmMessage(message)) {
                clientService.deleteClient(selectedClient.getUsername());
//                refreshTableView();
                alert.successMessage("Client deleted successfully !");
            }
        } else {
            alert.errorMessage("No client selected !");
        }
    }
//
//    @FXML
//    void searchClient(ActionEvent event) {
//        String username = addClientsearch.getText();
//        User client = clientService.getClientByUsername(username);
//        if (client != null) {
//            displaySelectedUserDetails(client);
//        } else {
//            clearFields();
//            alert.errorMessage("No client found with username: " + username);
//        }
//    }

    @FXML
    void updateClient(ActionEvent event) {
        User selectedClient = addClient_tableView.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            String newUsername = username.getText();
            String newEmail = email.getText();
            LocalDate newBirthdate = birthdate.getValue();
            String newGender = male.isSelected() ? "Male" : "Female";
            int newPhone = Integer.parseInt(phone.getText());
            String newPassword = password.getText();
            String newConfirmPass = confirmPass.getText();

            String message = "Are you sure you want to update the client information " + selectedClient.getUsername() + " ?";
            if (alert.confirmMessage(message)) {
                User newUser = new User();
                newUser.setUsername(newUsername);
                newUser.setEmail(newEmail);
                newUser.setBirthdate(Date.valueOf(newBirthdate));
                newUser.setGender(newGender);
                newUser.setPhone(newPhone);
                newUser.setPassword(newPassword);
                newUser.setConfirmPass(newConfirmPass);

                clientService.updateClient(selectedClient.getUsername(), newUsername, newEmail, Date.valueOf(newBirthdate),
                        newGender, newPhone, newPassword, newConfirmPass);

                alert.successMessage("Client information updated successfully !");
//                refreshTableView();
            }
        } else {
            alert.errorMessage("No client selected !");
        }
    }



    private void clearFields() {
        username.clear();
        email.clear();
        birthdate.setValue(LocalDate.now());
        male.setSelected(false);
        female.setSelected(false);
        phone.clear();
        password.clear();
        confirmPass.clear();
    }
}
