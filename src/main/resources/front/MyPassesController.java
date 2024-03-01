/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui.front;



import Models.Pass;
import Services.PassService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dali
 */
public class MyPassesController implements Initializable {

    @FXML
    private HBox HOME;

    @FXML
    private ScrollPane scrollPass;

    @FXML
    private GridPane gridPass;
    
    @FXML
    public void EventAfficher() {
        ObservableList<Pass> MyPassesList = new PassService().GetMyPasses(1);

        
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < MyPassesList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                
                fxmlLoader.setLocation(getClass().getResource("/Gui/front/itemPass.fxml"));
                
                AnchorPane anchorPane = fxmlLoader.load();
                
                ItemPassController itemController = fxmlLoader.getController();

                //System.out.println(myListener);
                itemController.setData(MyPassesList.get(i));
                
                if (column == 3) {
                    column = 0;
                    row++;
                }
                
                gridPass.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                gridPass.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPass.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPass.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridPass.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPass.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPass.setMaxHeight(Region.USE_PREF_SIZE);
                
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @FXML
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EventAfficher() ;
    }

}
