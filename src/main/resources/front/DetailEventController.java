/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui.front;

import Models.Evenement;
import Models.Pass;
import Models.Sponsor;
import Services.EvenementService;
import Services.PassService;
import Services.SponsorService;
import Utils.getData;
import static Utils.getData.codeDeConfirmation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javax.imageio.ImageIO;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.context.Context;

/************/
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Properties;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;
import java.io.*;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * FXML Controller class
 *
 * @author Dali
 */
public class DetailEventController implements Initializable {


    private Label eventNameLabel;

    private Label eventLieuLabel;

    private Label eventBeginLabel;

    private Label eventFinishLabel;

    private Label EventDescriptionLabel;


    @FXML
    private Label EventIdLabelId;


    @FXML
    private GridPane gridSponsor;

    private HBox HOME;

    private int id;
    @FXML
    private VBox EventCard;
    @FXML
    private Label EventNameLabel;
    @FXML
    private Label EventLieuLabel;
    @FXML
    private Label EventBeginLabel;
    @FXML
    private Label EventFinishLabel;
    @FXML
    private Label EventDescriptionLabel1;
    @FXML
    private Label EventCapacityLabel;
    @FXML
    private Label EventPriceLabel;
    @FXML
    private ScrollPane scrollSponsor1;
    @FXML
    private Button ViewDetailEvent_Btn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        detailEvent(id);
        EventSponsorAfficher(id);
    }

    public void detailEvent(int id) {
        Evenement e = new EvenementService().recupererEventById(id);
        EventNameLabel.setText(e.getNom());
       EventLieuLabel.setText(e.getLieu());
        EventBeginLabel.setText(e.getBeginAt());
        EventFinishLabel.setText(e.getFinishAt());
        EventDescriptionLabel1.setText(e.getDescription());
        EventIdLabelId.setText(String.valueOf(e.getId()));

    }

    public void EventSponsorAfficher(int id) {
        ObservableList<Sponsor> sponsorList = new SponsorService().recupererSponsorsByEventId(id);
        System.out.println(sponsorList);

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < sponsorList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/Gui/front/itemSponsor.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ItemSponsorController itemController = fxmlLoader.getController();

                //System.out.println(myListener);
                itemController.setData(sponsorList.get(i));

                if (row == 1) {
                    row = 0;
                    column++;
                }

                gridSponsor.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                gridSponsor.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridSponsor.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridSponsor.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridSponsor.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridSponsor.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridSponsor.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Pass creerPass(int idEvent) {
        Pass pass = new Pass();
        pass.setEvent_id(idEvent);
        pass.setClient_id(1);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createdAt = currentDateTime.format(formatter);
        pass.setCreatedAt(createdAt);
        System.out.println("pass created");
        return pass;

    }

    public void AjouterPass() {
        int idEvent = Integer.valueOf(EventIdLabelId.getText());

        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Générer un nombre aléatoire entre 100000 et 999999
        getData.codeDeConfirmation = Integer.toString(code); // Convertir le nombre en une chaîne de caractères


        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Saisir le code de confirmation");
        dialog.setHeaderText("Entrez un texte :");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        Optional<String> result = dialog.showAndWait();

        // Vérifier si l'utilisateur a cliqué sur le bouton "OK"
        if (result.isPresent()) {
            String saisieUser = result.get();
            if (saisieUser.equals(getData.codeDeConfirmation)) {
                getData.codeDeConfirmation = "";
                Pass pass = creerPass(idEvent);
                new PassService().ajouterPass(pass);
                new EvenementService().MAJpass(idEvent);
                System.out.println("pass ajoute");

                sendEmailNotif(idEvent);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Texte saisi");
                alert.setContentText("Felicitation vous avez acheter un Pass");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("your code is incorrect");
                alert.showAndWait();
            }

            /* // Afficher le texte saisi par l'utilisateur
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Texte saisi");
            alert.setContentText("Vous avez saisi : " + texte);
            alert.showAndWait();*/
        }

    }

    private static void sendEmail(String code) {

        String msg = "veuillez confirmer votre achat du pass avec ce code " + code + " \n veuillez ne pas depasser 30 secondes";

        // Create all the needed properties
        Properties connectionProperties = new Properties();
        // SMTP host
        connectionProperties.put("mail.smtp.host", "smtp.gmail.com");//
        // Is authentication enabled
        connectionProperties.put("mail.smtp.auth", "true");//
        // Is TLS enabled
        connectionProperties.put("mail.smtp.starttls.enable", "true");//
        // SSL Port
        //connectionProperties.put("mail.smtp.socketFactory.port", "465");
        // SSL Socket Factory class
        //connectionProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // SMTP port, the same as SSL port :)
        connectionProperties.put("mail.smtp.port", "587");

        System.out.print("Creating the session...");

        // Create the session
        Session session = Session.getDefaultInstance(connectionProperties,
                new javax.mail.Authenticator() {	// Define the authenticator
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("mohamedali.marrakchi@esprit.tn", "201JMT3116");
            }
        });

        System.out.println("done!");

        // Create and send the message
        try {
            // Create the message
            Message message = new MimeMessage(session);
            // Set sender
            message.setFrom(new InternetAddress("mohamedali.marrakchi@esprit.tn"));
            // Set the recipients
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("hammaali3211@gmail.com"));
            // Set message subject
            message.setSubject("confirmation d'achat d'un Pass");
            // Set message text
            message.setText(msg);

            System.out.print("Sending message...");
            // Send the message
            Transport.send(message);

            System.out.println("done!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void goHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/front/home.fxml"));
            Parent root = loader.load();

            Scene viewHomeScene = new Scene(root);
            Stage stage = (Stage) HOME.getScene().getWindow();
            stage.setScene(viewHomeScene);
            //System.out.println(controller.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static String saveImageToFile(ImageView image, String fileName) {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image.getImage(), null);
            File file = new File(fileName);
            ImageIO.write(bufferedImage, "png", file);
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ImageView QrGenerator(String content) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        int width = 300;
        int height = 300;
        String fileType = "png";

        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();

            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            System.out.println("Success...");

        } catch (WriterException ex) {
            Logger.getLogger(DetailEventController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ImageView qrView = new ImageView();
        qrView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

        return qrView;

    }

    private static void sendEmailNotif(int idE) {

        //String msg = "veuillez confirmer votre achat du pass avec ce code " + code + " \n veuillez ne pas depasser 30 secondes";
        // Create all the needed properties
        Properties connectionProperties = new Properties();
        // SMTP host
        connectionProperties.put("mail.smtp.host", "smtp.gmail.com");//
        // Is authentication enabled
        connectionProperties.put("mail.smtp.auth", "true");//
        // Is TLS enabled
        connectionProperties.put("mail.smtp.starttls.enable", "true");//
        // SSL Port
        //connectionProperties.put("mail.smtp.socketFactory.port", "465");
        // SSL Socket Factory class
        //connectionProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // SMTP port, the same as SSL port :)
        connectionProperties.put("mail.smtp.port", "587");

        System.out.print("Creating the session...");

        // Create the session
        Session session = Session.getDefaultInstance(connectionProperties,
                new javax.mail.Authenticator() {	// Define the authenticator
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("mohamedali.marrakchi@esprit.tn", "201JMT3116");
            }
        });

        System.out.println("done!");

        // Create and send the message
        try {
            // Create the message
            Message message = new MimeMessage(session);
            // Set sender
            message.setFrom(new InternetAddress("mohamedali.marrakchi@esprit.tn"));
            // Set the recipients
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("hammaali3211@gmail.com"));
            // Set message subject
            message.setSubject("Detail Pass");

            // Lire le contenu HTML à partir du fichier
//            File file = new File(htmlFilePath);
//            FileReader reader = new FileReader(file);
//            char[] chars = new char[(int) file.length()];
//            reader.read(chars);
//            String htmlBody = new String(chars);
//            reader.close();
//            message.setContent(htmlBody, "text/html");
            /**
             * *******template*****************************
             */
            FileTemplateResolver resolver = new FileTemplateResolver();
            resolver.setTemplateMode("HTML");
            resolver.setPrefix("src/Gui/front/");
            resolver.setSuffix(".html");

            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(resolver);

// Définir les variables à utiliser dans le template
            Map<String, Object> variables = new HashMap<>();
            variables.put("name", "Zeddini Mohamed Dhia");
            variables.put("message", "Hello, world!");

            //String idE= EventIdLabelId.getText();
            Evenement ev = new EvenementService().recupererEventById(idE);
            String QrContent="Client Zeddini Dhia "+"Event Name "+ev.getNom()+" allowed" ;
            String fileNameQr="ZeddiniDhia"+ev.getNom()+".png";

            System.out.println(QrContent);
            ImageView qrImage = QrGenerator(QrContent);//QrContent
            String qrCodePath = saveImageToFile(qrImage, fileNameQr);
            System.out.println(qrCodePath);
            variables.put("qrCodeWidth", "300");
            variables.put("qrCodeHeight", "300");
            variables.put("qrCode", "cid:qrCode");

            /**
             * *************
             */
            // Créer un MimeBodyPart pour l'image du QR code
            MimeBodyPart qrCodePart = new MimeBodyPart();
            qrCodePart.attachFile(qrCodePath);
            qrCodePart.setContentID("<qrCode>");
            qrCodePart.setDisposition(MimeBodyPart.INLINE);

// Créer un MimeMultipart pour contenir le texte du mail et l'image
//MimeMultipart multipart = new MimeMultipart();
//multipart.addBodyPart(textPart);
//multipart.addBodyPart(qrCodePart);
            /**
             * ************
             */
// Générer le contenu HTML à partir du template et des variables
            Context context = new Context(Locale.getDefault(), variables);
            String htmlBody = templateEngine.process("templatePass", context);

            System.out.println("2");

            // Créer un MimeMultipart pour contenir le contenu HTML et l'image
            MimeMultipart multipart = new MimeMultipart("related");

// Ajouter le contenu HTML au multipart
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html");
            multipart.addBodyPart(htmlPart);

            // Ajouter l'image du QR code au multipart
            multipart.addBodyPart(qrCodePart);

// Ajouter le multipart au message
            message.setContent(multipart);

            System.out.println("3");

            /**
             * ***********template*************************
             */
            // Set message text
            // message.setText(msg);
            System.out.print("Sending message...");
            // Send the message
            Transport.send(message);

            System.out.println("done!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        EventIdLabelId.setVisible(false);
        //sendEmailNotif(43);
    }

}
