/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Interfaces.MyListner;
import Entities.Sponsor;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class ItemSponsorController  {

    @FXML
    private ImageView imgSponsor;
    
    private Sponsor sponsor;
    
    @FXML
    private AnchorPane AnchorSponsor;
    
 public void setData(Sponsor sponsor) {
        this.sponsor = sponsor;
        

       
        ImageView logo = new ImageView(new Image(new File(sponsor.getLogo()).toURI().toString()));
        logo.setFitWidth(100);
       // logo.setPreserveRatio(true);
        logo.setFitHeight(100);
          AnchorSponsor.getChildren().add(logo);
        
         /* Image image = new Image(sponsor.getLogo());
          

        img.setImage(image);*/ 
                //System.out.println("je suis dans itemcontroller setdata");

    } 
    
}
