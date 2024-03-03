package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import tn.esprit.entities.ReservationTerrain;
import tn.esprit.entities.Terrain;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ResourceBundle;
import tn.esprit.services.ReservationService;
import tn.esprit.services.TerrainService;
import tn.esprit.services.UserService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class DetaillTerrainController implements Initializable {


    @FXML
    private Label Description_terrain;

    @FXML
    private ImageView ImageView_terrain;

    @FXML
    private  Label Nom_terrain;

    @FXML
    private Label adresse_text;

    @FXML
    private  DatePicker date;

    @FXML
    private TextField email;

    @FXML
    private AnchorPane menu_form;
    @FXML
    private Text text;
    @FXML
    private GridPane menu_gridPane;

    @FXML
    private ScrollPane menu_scrollPane;
    private Image image;
    private Terrain terrain;
    @FXML
    private Button Button_Reservation;
    @FXML
    private  ComboBox<String> hourComboBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 9; i < 24; i++) {
            String formattedHour = String.format("%02d:00", i); // Formatage de l'heure
            hourComboBox.getItems().add(formattedHour);
        }

    }

    public void setdetaillTerrain(Terrain terrain) throws IOException {
        Nom_terrain.setText(terrain.getNom());
        adresse_text.setText(terrain.getAdresse());

        text.setText(String.valueOf(terrain.getDescription()));
        image = new Image(terrain.getImage());
        ImageView_terrain.setImage(image);
        Button_Reservation.setOnAction(this::reserverTerrain);
        Button_Reservation.setUserData(terrain);


    }
    private MainformController mainFormController;

    public void setController(MainformController mainFormController) {
        this.mainFormController = mainFormController;
    }


    public void reserverTerrain(ActionEvent actionEvent) {
        try{
            ReservationService connector = new ReservationService();
            String Email=email.getText();

            TerrainService terrain =new TerrainService();
            int idTerrain= terrain.obtenirIdTerrain(Nom_terrain.getText());

            UserService user=new UserService();
            String idUser=user.obtenirNomUser(1);
            Date selectedDate = Date.valueOf(date.getValue());

            String selectedHour = hourComboBox.getValue();
            String[] parts = selectedHour.split(":");

            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);

            // Création d'un objet Time
            Time selectedTime = new Time(hour, minute, 0);

            System.out.println(selectedTime);
            if (connector.checkTerrainExist(idTerrain,selectedDate,selectedTime)) {
                Alert existingNameAlert = new Alert(Alert.AlertType.ERROR);
                existingNameAlert.setTitle("Error");
                existingNameAlert.setHeaderText(null);
                existingNameAlert.setContentText("Terrain déjà indisponible.Modifier Date ou heure");
                existingNameAlert.showAndWait();
            }else {
                connector.add(new ReservationTerrain(selectedDate, selectedTime, idTerrain, 1));
                sendEmail(Email, Nom_terrain.getText(), date.getValue(), hourComboBox.getValue());
                // Afficher une confirmation à l'utilisateur
                Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                confirmationAlert.setTitle("Success");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Votre réservation a été ajoutée avec succès.");
                confirmationAlert.showAndWait();

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sendEmail(String recipientEmail, String terrain, LocalDate date, String heure) {
        // Configurer les propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Hôte SMTP (exemple avec Gmail)
        props.put("mail.smtp.port", "587"); // Port SMTP (587 pour Gmail)
        props.put("mail.smtp.auth", "true"); // Authentification SMTP
        props.put("mail.smtp.starttls.enable", "true"); // Activation du démarrage TLS

        // Créer une session SMTP avec authentification
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("oumayma.zou19@gmail.com", "psmj mcri bjif zvuo");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("oumayma.zou19@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Confirmation de la reservation"); // Définir le sujet du message
            String content = "Cher Client,\n\n";
            content += "Votre réservation a été confirmée avec succès. Voici les détails de votre réservation :\n\n";
            content += "Terrain: " + terrain + "\n";
            content += "Date: " + date + "\n";
            content += "Heure: " + heure + "\n\n";
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