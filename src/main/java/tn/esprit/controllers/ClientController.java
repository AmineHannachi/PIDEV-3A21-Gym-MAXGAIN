package tn.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ClientController {
    @FXML
    private Button addBtn;

    @FXML
    private TableView<?> addClient_tableView;

    @FXML
    private TextField addClientsearch;

    @FXML
    private AnchorPane ap;

    @FXML
    private DatePicker birthdate;

    @FXML
    private AnchorPane bp;

    @FXML
    private JFXButton btn_client;

    @FXML
    private JFXButton btn_coach;

    @FXML
    private JFXButton btn_event;

    @FXML
    private JFXButton btn_home;

    @FXML
    private JFXButton btn_offre;

    @FXML
    private JFXButton btn_salle;

    @FXML
    private JFXButton btn_terr;

    @FXML
    private TableColumn<?, ?> colBirthdate;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableColumn<?, ?> colusername;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField email;

    @FXML
    private JFXRadioButton female;

    @FXML
    private AnchorPane home_form;

    @FXML
    private ImageView home_form_image;

    @FXML
    private JFXRadioButton male;

    @FXML
    private TextField phone;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField username;

    @FXML
    void Client(ActionEvent event) {
    }
}

