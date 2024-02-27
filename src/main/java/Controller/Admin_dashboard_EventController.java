/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entities.EvenementType;

import Services.EvenementTypeServices;

import java.io.IOException;
import java.net.URL;

import java.util.Optional;
import java.util.ResourceBundle;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class Admin_dashboard_EventController implements Initializable {

    @FXML
    private Button Event_button;

    @FXML
    private TextField IDTypeEvent_label;

    @FXML
    private Button Sponsor_button;

    @FXML
    private TextField TypeEvent_Label;

    @FXML
    private Button TypeEvent_addBtn;

    @FXML
    private Button TypeEvent_button;

    @FXML
    private Button TypeEvent_clearBtn;

    @FXML
    private Button TypeEvent_deleteBtn;

    @FXML
    private AnchorPane TypeEvent_form;

    @FXML
    private TextField TypeEvent_search;

    @FXML
    private TableView<EvenementType> TypeEvent_tableView;

    @FXML
    private Button TypeEvent_updateBtn;

    @FXML
    private TableColumn<EvenementType, String> col_IdTypeEvent;

    @FXML
    private TableColumn<EvenementType, String> col_TypeEvent;

    @FXML
    private AnchorPane main_form;

    /**
     * *********************Begin Type
     * Event************************************
     */
    /*Afficher la liste des EventType Dans le tableau*/
    public void EvenementTypeShowListData() {
        ObservableList<EvenementType> EvenementTypeList = (ObservableList<EvenementType>) new EvenementTypeServices().GetAll();

        col_IdTypeEvent.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_TypeEvent.setCellValueFactory(new PropertyValueFactory<>("type"));

        // System.out.println(EvenementTypeList);
        TypeEvent_tableView.setItems(EvenementTypeList);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EvenementTypeShowListData();
    }

    /*Afficher la liste des EventType Dans le tableau*/

 /*----------------------------------------------------------------------------------------------------*/
 /*Selectionner une ligne dans le tableau de Type Event*/
    @FXML
    public void EvenementTypeSelect() {
        EvenementType evenementType = TypeEvent_tableView.getSelectionModel().getSelectedItem();
        int num = TypeEvent_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        IDTypeEvent_label.setText(String.valueOf(evenementType.getId()));
        TypeEvent_Label.setText(String.valueOf(evenementType.getType()));

    }

    /*Selectionner une ligne dans le tableau de Type Event*/

 /*----------------------------------------------------------------------------------------------------*/
    /**
     * *******Ajout Event Type*************
     */
    @FXML
    public void EvenementTypeAdd() {

        try {
            IDTypeEvent_label.setText(null);
           Alert alert;
            if (TypeEvent_Label.getText().isEmpty() ) {
               alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields but not the id");
                alert.showAndWait();
            } else {

                boolean exist = new EvenementTypeServices().checkEventTypeExist(TypeEvent_Label.getText());

                if (exist) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Type: " + TypeEvent_Label.getText() + " was already exist!");
                    alert.showAndWait();
                } else {

                    EvenementType e = new EvenementType(TypeEvent_Label.getText());
                    boolean add = new EvenementTypeServices().ajouterEvenementType(e);


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    EvenementTypeShowListData();
                    EvenementTypeReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

   }

    /**
     * *******Ajout Event Type*************
     */

    /*-----------------------------------------------------------------------*/
    /**
     * *******Update Event Type*************
     */
    @FXML
    public void EvenementTypeUpdate() {

        try {
            Alert alert;
            if (TypeEvent_Label.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                boolean exist = new EvenementTypeServices().checkEventTypeExistById(IDTypeEvent_label.getText());

                if (!exist) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("please select the item from the table!");
                    alert.showAndWait();
                } else {

                    EvenementType e = new EvenementType(Integer.parseInt(IDTypeEvent_label.getText()), TypeEvent_Label.getText());
                    boolean add = new EvenementTypeServices().modifierEvenementType(e);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated!");
                    alert.showAndWait();

                    EvenementTypeShowListData();
                    EvenementTypeReset();
                  //  EvenementShowListData();
                }
            }

        } catch (NumberFormatException e) {
        }

    }

    /**
     * *******Update Event Type*************
     */
    /*-----------------------------------------------------------------------*/
    /**
     * *************effacer les text fields apres l'ajout ou modif ou
     * delete************************************
     */
    @FXML
    public void EvenementTypeReset() {
        TypeEvent_Label.setText(null);
        IDTypeEvent_label.setText(null);

    }

    /**
     * *************effacer les text fields apres
     * l'ajout************************************
     */

    /*-----------------------------------------------------------------------*/
    /**
     * **********Delete Type Event****************************
     */
    @FXML
    public void EvenementTypeDelete() {

        try {

            Alert alert;
            if (IDTypeEvent_label.getText().isEmpty()
                    || TypeEvent_Label.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Select From Table");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE This Type ID: " + IDTypeEvent_label.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    /**
                     * **************
                     */
                    boolean exist = new EvenementTypeServices().checkEventTypeExistById(IDTypeEvent_label.getText());
                    if (exist) {
                        EvenementType e = new EvenementType(Integer.parseInt(IDTypeEvent_label.getText()), "");
                        boolean delete = new EvenementTypeServices().supprimerEvenementType(e.getId());
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Deleted!");
                        alert.showAndWait();

                        EvenementTypeShowListData();
                        EvenementTypeReset();
                        //EvenementShowListData();

                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("This ID" + IDTypeEvent_label.getText() + "Does not exist");
                        alert.showAndWait();

                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void goEvent(ActionEvent event) throws IOException{
     // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventFx.fxml"));
       Parent root = loader.load();

        // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
        Scene scene = new Scene(root);

        // Obtenir la fenêtre (stage) à partir de l'événement
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Mettre à jour la scène de la fenêtre avec la nouvelle scène contenant la nouvelle interface FXML
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void goSponsor(ActionEvent event) throws IOException{
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SponsorFx.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
        Scene scene = new Scene(root);

        // Obtenir la fenêtre (stage) à partir de l'événement
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Mettre à jour la scène de la fenêtre avec la nouvelle scène contenant la nouvelle interface FXML
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void goSponsortype(ActionEvent event) throws IOException{
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SponsorType.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène avec la racine chargée depuis le fichier FXML
        Scene scene = new Scene(root);

        // Obtenir la fenêtre (stage) à partir de l'événement
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Mettre à jour la scène de la fenêtre avec la nouvelle scène contenant la nouvelle interface FXML
        stage.setScene(scene);
        stage.show();
    }



}
