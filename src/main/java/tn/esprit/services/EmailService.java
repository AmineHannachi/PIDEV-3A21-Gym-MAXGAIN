package tn.esprit.services;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class EmailService {

    // Méthode pour envoyer un e-mail de confirmation sans pièce jointe
    public static void sendConfirmationEmail(String recipientEmail, String subject, String body) {
        final String username = "chihidorsaaf99@gmail.com";
        final String password = "orim mxmc vvke zqcj";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("E-mail envoyé avec succès.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode pour envoyer un e-mail de confirmation avec pièce jointe
    public static void sendConfirmationEmailWithAttachment(String recipientEmail, String subject, String body, String attachmentPath) {
        final String username = "chihidorsaf99@gmail.com"; // Remplacez par votre adresse e-mail
        final String password = "orim mxmc vvke zqcj"; // Remplacez par votre mot de passe

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            // Créer le contenu du message
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            // Créer la pièce jointe
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachmentPath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(new File(attachmentPath).getName());

            // Ajouter la pièce jointe au message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            // Envoyer l'e-mail
            Transport.send(message);

            System.out.println("E-mail avec pièce jointe envoyé avec succès.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
