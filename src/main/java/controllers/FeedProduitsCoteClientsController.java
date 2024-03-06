package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import models.Offres;
import services.MyListener;
import services.OffresService;
import utils.MyDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class FeedProduitsCoteClientsController implements Initializable {

    @FXML
    private AnchorPane BigAnchorPane;

    @FXML
    private AnchorPane filter;

    @FXML
    private TextField SearchProductByAllNorml;

    @FXML
    private Label LabelUser;

    @FXML
    private Button btnPanier;

    @FXML
    private AnchorPane menu_formP;

    @FXML
    private ScrollPane prod_scrollpane;

    @FXML
    private GridPane menuP_gridpane;

    ObservableList<Offres> listOffres = FXCollections.observableArrayList();
    OffresService offresService = new OffresService();

    public void AfficherOFFres() throws SQLException {
        listOffres.addAll(GetListOFFRES());
        if (listOffres != null) {
            System.out.println("Recuperation reussie!");
        } else {
            System.out.println("Erreur de récupération");
        }

        int row = 0;
        int column = 0;
        menuP_gridpane.getChildren().clear();
        menuP_gridpane.getRowConstraints().clear();
        menuP_gridpane.getColumnConstraints().clear();

        for (int q = 0; q < listOffres.size(); q++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/controllers/cardoffer.fxml"));
                AnchorPane pane = loader.load();
                Cardoffer cardProduct = loader.getController();
                MyListener myListener = null;
                cardProduct.setData(listOffres.get(q), myListener);

                if (column == 3) {
                    column = 0;
                    row += 1;
                }
                menuP_gridpane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AfficherOFFres();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Offres> GetListOFFRES() {
        ObservableList<Offres> ListProduct = FXCollections.observableArrayList();
        MyDatabase db = new MyDatabase();
        Connection cnx = db.getConnection();
        String query = "SELECT * FROM offres";
        Statement st;
        ResultSet rs;
        try {
            st = cnx.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                int idProduit = rs.getInt("id");
                float prix = rs.getFloat("prix");
                int duree = rs.getInt("duree");
                String description = rs.getString("description");

                Offres offres = new Offres(idProduit, prix, duree, description);
                ListProduct.add(offres);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ListProduct;
    }
}
