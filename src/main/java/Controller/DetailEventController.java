/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entities.Evenement;
import Entities.Pass;
import Entities.Sponsor;
import Services.Evenementservice;
import Services.PassService;
import Services.SponsorService;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import javafx.scene.control.Alert;

import javafx.scene.layout.HBox;



/************/





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
        Evenement e = new Evenementservice().recupererEventById(id);
        EventNameLabel.setText(e.getNom());
       EventLieuLabel.setText(e.getLieu());
        EventBeginLabel.setText(e.getBeginat());
        EventFinishLabel.setText(e.getFinishat());
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

                fxmlLoader.setLocation(getClass().getResource("/front/itemSponsor.fxml"));

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
        int idEvent = getId();

        System.out.println("id event de pas"+idEvent);


                Pass pass = creerPass(idEvent);
        System.out.println(pass);
                new PassService().ajouterPass(pass);
                new Evenementservice().MAJpass(idEvent);
                System.out.println("pass ajoute");
                

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Texte saisi");
                alert.setContentText("Felicitation vous avez acheter un Pass");
                alert.showAndWait();



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //EventIdLabelId.setVisible(false);
    }
}
