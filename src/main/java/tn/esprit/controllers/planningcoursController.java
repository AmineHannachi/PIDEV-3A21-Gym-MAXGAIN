package tn.esprit.controllers;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.esprit.entities.ReservationCours;
import tn.esprit.entities.Salle;
import tn.esprit.entities.cours;
import tn.esprit.services.SalleService;
import tn.esprit.services.planningService;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Properties;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class planningcoursController {
    @FXML
    private DatePicker date;

    @FXML
    private ComboBox<String> hourComboBox;

    @FXML
    private TextField idUser;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private ComboBox<String> nomCours;

    @FXML
    private Label nom_sal;

    @FXML
    private Label prix_ter;

    @FXML
    private Button reserverC;


    @FXML
    private TextField email;


    public void initialize() {

        nomCours.setItems(FXCollections.observableArrayList("zomba", "salsa", "musculation"));

        // Initialisez les ComboBox pour les heures et les minutes avec les valeurs appropriées
        for (int i = 9; i < 24; i++) {
            String formattedHour = String.format("%02d:00", i); // Formatage de l'heure
            hourComboBox.getItems().add(formattedHour);
        }
//        ComboBox<String> timeSlotsComboBox = new ComboBox<>();

    }

    private MainformController mainFormController;


    public void setHomeController(MainformController mainFormController) {
        this.mainFormController = mainFormController;
    }

    public void setreserverSalle(Salle salle) {
        nom_sal.setText(salle.getNom());


    }


    @FXML
    void Suivant(ActionEvent event) {

        // Établir la connexion avec la base de données
        planningService connector = new planningService();
        String Email=email.getText();

        cours cours = new cours();
        String nom = nom_sal.getText();
        SalleService salle = new SalleService();
        int idSalle = salle.obtenirIdSalle(nom);

        String selectednomCours = nomCours.getValue();
        int idCours = connector.checkCoursExist(idSalle, selectednomCours);

        System.out.println(idCours);

        Date selectedDate = Date.valueOf(date.getValue());


        String selectedHour = hourComboBox.getValue();
        String[] parts = selectedHour.split(":");

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        // Création d'un objet Time
        Time selectedTime = new Time(hour, minute, 0);

        System.out.println(selectedTime);
        int ids = connector.checkCoursExist(idSalle, selectednomCours);


        if (connector.checkCExist(idCours, selectedDate, selectedTime)) {
            Alert existingNameAlert = new Alert(Alert.AlertType.ERROR);
            existingNameAlert.setTitle("Erreur");
            existingNameAlert.setHeaderText(null);
            existingNameAlert.setContentText("Le cours est complet. Veuillez choisir un autre horaire.");
            existingNameAlert.showAndWait();

        } else if (ids == 0) {


            Alert existingNameAlert = new Alert(Alert.AlertType.ERROR);
            existingNameAlert.setTitle("Erreur");
            existingNameAlert.setHeaderText(null);
            existingNameAlert.setContentText("Le cours est complet. Veuillez choisir un autre horaire.");
            existingNameAlert.showAndWait();

        } else if (ids != -1) {
            connector.add(new ReservationCours(ids, selectedDate, 1, selectedTime));
            sendEmail(Email, nom_sal.getText(), date.getValue(), hourComboBox.getValue());


//            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//
//
//            stage.close();
        }


    }



    public static void sendEmail(String recipientEmail, String text, LocalDate value, String hourComboBoxValue) {
        // Configurer les propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Hôte SMTP (exemple avec Gmail)
        props.put("mail.smtp.port", "587"); // Port SMTP (587 pour Gmail)
        props.put("mail.smtp.auth", "true"); // Authentification SMTP
        props.put("mail.smtp.starttls.enable", "true"); // Activation du démarrage TLS



        // Créer une session SMTP avec authentification
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // Utiliser des chaînes de caractères pour le nom d'utilisateur et le mot de passe
                return new PasswordAuthentication("oumayma.zou19@gmail.com", "psmj mcri bjif zvuo");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("oumayma.zou19@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Confirmation de réservation-MAX GAIN GYM"); // Définir le sujet du message
            String content = "Cher Client,\n\n";
            content += "Votre réservation a été confirmée avec succès. Voici les détails de votre réservation :\n\n";
            content += "Terrain: " + text + "\n";
            content += "Date: " + value + "\n";
            content += "Heure: " + hourComboBoxValue + "\n\n";
            content += "Merci de nous avoir choisis. Nous vous souhaitons un excellent moment sur notre terrain.\n\n";
            content += "Cordialement,\nVotre équipe de réservation";



            message.setText(content);


            // Envoyer le message
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès à " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}





