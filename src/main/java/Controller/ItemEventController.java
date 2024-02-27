/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entities.Evenement;
import Interfaces.MyListnerEvent;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dali
 */
public class ItemEventController /*implements Initializable*/ {

    /**
     * Initializes the controller class.
     */
    /*@Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }*/
    @FXML
    private Label itemEventName;

    @FXML
    private Label itemEventLieu;

    @FXML
    private Label itemEventBegin;

    @FXML
    private Label itemEventFinish;

    @FXML
    private Label itemEventId;

    @FXML
    private void click(MouseEvent mouseEvent) {
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/front/detailEvent.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene =new Scene(root);
     
        // Accéder au contrôleur de la nouvelle interface
        DetailEventController controller = loader.getController();

        // Passer la valeur de la label "id" comme paramètre
        String id = itemEventId.getText();
              System.out.println(id);
        controller.setId(Integer.valueOf(id));

   stage.setScene(scene);
stage.show();
                        //System.out.println(controller.getId());

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    private Evenement event;
    private MyListnerEvent myListenerEvent;

    public void setData(Evenement event, MyListnerEvent myListenerEvent) {
        this.event = event;
        this.myListenerEvent = myListenerEvent;
        itemEventName.setText(event.getNom());
        itemEventLieu.setText(event.getLieu());
        itemEventBegin.setText(event.getBeginat());
        itemEventFinish.setText(event.getFinishat());
        itemEventId.setText(""+event.getId());

    }

}
