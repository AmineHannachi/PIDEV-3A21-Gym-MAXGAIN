package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CalorieCalculatorController {

    @FXML
    private TextField ageTextField;

    @FXML
    private RadioButton hommeRadioButton;

    @FXML
    private RadioButton femmeRadioButton;

    @FXML
    private TextField tailleTextField;

    @FXML
    private TextField poidsTextField;

    @FXML
    private RadioButton perdrePoidsRadioButton;

    @FXML
    private RadioButton maintenanceRadioButton;

    @FXML
    private RadioButton priseMasseRadioButton;

    @FXML
    private RadioButton legereActiviteRadioButton;

    @FXML
    private RadioButton modereeActiviteRadioButton;

    @FXML
    private RadioButton tresActifRadioButton;

    @FXML
    private RadioButton extraActifRadioButton;

    @FXML
    private TextField resultTextField;

    @FXML
    private Button calculateButton;

    @FXML
    private Button resetButton;

    @FXML
    private Label resultLabel;

    private final ToggleGroup genderToggleGroup = new ToggleGroup();
    private final ToggleGroup goalToggleGroup = new ToggleGroup();
    private final ToggleGroup activityLevelToggleGroup = new ToggleGroup();

    @FXML
    private void initialize() {
        hommeRadioButton.setToggleGroup(genderToggleGroup);
        femmeRadioButton.setToggleGroup(genderToggleGroup);

        perdrePoidsRadioButton.setToggleGroup(goalToggleGroup);
        maintenanceRadioButton.setToggleGroup(goalToggleGroup);
        priseMasseRadioButton.setToggleGroup(goalToggleGroup);

        legereActiviteRadioButton.setToggleGroup(activityLevelToggleGroup);
        modereeActiviteRadioButton.setToggleGroup(activityLevelToggleGroup);
        tresActifRadioButton.setToggleGroup(activityLevelToggleGroup);
        extraActifRadioButton.setToggleGroup(activityLevelToggleGroup);
    }

    @FXML
    private void calculateCalories() {
        try {
            // Get user input
            int age = Integer.parseInt(ageTextField.getText());
            double height = Double.parseDouble(tailleTextField.getText());
            double weight = Double.parseDouble(poidsTextField.getText());

            // Calculate BMR based on gender
            double bmr;
            if (hommeRadioButton.isSelected()) {
                bmr = 10 * weight + 6.25 * height - 5 * age + 5;
            } else if (femmeRadioButton.isSelected()) {
                bmr = 10 * weight + 6.25 * height - 5 * age - 161;
            } else {
                showAlert("Veuillez sélectionner le sexe.");
                return;
            }

            // Adjust BMR based on goal
            double calories;
            if (perdrePoidsRadioButton.isSelected()) {
                calories = bmr * 0.8; // 20% caloric deficit for weight loss
            } else if (priseMasseRadioButton.isSelected()) {
                calories = bmr * 1.2; // 20% caloric surplus for weight gain
            } else {
                calories = bmr; // Maintenance calories
            }

            // Adjust calories based on activity level
            if (legereActiviteRadioButton.isSelected()) {
                calories *= 1.2;
            } else if (modereeActiviteRadioButton.isSelected()) {
                calories *= 1.55;
            } else if (tresActifRadioButton.isSelected()) {
                calories *= 1.725;
            } else if (extraActifRadioButton.isSelected()) {
                calories *= 1.9;
            }

            // Display the result
            resultTextField.setText(String.format("%.2f", calories) + " Kcal");
        } catch (NumberFormatException e) {
            showAlert("Veuillez entrer des valeurs valides pour l'âge, la taille et le poids.");
        }
    }

    @FXML
    private void resetForm() {
        // Reset all input fields and result
        ageTextField.clear();
        tailleTextField.clear();
        poidsTextField.clear();
        genderToggleGroup.selectToggle(null);
        goalToggleGroup.selectToggle(null);
        activityLevelToggleGroup.selectToggle(null);
        resultTextField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
