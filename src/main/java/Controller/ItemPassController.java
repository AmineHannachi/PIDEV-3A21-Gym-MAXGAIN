/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Entities.Pass;
import Services.Evenementservice;
import Services.PassService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


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
        itemPassEventName.setText(new Evenementservice().recupererEventById(pass.getEvent_id()).getNom());
        itemPassCreatedAt.setText(pass.getCreatedAt());


    }

    
}
