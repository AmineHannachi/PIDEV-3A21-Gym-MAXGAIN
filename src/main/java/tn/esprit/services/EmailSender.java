package tn.esprit.services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

    public static void sendEmail(String recipientEmail, String subject, String body) {
        // Configurer les propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Hôte SMTP (exemple avec Gmail)
        props.put("mail.smtp.port", "587"); // Port SMTP (587 pour Gmail)
        props.put("mail.smtp.auth", "true"); // Authentification SMTP
        props.put("mail.smtp.starttls.enable", "true"); // Activation du démarrage TLS

        // Créer une session SMTP avec authentification
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("chihidorsaf99@gmail.com", "orim mxmc vvke zqcj");
            }
        });

        try {
            // Créer un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("chihidorsaf99@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            // Envoyer le message
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès à " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
