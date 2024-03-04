package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.EmailSender;
import tn.esprit.services.PasswordResetService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PasswordResetController implements Initializable {
    @FXML
    private Hyperlink back;

    @FXML
    private Button btn_enter;

    @FXML
    private TextField codeField;


    @FXML
    private TextField emailField;


    @FXML
    private Button sendCodeButton;

    private PasswordResetService passwordResetService;

    public PasswordResetController() {
        passwordResetService = new PasswordResetService();
    }

    @FXML
    void handleSendVerificationCode(ActionEvent event) {
        String email = emailField.getText();
        if (email.isEmpty()) {
            showAlert("Please enter your email.");
            return;
        }

        // Initiate password reset process
        boolean result = passwordResetService.initiatePasswordReset(email);
        if (result) {
            // Redirect to verification code page
            redirectToVerificationCodePage();
        } else {
            showAlert("Failed to send verification code. Please try again.");
        }
    }

    private void redirectToVerificationCodePage() {
        try {
            Parent verificationCodeParent = FXMLLoader.load(getClass().getResource("/VerificationCode.fxml"));

            Scene verificationCodeScene = new Scene(verificationCodeParent);
            Stage stage = (Stage) sendCodeButton.getScene().getWindow(); // Get the current stage
            stage.setScene(verificationCodeScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password Reset");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passwordResetService = new PasswordResetService();
    }
    @FXML
    void handleVerifyCode(ActionEvent event) {
        String code = codeField.getText();
        String email = emailField.getText();
        if (email.isEmpty() || code.isEmpty()) {
            showAlert("Please enter both your email and the verification code.");
            return;
        }

        // Vérifier le code de vérification
        boolean isValid = passwordResetService.verifyVerificationCode(email, code);
        if (isValid) {
            // Rediriger vers la page de réinitialisation du mot de passe
            redirectToChangePasswordPage();
        } else {
            showAlert("Invalid verification code. Please try again.");
        }
    }

    private void redirectToChangePasswordPage() {
        // Redirection vers la page de réinitialisation du mot de passe
        try {
            Parent passwordResetParent = FXMLLoader.load(getClass().getResource("/ChangePassword.fxml"));
            Scene passwordResetScene = new Scene(passwordResetParent);
            Stage stage = (Stage) btn_enter.getScene().getWindow(); // Get the current stage
            stage.setScene(passwordResetScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    @FXML
//    private void handleEnterButton() {
//        // Récupérer l'adresse e-mail de l'utilisateur depuis le service PasswordResetService
//        String userEmail = passwordResetService.getUserEmail();
//
//        // Récupérer le code saisi par l'utilisateur depuis le champ de texte
//        String verificationCode = code.getText();
//
//        // Vérifier si le code saisi par l'utilisateur est correct pour l'adresse e-mail spécifiée
//        if (passwordResetService.isCodeCorrect(userEmail, verificationCode)) {
//            // Si le code est correct, rediriger vers la vue de changement de mot de passe
//            redirectToChangePasswordView();
//        } else {
//            // Sinon, afficher un message d'erreur
//            alert.errorMessage("The verification code is incorrect.");
//        }
//    }
//
//    private void redirectToChangePasswordView() {
//        closeCurrentWindow();
//        loadChangePasswordView();
//    }
//
//    private void closeCurrentWindow() {
//        Stage stage = (Stage) btn_enter.getScene().getWindow();
//        stage.close();
//    }
//
//    private void loadChangePasswordView() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChangePassword.fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}


}
