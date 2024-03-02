package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private Button btn_submit;

    @FXML
    private TextField email_ver;

    @FXML
    private AnchorPane login_form;

    private final PasswordResetService passwordResetService = new PasswordResetService();
    private final AlertMessage alert = new AlertMessage(); // Instance de la classe utilitaire AlertMessage

    @FXML
    public void handlePasswordReset() {
        String userEmail = email_ver.getText();
        if (!isValidEmail(userEmail)) {
            alert.errorMessage("Invalid email address. Please enter a valid email address.");
            return;
        }

        if (passwordResetService.initiatePasswordReset(userEmail)) {
            alert.successMessage("A verification code has been sent to your email address.");
        } else {
            alert.errorMessage("An error occurred while resetting the password. Please try again.");
        }
    }

    private boolean isValidEmail(String email) {
        // Ajouter votre logique de validation d'e-mail ici
        return email != null && !email.isEmpty(); // Exemple simple de validation
    }

    @FXML
    public void redirectToLoginView() {
        loadScene("/Login.fxml", back);
    }

    @FXML
    private void handleVerificationCodeSending() {
        String userEmail = email_ver.getText();
        String verificationCode = passwordResetService.generateVerificationCode();
        sendVerificationCodeByEmail(userEmail, verificationCode);
    }

    @FXML
    private void redirectToVerificationCodeView() {
        loadScene("/VerificationCode.fxml", btn_submit);
    }

    private void sendVerificationCodeByEmail(String email, String verificationCode) {
        // Envoyer le code de vérification par e-mail à l'utilisateur
        String subject = "Verification Code for Password Reset";
        String body = "Your verification code is: " + verificationCode;

        try {
            EmailSender.sendEmail(email, subject, body);
            alert.successMessage("The verification email has been sent successfully.");
            redirectToVerificationCodeView();
        } catch (Exception e) {
            alert.errorMessage("Error: The verification email could not be sent.");
            e.printStackTrace();
        }
    }

    private void loadScene(String fxmlFile, Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_submit.setOnAction(event -> handleVerificationCodeSending());
    }
}
