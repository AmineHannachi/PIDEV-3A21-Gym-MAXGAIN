/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui.front;

import Gui.ItemSponsorBackController;
import Interfaces.MyListner;
import Interfaces.MyListnerEvent;
import Models.Evenement;
import Models.Sponsor;
import Models.User;
import Services.EvenementService;
import Services.PassService;
import Services.SponsorService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dali
 */
public class HomeController implements Initializable {
    
    
    @FXML
    private HBox Passes;
    
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
    private Label EventDescriptionLabel;
    
    @FXML
    private Label EventCapacityLabel;
    
    @FXML
    private Label EventPriceLabel;
    
    @FXML
    private Button ViewDetailEvent_Btn;
    
    @FXML
    private ScrollPane scrollEvent;
    
    @FXML
    private Label EventIdLabel;
    
    @FXML
    private GridPane gridEvent;
    
    private MyListnerEvent myListenerEvent;
    
    private void setChosenEvent(Evenement event) {
        
        EventNameLabel.setText(event.getNom());
        EventLieuLabel.setText(event.getLieu());
        EventBeginLabel.setText(event.getBeginAt());
        EventFinishLabel.setText(event.getFinishAt());
        EventDescriptionLabel.setText(event.getDescription());
        EventCapacityLabel.setText(String.valueOf(event.getCapacite()));
        EventPriceLabel.setText(String.valueOf(event.getPrix()));
        EventIdLabel.setText(String.valueOf(event.getId()));
        /*image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");*/
    }
    
    @FXML
    public void EventAfficher() {
        ObservableList<Evenement> evenementList = new EvenementService().GetAll();
        System.out.println(evenementList);
        if (evenementList.size() > 0) {
            //setChosenSponsor(sponsorList.get(0));
            myListenerEvent = (Evenement event) -> {
                setChosenEvent(event);
            };
        }
        
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < evenementList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                
                fxmlLoader.setLocation(getClass().getResource("/Gui/front/itemEvent.fxml"));
                
                AnchorPane anchorPane = fxmlLoader.load();
                
                ItemEventController itemController = fxmlLoader.getController();

                //System.out.println(myListener);
                itemController.setData(evenementList.get(i), myListenerEvent);
                
                if (column == 4) {
                    column = 0;
                    row++;
                }
                
                gridEvent.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                gridEvent.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridEvent.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridEvent.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridEvent.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridEvent.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridEvent.setMaxHeight(Region.USE_PREF_SIZE);
                
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    public void onViewDetailsButton() {
            try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/front/detailEvent.fxml"));
        Parent root = loader.load();

        // Accéder au contrôleur de la nouvelle interface
        DetailEventController controller = loader.getController();

        // Passer la valeur de la label "id" comme paramètre
        String id = EventIdLabel.getText();
        controller.setId(Integer.valueOf(id));



        Scene viewDetailEventScene = new Scene(root);
        Stage stage = (Stage) ViewDetailEvent_Btn.getScene().getWindow();
        stage.setScene(viewDetailEventScene);
                        //System.out.println(controller.getId());

    } catch (IOException e) {
        e.printStackTrace();
    }
      
    }
    
        @FXML
    public void goPasses() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/front/MyPasses.fxml"));
            Parent root = loader.load();

            Scene viewHomeScene = new Scene(root);
            Stage stage = (Stage) Passes.getScene().getWindow();
            stage.setScene(viewHomeScene);
            //System.out.println(controller.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        

                
        EventAfficher();

       
       
        
    }
    
}
