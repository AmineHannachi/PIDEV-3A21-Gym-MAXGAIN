package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Spinner;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import models.Offres;
import services.OffresService;

import java.sql.SQLException;

public class AjouterOffresController {

    @FXML
    private TableView<Offres> affichageOffres;

    @FXML
    private TableColumn<Offres, Integer> offreIdCol;

    @FXML
    private TableColumn<Offres, String> formattedStringCol; // Add a new column for formatted string

    @FXML
    private TableColumn<Offres, Double> prixCol;

    @FXML
    private TableColumn<Offres, Integer> dureeCol;

    @FXML
    private TableColumn<Offres, String> descriptionCol;

    @FXML
    private TextField prixField;

    @FXML
    private Spinner<Integer> dureeField;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button ajouterOffreBtn;

    @FXML
    private Button modifierOffreBtn;

    @FXML
    private Button supprimerOffreBtn;

    @FXML
    private Button refreshOffresBtn;

    private OffresService offresService;
    private ObservableList<Offres> offresList;

    public AjouterOffresController() {
        offresService = new OffresService();
        offresList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        configureTable();
        loadData();

        affichageOffres.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            supprimerOffreBtn.setDisable(newValue == null);
        });
    }

    private void configureTable() {
        offreIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        dureeCol.setCellValueFactory(new PropertyValueFactory<>("duree"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        prixCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        dureeCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());

        prixCol.setOnEditCommit(this::updatePrix);
        dureeCol.setOnEditCommit(this::updateDuree);
        descriptionCol.setOnEditCommit(this::updateDescription);

        affichageOffres.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        affichageOffres.setEditable(true);

        supprimerOffreBtn.setDisable(true);
        prixCol.setCellFactory(column -> new TableCell<Offres, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(String.format("%.2f DT", item));
                }
            }
        });
        dureeCol.setCellFactory(column -> new TableCell<Offres, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item + " mois");
                }
            }
        });

    }

    private void loadData() {
        try {
            offresList.clear();
            offresList.addAll(offresService.recuperer());
            affichageOffres.setItems(offresList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterOffre(ActionEvent event) {
        try {
            if (validateInput()) {
                double prix = Double.parseDouble(prixField.getText());
                int duree = dureeField.getValue();
                String description = descriptionField.getText();

                Offres newOffre = new Offres(prix, duree, description);
                offresService.ajouter(newOffre);

                loadData();
                clearFields();
            } else {
                showErrorMessage("Error", "Please enter valid data in all fields.");
            }
        } catch (NumberFormatException | SQLException e) {
            handleException(e, "Error adding new offer");
        }
    }

    @FXML
    private void modifierOffre(ActionEvent event) {
        Offres selectedOffre = affichageOffres.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            prixField.setText(String.valueOf(selectedOffre.getPrix()));
            dureeField.getValueFactory().setValue(selectedOffre.getDuree());
            descriptionField.setText(selectedOffre.getDescription());

            ajouterOffreBtn.setDisable(true);
        } else {
            showErrorMessage("Error", "Please select an offer to modify.");
        }
    }

    @FXML
    private void validerModificationOffre(ActionEvent event) {
        try {
            if (validateInput()) {
                Offres selectedOffre = affichageOffres.getSelectionModel().getSelectedItem();
                if (selectedOffre != null) {
                    selectedOffre.setPrix(Double.parseDouble(prixField.getText()));
                    selectedOffre.setDuree(dureeField.getValue());
                    selectedOffre.setDescription(descriptionField.getText());

                    offresService.modifier(selectedOffre);

                    clearFields();
                    loadData();

                    ajouterOffreBtn.setDisable(false);
                } else {
                    showErrorMessage("Error", "Please select an offer to modify.");
                }
            } else {
                showErrorMessage("Error", "Please enter valid data in all fields.");
            }
        } catch (NumberFormatException | SQLException e) {
            handleException(e, "Error updating offer");
        }
    }

    private boolean validateInput() {
        return !prixField.getText().trim().isEmpty()
                && !descriptionField.getText().trim().isEmpty();
    }

    private void refreshTable() {
        // Add logic to refresh the table if needed
    }

    @FXML
    private void annulerModificationOffre(ActionEvent event) {
        ajouterOffreBtn.setDisable(false);
        clearFields();
    }

    @FXML
    void supprimerOffre(ActionEvent event) {
        Offres selectedOffre = affichageOffres.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            try {
                offresService.supprimer(selectedOffre.getId());
                loadData();
            } catch (SQLException e) {
                handleException(e, "Error deleting offer");
            }
        }
    }

    @FXML
    void refreshOffres(ActionEvent event) {
        loadData();
    }

    private void updatePrix(TableColumn.CellEditEvent<Offres, Double> event) {
        Offres selectedOffre = affichageOffres.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            selectedOffre.setPrix(event.getNewValue());
            try {
                offresService.modifier(selectedOffre);
            } catch (SQLException e) {
                handleException(e, "Error updating offer");
            }
        }
    }

    private void updateDuree(TableColumn.CellEditEvent<Offres, Integer> event) {
        Offres selectedOffre = affichageOffres.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            selectedOffre.setDuree(event.getNewValue());
            try {
                offresService.modifier(selectedOffre);
            } catch (SQLException e) {
                handleException(e, "Error updating offer");
            }
        }
    }

    private void updateDescription(TableColumn.CellEditEvent<Offres, String> event) {
        Offres selectedOffre = affichageOffres.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            selectedOffre.setDescription(event.getNewValue());
            try {
                offresService.modifier(selectedOffre);
            } catch (SQLException e) {
                handleException(e, "Error updating offer");
            }
        }
    }

    private void clearFields() {
        prixField.clear();
        dureeField.getValueFactory().setValue(0);
        descriptionField.clear();
    }

    private void showErrorMessage(String error, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(error);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleException(Exception e, String errorMessage) {
        e.printStackTrace();
        showErrorMessage("Exception", errorMessage + ": " + e.getMessage());
    }
}
