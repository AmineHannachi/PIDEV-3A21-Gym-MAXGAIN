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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Dali
 */
public class ItemSponsorBackController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private ImageView img;

    @FXML
    private VBox sponsorBox;
    
        @FXML
    private void click(MouseEvent mouseEvent) {

        myListener.onClickListener(sponsor);
                                                               

    }

    
    private Sponsor sponsor;
    private MyListner myListener;

    public void setData(Sponsor sponsor, MyListner myListener) {
        this.sponsor = sponsor;
        this.myListener = myListener;

        //this.myListener = myListener;
        nameLabel.setText(sponsor.getNom());
        emailLabel.setText(sponsor.getEmail());
        ImageView logo = new ImageView(new Image(new File(sponsor.getLogo()).toURI().toString()));
        logo.setFitWidth(100);
       // logo.setPreserveRatio(true);
        logo.setFitHeight(100);
        sponsorBox.getChildren().add(logo);
        
         /* Image image = new Image(sponsor.getLogo());
          

        img.setImage(image);*/ 
                //System.out.println("je suis dans itemcontroller setdata");

    }

}
