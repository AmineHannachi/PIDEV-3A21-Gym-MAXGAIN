package Controller;

import Entities.Evenement;
import Entities.SponsorType;
import Services.Evenementservice;
import Services.SponsorTypeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TypeSponsor implements Initializable {
    @FXML
    private TextField Event_EventCapacite;

    @FXML
    private TextField Event_EventDescription;

    @FXML
    private TextField Event_EventId;

    @FXML
    private TextField Event_EventLieu;

    @FXML
    private TextField Event_EventNom;

    @FXML
    private TextField Event_EventPrix;

    @FXML
    private ComboBox<?> Event_EventType;

    @FXML
    private Button Event_addBtn;

    @FXML
    private Button Event_button;

    @FXML
    private Button Event_clearBtn;

    @FXML
    private TableColumn<?, ?> Event_col_BeginAt;

    @FXML
    private TableColumn<?, ?> Event_col_Capacite;

    @FXML
    private TableColumn<?, ?> Event_col_Description;

    @FXML
    private TableColumn<?, ?> Event_col_EventId;

    @FXML
    private TableColumn<?, ?> Event_col_FinishAt;

    @FXML
    private TableColumn<?, ?> Event_col_Lieu;

    @FXML
    private TableColumn<?, ?> Event_col_Nom;

    @FXML
    private TableColumn<?, ?> Event_col_Prix;

    @FXML
    private TableColumn<?, ?> Event_col_Type;

    @FXML
    private Button Event_deleteBtn;

    @FXML
    private AnchorPane Event_form;

    @FXML
    private TextField Event_search;

    @FXML
    private TableView<?> Event_tableView;

    @FXML
    private Button Event_updateBtn;

    @FXML
    private Button OpenSystem;

    @FXML
    private ComboBox<String> Sponsor_Evenement;

    @FXML
    private TextField Sponsor_SponsorId;

    @FXML
    private TextField Sponsor_SponsorMail;

    @FXML
    private TextField Sponsor_SponsorName;

    @FXML
    private ComboBox<String> Sponsor_SponsorType;

    @FXML
    private Button Sponsor_addBtn;

    @FXML
    private Button Sponsor_button;

    @FXML
    private Button Sponsor_deleteBtn;

    @FXML
    private AnchorPane Sponsor_form;

    @FXML
    private ImageView Sponsor_image;

    @FXML
    private Button Sponsor_importBtn;

    @FXML
    private Button Sponsor_updateBtn;

    @FXML
    private TextField TypeEvent_TypeEvent;

    @FXML
    private TextField TypeEvent_TypeEventId;

    @FXML
    private Button TypeEvent_addBtn;

    @FXML
    private Button TypeEvent_button;

    @FXML
    private Button TypeEvent_clearBtn;

    @FXML
    private TableColumn<?, ?> TypeEvent_col_TypeEvent;

    @FXML
    private TableColumn<?, ?> TypeEvent_col_TypeEventId;

    @FXML
    private Button TypeEvent_deleteBtn;

    @FXML
    private AnchorPane TypeEvent_form;

    @FXML
    private TextField TypeEvent_search;

    @FXML
    private TableView<?> TypeEvent_tableView;

    @FXML
    private Button TypeEvent_updateBtn;

    @FXML
    private TextField TypeSponsor_Type;

    @FXML
    private TextField TypeSponsor_TypeID;

    @FXML
    private Button TypeSponsor_addBtn;

    @FXML
    private Button TypeSponsor_button;

    @FXML
    private Button TypeSponsor_clearBtn;

    @FXML
    private TableColumn<SponsorType, String> TypeSponsor_col_TypeSponsor;

    @FXML
    private TableColumn<SponsorType, String> TypeSponsor_col_TypeSponsorId;

    @FXML
    private Button TypeSponsor_deleteBtn;

    @FXML
    private AnchorPane TypeSponsor_form;

    @FXML
    private TextField TypeSponsor_search;

    @FXML
    private TableView<SponsorType> TypeSponsor_tableView;

    @FXML
    private Button TypeSponsor_updateBtn;

    @FXML
    private ImageView addEmployee_image1;

    @FXML
    private Button addEmployee_importBtn1;

    @FXML
    private AnchorPane main_form;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane sponsorGrid;
    /**
     * *********************Begin Type
     * Sponsor************************************
     */

    /*Afficher la liste des EventType Dans le tableau*/
    public void SponsorTypeShowListData() {
        ObservableList<SponsorType> sponsorTypeList = new SponsorTypeService().GetAll();

        TypeSponsor_col_TypeSponsorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TypeSponsor_col_TypeSponsor.setCellValueFactory(new PropertyValueFactory<>("type"));

        //System.out.println(sponsorTypeList);
        TypeSponsor_tableView.setItems(sponsorTypeList);

    }

    /*Afficher la liste des EventType Dans le tableau*/

    /*----------------------------------------------------------------------------------------------------*/
    /*Selectionner une ligne dans le tableau de Type Sponsor*/
    @FXML
    public void SponsorTypeSelect() {
        SponsorType sponsorType = (SponsorType) TypeSponsor_tableView.getSelectionModel().getSelectedItem();
        int num = TypeSponsor_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        TypeSponsor_TypeID.setText(String.valueOf(sponsorType.getId()));
        TypeSponsor_Type.setText(String.valueOf(sponsorType.getType()));

    }

    /*Selectionner une ligne dans le tableau de Type Event*/

    /*----------------------------------------------------------------------------------------------------*/
    /**
     * *******Ajout Sponsor Type*************
     */
    @FXML
    public void SponsorTypeAdd() {

        try {
            Alert alert;
            if (TypeSponsor_Type.getText().isEmpty() || !(TypeSponsor_TypeID.getText().isEmpty())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields but not the id");
                alert.showAndWait();
            } else {

                boolean exist = new SponsorTypeService().checkSponsorTypeExist(TypeSponsor_Type.getText());

                if (exist) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Type: " + TypeSponsor_Type.getText() + " was already exist!");
                    alert.showAndWait();
                } else {

                    SponsorType e = new SponsorType(TypeSponsor_Type.getText());
                    boolean add = new SponsorTypeService().ajouterSponsorType(e);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    SponsorTypeShowListData();
                    SponsorTypeReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * *******Ajout Sponsor Type*************
     */

    /*-----------------------------------------------------------------------*/
    /**
     * *******Update Sponsor Type*************
     */
    @FXML
    public void SponsorTypeUpdate() {

        try {
            Alert alert;
            if (TypeSponsor_Type.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                boolean exist = new SponsorTypeService().checkSponsorTypeExistById(TypeSponsor_TypeID.getText());

                if (!exist) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID: " + TypeSponsor_TypeID.getText() + " does not exist!");
                    alert.showAndWait();
                } else {

                    SponsorType e = new SponsorType(Integer.parseInt(TypeSponsor_TypeID.getText()), TypeSponsor_Type.getText());
                    boolean add = new SponsorTypeService().modifierSponsorType(e);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated!");
                    alert.showAndWait();

                    SponsorTypeShowListData();
                    SponsorTypeReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * *******Update Sponsor Type*************
     */
    /*-----------------------------------------------------------------------*/
    /**
     * *************effacer les text fields apres l'ajout ou modif ou
     * delete************************************
     */
    @FXML
    public void SponsorTypeReset() {
        TypeSponsor_TypeID.setText("");
        TypeSponsor_Type.setText("");

    }

    /**
     * *************effacer les text fields apres
     * l'ajout************************************
     */

    /*-----------------------------------------------------------------------*/
    /**
     * **********Delete Type Sponsor****************************
     */
    @FXML
    public void SponsorTypeDelete() {

        try {

            Alert alert;
            if (TypeSponsor_TypeID.getText().isEmpty()
                    || TypeSponsor_Type.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE This SPOSNSOR ID: " + TypeSponsor_TypeID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (((Optional<?>) option).get().equals(ButtonType.OK)) {
                    /**
                     * **************
                     */
                    boolean exist = new SponsorTypeService().checkSponsorTypeExistById(TypeSponsor_TypeID.getText());
                    if (exist) {
                        SponsorType e = new SponsorType(Integer.parseInt(TypeSponsor_TypeID.getText()), "");
                        boolean delete = new SponsorTypeService().supprimerSponsorType(e);
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Deleted!");
                        alert.showAndWait();

                        SponsorTypeShowListData();
                        SponsorTypeReset();

                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("This ID" + TypeEvent_TypeEventId.getText() + "Does not exist");
                        alert.showAndWait();

                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void goEvent(ActionEvent event) throws IOException {
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
    public void goTypeEvent(ActionEvent event) throws IOException{
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin_dashboard_Event.fxml"));
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SponsorTypeShowListData();


    }


}
