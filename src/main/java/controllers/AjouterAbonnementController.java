package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Abonnement;
import models.Offres;
import services.AbonnementService;
import services.OffresService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AjouterAbonnementController {

    @FXML
    private TextField abonnementName;

    @FXML
    private TextField abonnementEmail;

    @FXML
    private TextField abonnementSalle;

    @FXML
    private TextField abonnementMpayement;

    @FXML
    private DatePicker abonnementDate;

    @FXML
    private ComboBox<String> abonnementOfferComboBox;

    @FXML
    private TableView<Abonnement> affichageAbonnement;

    @FXML
    private TableColumn<Abonnement, Integer> abonnementIdCol;

    @FXML
    private TableColumn<Abonnement, String> nameCol;

    @FXML
    private TableColumn<Abonnement, String> emailCol;

    @FXML
    private TableColumn<Abonnement, String> salleCol;

    @FXML
    private TableColumn<Abonnement, String> mpayementCol;

    @FXML
    private TableColumn<Abonnement, String> dateDCol;

    @FXML
    private TableColumn<Abonnement, Integer> offreIdCol;

    @FXML
    private TableColumn<Abonnement, String> offreDetailsCol;

    @FXML
    private Button ajouterAbonnementBtn;

    @FXML
    private Button modifierAbonnementBtn;

    @FXML
    private Button supprimerAbonnementBtn;

    @FXML
    private Button refreshBtn;
    @FXML
    private Button ajouterOffreBtn;

    private final AbonnementService abonnementService = new AbonnementService();

    private ObservableList<Abonnement> abonnementObservableList;

    @FXML

    private void initialize() {
        OffresService offresService = new OffresService();
        populateOfferComboBox(offresService);
        initializeTableView();
        refreshTable();
    }

    private void populateOfferComboBox(OffresService offresService) {
        try {
            List<Offres> offresList = offresService.recuperer();
            ObservableList<String> offerDescriptions = FXCollections.observableArrayList();
            for (Offres offre : offresList) {
                offerDescriptions.add(offre.getDescription());
            }
            abonnementOfferComboBox.setItems(offerDescriptions);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your needs
        }
    }


    private void initializeTableView() {
        abonnementIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        salleCol.setCellValueFactory(new PropertyValueFactory<>("salle"));
        mpayementCol.setCellValueFactory(new PropertyValueFactory<>("mpayement"));
        dateDCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        offreIdCol.setCellValueFactory(new PropertyValueFactory<>("offreId"));
        offreDetailsCol.setCellValueFactory(new PropertyValueFactory<>("offer.details"));

        abonnementObservableList = FXCollections.observableArrayList();
        affichageAbonnement.setItems(abonnementObservableList);
    }

    private void refreshTableData() {
        try {
            List<Abonnement> abonnements = abonnementService.recuperer();
            abonnementObservableList.setAll(abonnements);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your needs
        }
    }

    @FXML
    private void ajouterAbonnement(ActionEvent event) {
        try {
            String name = abonnementName.getText();
            String email = abonnementEmail.getText();
            String salle = abonnementSalle.getText();
            String mpayement = abonnementMpayement.getText();
            LocalDate date = abonnementDate.getValue();

            // Validation
            if (!isValidEmail(email)) {
                showAlert("Veuillez saisir une adresse e-mail valide.");
                return;
            }

            if (name.isEmpty() || salle.isEmpty()) {
                showAlert("Veuillez remplir tous les champs obligatoires.");
                return;
            }

            if (containsNumbers(name)) {
                showAlert("Le champ Nom ne doit pas contenir de chiffres.");
                return;
            }

            if (containsNumbers(salle)) {
                showAlert("Le champ Salle ne doit pas contenir de chiffres.");
                return;
            }

            Abonnement abonnement = new Abonnement();
            abonnement.setName(name);
            abonnement.setEmail(email);
            abonnement.setSalle(salle);
            abonnement.setMpayement(mpayement);
            abonnement.setDate(date);

            // Set the offer ID based on the selected value in the ComboBox
            String selectedOfferDescription = abonnementOfferComboBox.getValue();
            int offerId = getOfferIdFromDescription(selectedOfferDescription); // Get offer ID from description
            abonnement.setOffreId(offerId);

            abonnementService.ajouter(abonnement);
            refreshTable();
        } catch (SQLException e) {
            showErrorMessage("Error", e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to check if a string contains numbers
    private boolean containsNumbers(String input) {
        return input.matches(".*\\d.*");
    }

    private boolean isValidEmail(String email) {
        // Use a regular expression to validate the email address
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


    // You need to implement this method to get the offer ID based on the selected offer description
    private int getOfferIdFromDescription(String selectedOfferDescription) {
        try {
            OffresService offresService = new OffresService();
            List<Offres> offresList = offresService.recuperer();
            for (Offres offre : offresList) {
                if (offre.getDescription().equals(selectedOfferDescription)) {
                    return offre.getId();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return a default offer ID if the selected offer description is not found
        return 0;
    }


    @FXML
    private void modifierAbonnement(ActionEvent event) {
        Abonnement selectedAbonnement = affichageAbonnement.getSelectionModel().getSelectedItem();
        if (selectedAbonnement != null) {
            // Populate the UI elements with the selected abonnement's data
            abonnementName.setText(selectedAbonnement.getName());
            abonnementEmail.setText(selectedAbonnement.getEmail());
            abonnementSalle.setText(selectedAbonnement.getSalle());
            abonnementMpayement.setText(selectedAbonnement.getMpayement());
            abonnementDate.setValue(selectedAbonnement.getDate());

            // Set the selected offer in the ComboBox
            abonnementOfferComboBox.setValue(getOfferLabelFromId(selectedAbonnement.getOffreId()));

            // Disable the ajouterAbonnementBtn while modifying
            ajouterAbonnementBtn.setDisable(true);
        } else {
            showErrorMessage("Error", "Please select an abonnement to modify.");
        }
    }

    // You need to implement this method to get the offer label based on the offer ID
    private String getOfferLabelFromId(int offerId) {
        // Implement the logic to get the offer label based on the offer ID
        // For simplicity, you can hardcode values or fetch from a database
        // Replace the following line with your actual logic
        return offerId == 1 ? "Offer 1" : "Offer 2";
    }


    @FXML
    private void validerModification(ActionEvent event) {
        Abonnement selectedAbonnement = affichageAbonnement.getSelectionModel().getSelectedItem();
        if (selectedAbonnement != null) {
            try {
                // Update the selected Abonnement with the modified data
                selectedAbonnement.setName(abonnementName.getText());
                selectedAbonnement.setEmail(abonnementEmail.getText());
                selectedAbonnement.setSalle(abonnementSalle.getText());
                selectedAbonnement.setMpayement(abonnementMpayement.getText());
                selectedAbonnement.setDate(abonnementDate.getValue());

                // You may need to set the offer ID based on the selected value in the ComboBox

                // Perform the modification in the database
                abonnementService.modifier(selectedAbonnement);

                // Enable the ajouterAbonnementBtn after modification
                ajouterAbonnementBtn.setDisable(false);

                // Clear the text fields
                clearFields();

                // Refresh the table
                refreshTable();
            } catch (SQLException e) {
                showErrorMessage("Error", e.getMessage());
            }
        } else {
            showErrorMessage("Error", "Please select an abonnement to modify.");
        }
    }

    @FXML
    private void annulerModification(ActionEvent event) {
        // Enable the ajouterAbonnementBtn when canceling modification
        ajouterAbonnementBtn.setDisable(false);

        // Clear the text fields
        clearFields();
    }

    @FXML
    private void supprimerAbonnement(ActionEvent event) {
        // Handle the action for deleting an abonnement
        Abonnement selectedAbonnement = affichageAbonnement.getSelectionModel().getSelectedItem();
        if (selectedAbonnement != null) {
            try {
                abonnementService.supprimer(selectedAbonnement.getId());
                refreshTable();
            } catch (SQLException e) {
                showErrorMessage("Error", e.getMessage());
            }
        } else {
            showErrorMessage("Error", "Please select an abonnement to delete.");
        }
    }

    @FXML
    private void refreshTable() {
        refreshTableData();
    }

    private void showErrorMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void clearFields() {
        abonnementName.clear();
        abonnementEmail.clear();
        abonnementSalle.clear();
        abonnementMpayement.clear();
        abonnementDate.setValue(null);
        abonnementOfferComboBox.setValue(null);
    }
    @FXML
    private void ajouterOffreButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterOffres.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }
}
