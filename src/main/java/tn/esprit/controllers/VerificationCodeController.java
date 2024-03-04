//package tn.esprit.controllers;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import tn.esprit.services.PasswordResetService;
//
//import java.io.IOException;
//
//public class VerificationCodeController {
//
//    @FXML
//    private Button btn_enter;
//
//    @FXML
//    private TextField code;
//
//    private final PasswordResetService passwordResetService = new PasswordResetService();
//    private final AlertMessage alert = new AlertMessage(); // Instance de la classe utilitaire AlertMessage
//
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
