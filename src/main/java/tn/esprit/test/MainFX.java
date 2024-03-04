package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.services.JWTUtil;
import tn.esprit.services.SessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static javafx.application.Application.launch;

public class MainFX extends Application {
    private double x, y;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));

        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("MaxGain");
        stage.setScene(scene);
        stage.show();
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

        });

//        String username = "user123";
//        String password = "password123";
//        String jwt = JWTUtil.generateJWT(username);
//
//        // Stocker localement le JWT
//        SessionManager.setJWT(jwt);
//
//        // Construire l'URL du point de terminaison API
//        String apiUrl = "https://example.com/api/resource";
//
//        try {
//            // Ouvrir la connexion HTTP
//            URL url = new URL(apiUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            // Définir la méthode de la requête et l'en-tête d'autorisation avec le JWT
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Authorization", "Bearer " + SessionManager.getJWT());
//
//            // Lire la réponse
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            // Afficher la réponse
//            System.out.println("Response: " + response.toString());
//
//            // Fermer la connexion
//            conn.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
