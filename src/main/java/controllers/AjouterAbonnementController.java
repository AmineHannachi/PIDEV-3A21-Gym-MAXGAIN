package controllers;


import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Cell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Abonnement;
import models.Offres;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.AbonnementService;
import services.OffresService;
import javafx.scene.control.TableRow;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
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
    private Button statisticsButton;
    @FXML
    private Button ajouterOffreBtn;

    @FXML
    private RadioButton sortNameRadio;

    @FXML
    private RadioButton sortSalleRadio;

    @FXML
    private RadioButton sortOffreRadio;
    @FXML
    private Button generateQRCodeBtn;



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
        abonnementIdCol.setVisible(false);
        offreIdCol.setVisible(false);
        // Use a Callback to retrieve the offer details from the model
        offreDetailsCol.setCellValueFactory(cellData -> {
            Abonnement abonnement = cellData.getValue();
            return new SimpleStringProperty(abonnement.getOfferDescription());
        });

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


    private void sortAbonnements() {
        // Get the data from the TableView
        ObservableList<Abonnement> data = affichageAbonnement.getItems();

        // Sort based on the selected RadioButton
        if (sortNameRadio.isSelected()) {
            Collections.sort(data, Comparator.comparing(Abonnement::getName));
        } else if (sortSalleRadio.isSelected()) {
            Collections.sort(data, Comparator.comparing(Abonnement::getSalle));
        } else if (sortOffreRadio.isSelected()) {
            Collections.sort(data, Comparator.comparing(Abonnement::getOfferDescription));
        }

        // Set the sorted data back to the TableView
        affichageAbonnement.setItems(FXCollections.observableArrayList(data));
    }
    @FXML
    public void sortTable(ActionEvent event) {
        sortAbonnements();
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

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void generateQRCode(ActionEvent event) {
        try {
            String abonnementDetails = generateAbonnementDetails();

            if (abonnementDetails.isEmpty()) {
                showAlert("Error", "Abonnement details are empty.", "Please fill in the details before generating a QR code.");
                return;
            }

            String filePath = "C:\\Users\\amine\\Desktop\\QR code\\QRCode.png";

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(abonnementDetails, BarcodeFormat.QR_CODE, 300, 300);

            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get(filePath));
            showAlert("QR Code Generated", null, "QR Code has been generated and saved.");
        } catch (WriterException | IOException e) {
            showAlert("Error", "Failed to generate QR Code", e.getMessage());
        }
    }



    private String generateAbonnementDetails() {
        Abonnement selectedAbonnement = affichageAbonnement.getSelectionModel().getSelectedItem();
        if (selectedAbonnement != null) {
            return String.format("Name: %s\nEmail: %s\nSalle: %s\nDate: %s",
                    selectedAbonnement.getName(), selectedAbonnement.getEmail(),
                    selectedAbonnement.getSalle(), selectedAbonnement.getDate());
        }
        return "";
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




    @FXML
    private void generateExcel(ActionEvent event) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Abonnement Data");

        // Add headers
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Email");
        headerRow.createCell(2).setCellValue("Salle");
        headerRow.createCell(3).setCellValue("Mpayement");
        headerRow.createCell(4).setCellValue("Date");
        headerRow.createCell(5).setCellValue("Offre");

        // Add data - assuming Abonnement is the type of your TableView items
        TableView<Abonnement> tableView = affichageAbonnement;
        ObservableList<Abonnement> data = tableView.getItems();
        int rowNum = 1;
        for (Abonnement abonnement : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(abonnement.getName());
            row.createCell(1).setCellValue(abonnement.getEmail());
            row.createCell(2).setCellValue(abonnement.getSalle());
            row.createCell(3).setCellValue(abonnement.getMpayement());
            row.createCell(4).setCellValue(abonnement.getDate().toString());
            row.createCell(5).setCellValue(abonnement.getOfferDescription());
        }

        try {
            // Specify the file path on your desktop
            String desktopPath = System.getProperty("user.home") + "\\Desktop\\excel\\";
            String filePath = desktopPath + "AbonnementData.xlsx";

            // Create directories if they don't exist
            File directory = new File(desktopPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Write the workbook content to the specified file
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }


    @FXML
    private void openStatisticsInterface(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StatisticsInterface.fxml"));
            Parent root = loader.load();

            // Create a new stage for the statistics interface
            Stage stage = new Stage();
            stage.setTitle("Statistics");
            stage.setScene(new Scene(root));

            // Show the statistics interface
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }}


//       try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAbonnement.fxml"));
//            Parent root = loader.load();
//
//            // Get the current stage
//            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//
//            // Set the new scene
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle the exception according to your needs
//        }
//    }
