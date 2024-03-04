package tn.esprit.services;

import tn.esprit.utilis.DataSource;

import java.util.Random;

public class PasswordResetService {

    private DataSource dataSource;

    public PasswordResetService() {
        dataSource = DataSource.getInstance();
    }
    public boolean isVerificationCodeCorrect(String email, String code) {
        return dataSource.isVerificationCodeCorrect(email, code);
    }
    public boolean initiatePasswordReset(String email) {
        // Générer un code de vérification aléatoire
        String verificationCode = generateVerificationCode();

        // Enregistrer le code de vérification en base de données
        dataSource.saveVerificationCode(email, verificationCode);

        // Envoyer le code de vérification par email
        EmailSender.sendEmail(email, "Code de vérification", "Votre code de vérification est : " + verificationCode);

        return true;
    }


    public boolean verifyVerificationCode(String email, String code) {
        // Récupérer le code de vérification enregistré en base de données
        String savedCode = dataSource.getVerificationCode(email);

        // Vérifier si les codes correspondent
        return savedCode != null && savedCode.equals(code);
    }


    public boolean resetPassword(String email, String newPassword) {
        // Mettre à jour le mot de passe de l'utilisateur en base de données
        dataSource.updatePassword(email, newPassword);
        return true; // Pour l'instant, retourne toujours true, mais peut être modifié pour gérer les erreurs éventuelles
    }

    private String generateVerificationCode() {
        // Générer un code de vérification aléatoire de 6 chiffres
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
