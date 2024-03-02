package com.example.maxgain_store;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.io.File;
import java.net.URISyntaxException;

public class HelloController implements Initializable {
    @FXML private ScrollPane scroll;
    @FXML private Pane topPane;

    private final GridPane productGrid = new GridPane();

    private Pane createProduct(productModel produit)
    {
        Pane product = new Pane();
        product.setPrefWidth(400);
        product.setPrefHeight(400);

        VBox vLayout = new VBox();

        ImageView productImage = new ImageView();
        productImage.setImage(new Image(produit.getImage()));
        productImage.setFitHeight(300);
        productImage.setFitWidth(300);

        Label productName = new Label();
        productName.setText(produit.getNom());
        productName.setPrefWidth(400);
        productName.setAlignment(Pos.CENTER);


        Label productPrice = new Label();
        productPrice.setPrefWidth(400);
        productPrice.setAlignment(Pos.CENTER);
        productPrice.setText(String.valueOf(produit.getPrix()));

        Label productStock = new Label();
        productStock.setAlignment(Pos.CENTER);
        productStock.setText(String.valueOf(produit.getStock()));
        productStock.setPrefWidth(400);
        product.getChildren().add(vLayout);

        Button ajouterPanier = new Button();
        ajouterPanier.setText("Acheter");
        ajouterPanier.setAlignment(Pos.CENTER);

        vLayout.getChildren().add(productImage);
        vLayout.getChildren().add(productName);
        vLayout.getChildren().add(productPrice);
        vLayout.getChildren().add(productStock);
        vLayout.getChildren().add(ajouterPanier);
        vLayout.setAlignment(Pos.CENTER);

        return product;
    }

    public void fillGrid()
    {
        this.clearGrid();
        int j = 0;
        productModel[] products = this.getProducts();
        System.out.println(products.length);
        int x = 0;
        int y = 0;
        for (int i=0;i<products.length;i++)
        {
            productGrid.add(this.createProduct(products[i]), y, x);
            y = y + 1;
            if(y == 3)
            {
                x = x + 1;
                y = 0;
            }
        }
        scroll.setContent(productGrid);
    }

    public void addPane()
    {
        topPane.getChildren().clear();

        ImageView logo = new ImageView();
        logo.setFitWidth(70);
        logo.setFitHeight(70);
        logo.setX(20);
        logo.setImage(new Image("C:\\Users\\anene\\IdeaProjects\\PIDEV-3A21-Gym-MAXGAIN\\src\\main\\java\\com\\example\\maxgain_store\\assets\\logo.png"));

        Label store = new Label();
        store.setText("Store");
        store.setPrefHeight(77);
        store.setAlignment(Pos.CENTER);
        store.setPrefWidth(100);
        store.setLayoutX(750);
        store.setFont(Font.font(30));
        store.setStyle("-fx-text-fill: white;");

        ImageView panier = new ImageView();
        panier.setFitWidth(70);
        panier.setFitHeight(70);
        panier.setX(1510);
        panier.setImage(new Image("C:\\Users\\anene\\IdeaProjects\\PIDEV-3A21-Gym-MAXGAIN\\src\\main\\java\\com\\example\\maxgain_store\\assets\\cart.png"));

        topPane.getChildren().add(logo);
        topPane.getChildren().add(store);
        topPane.getChildren().add(panier);


    }

    public void clearGrid()
    {
        productGrid.getChildren().clear();
    }

    public int getProductGrid() {
        return productGrid.getColumnCount();
    }

    public productModel[] getProducts()
    {
        MySQLDatabaseConnection databaseConnection = MySQLDatabaseConnection.getInstance();
        productModel[] productModels = new productModel[databaseConnection.getRowCount("produit")];
        ResultSet resultSet = databaseConnection.executeQuery("SELECT * FROM produit;");
        int i=0;
        try {
            while (resultSet.next()) {
                productModel product = new productModel();
                product.setId(resultSet.getInt("id"));
                product.setPanier_id(resultSet.getInt("panier_id"));
                product.setNom(resultSet.getString("nom_p"));
                product.setpType(resultSet.getString("type_p"));
                product.setPrix(resultSet.getFloat("prix"));
                product.setStock(resultSet.getInt("stock"));
                product.setImage(resultSet.getString("image"));
                productModels[i] = product;
                i = i + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productModels;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.addPane();
        this.fillGrid();
    }



}