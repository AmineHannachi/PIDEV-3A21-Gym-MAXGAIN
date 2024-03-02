package tn.esprit.services;

import tn.esprit.utilis.DataSource;

import java.sql.*;
import java.util.UUID;



public class PasswordResetService extends EmailSender {

    private DataSource dataSource;

    public PasswordResetService() {
        dataSource = DataSource.getInstance();
    }

    public String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    private void saveVerificationCode(String email, String verification_code) {
        String sql = "INSERT INTO password_reset (email, verification_code, created_at) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getCnx();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, verification_code);
            pstmt.setTimestamp(3, getCurrentTimestamp());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void sendVerificationCodeByEmail(String email, String verificationCode) {
        // Envoyer le code de vérification par e-mail à l'utilisateur
        String subject = "Code de vérification pour réinitialisation de mot de passe";
        String body = "Votre code de vérification est : " + verificationCode;
        boolean emailSent = false;

        try {
            EmailSender.sendEmail(email, subject, body);
            emailSent = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (emailSent) {
            System.out.println("L'e-mail de vérification a été envoyé avec succès.");
        } else {
            System.out.println("Erreur : L'e-mail de vérification n'a pas pu être envoyé.");
        }
    }

    private Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

//    public boolean emailExists(String email) {
//        String userSql = "SELECT COUNT(*) FROM user WHERE email = ?";
//        String resetSql = "SELECT COUNT(*) FROM password_reset WHERE email = ?";
//
//        try (
//                Connection conn = dataSource.getCnx();
//                PreparedStatement userPstmt = conn.prepareStatement(userSql);
//                PreparedStatement resetPstmt = conn.prepareStatement(resetSql);
//        ) {
//
//            userPstmt.setString(1, email);
//            ResultSet userRs = userPstmt.executeQuery();
//            if (userRs.next() && userRs.getInt(1) > 0) {
//                resetPstmt.setString(1, email);
//                ResultSet resetRs = resetPstmt.executeQuery();
//                if (resetRs.next() && resetRs.getInt(1) > 0) {
//                    return true;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }

    public boolean initiatePasswordReset(String email) {
//
//        if (!emailExists(email)) {
//            return false;
//        }
        String verificationCode = generateVerificationCode();
        saveVerificationCode(email, verificationCode);
        sendVerificationCodeByEmail(email, verificationCode);

        return true; // Renvoyer true si l'opération s'est déroulée avec succès
    }
}
