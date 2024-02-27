package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Register.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("MaxGain");
        stage.setScene(scene);
        stage.show();
    }
    public static  void main(String[]args){
        launch(args);

    }
}















