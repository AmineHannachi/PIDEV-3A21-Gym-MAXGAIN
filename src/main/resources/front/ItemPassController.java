/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui.front;


import Models.Pass;
import Services.EvenementService;
import Services.PassService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Dali
 */
public class ItemPassController  {

    /**
     * Initializes the controller class.
     */
    
        @FXML
    private Label itemPassClientName;

    @FXML
    private Label itemPassEventName;

    @FXML
    private Label itemPassCreatedAt;
    
    private Pass pass;


    public void setData(Pass pass) {
        this.pass = pass;
     
        itemPassClientName.setText(new PassService().recupererNameClientById(pass.getClient_id()));
        itemPassEventName.setText(new EvenementService().recupererEventById(pass.getEvent_id()).getNom());
        itemPassCreatedAt.setText(pass.getCreatedAt());


    }

    
}
