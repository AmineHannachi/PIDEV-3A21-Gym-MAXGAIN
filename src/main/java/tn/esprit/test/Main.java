package tn.esprit.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import tn.esprit.services.EmailSender;

import tn.esprit.services.UserService;
import tn.esprit.utilis.DataSource;


import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException {
        DataSource ds1 = DataSource.getInstance();

        UserService ps = new UserService();
//        PasswordResetService passwordResetService = new PasswordResetService();
      // Adresse e-mail de l'utilisateur
//       String email = "chihidorsaf2001@gmail.com";
////        // Lancer la réinitialisation du mot de passe et vérifier si l'e-mail a été envoyé avec succès
//      boolean emailSent = passwordResetService.initiatePasswordReset(email);
////
//      if (emailSent) {
//          System.out.println("L'e-mail de vérification a été envoyé avec succès.");
//       } else {
//            System.out.println("Erreur : L'e-mail de vérification n'a pas pu être envoyé.");
//        }
  }
}