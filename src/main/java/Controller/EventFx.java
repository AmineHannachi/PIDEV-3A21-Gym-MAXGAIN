package Controller;

import Entities.Evenement;
import Entities.EvenementType;
import Services.EvenementTypeServices;
import Services.Evenementservice;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class EventFx implements Initializable {
    @FXML
    private TextField Event_EventCapacite;
    @FXML
    private DatePicker Event_EventBeginDate;
    @FXML
    private DatePicker Event_EventFinishDate;

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
    private ComboBox<String> Event_EventType;

    @FXML
    private Button Event_addBtn;

    @FXML
    private Button Event_button;

    @FXML
    private Button Event_clearBtn;

    @FXML
    private TableColumn<Evenement, String> Event_col_BeginAt;

    @FXML
    private TableColumn<Evenement, String> Event_col_Capacite;

    @FXML
    private TableColumn<Evenement, String> Event_col_Description;

    @FXML
    private TableColumn<Evenement, String> Event_col_EventId;

    @FXML
    private TableColumn<Evenement, String> Event_col_FinishAt;

    @FXML
    private TableColumn<Evenement, String> Event_col_Lieu;

    @FXML
    private TableColumn<Evenement, String> Event_col_Nom;

    @FXML
    private TableColumn<Evenement, String> Event_col_Prix;

    @FXML
    private TableColumn<Evenement, String> Event_col_Type;

    @FXML
    private Button Event_deleteBtn;

    @FXML
    private AnchorPane Event_form;

    @FXML
    private TextField Event_search;

    @FXML
    private TableView<Evenement> Event_tableView;

    @FXML
    private Button Event_updateBtn;

    @FXML
    private Button OpenSystem;

    @FXML
    private Button Sponsor_button;

    @FXML
    private Button TypeEvent_button;

    @FXML
    private Button TypeSponsor_button;

    @FXML
    private ImageView addEmployee_image1;

    @FXML
    private Button addEmployee_importBtn1;

    @FXML
    private AnchorPane main_form;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EvenementShowListData();
        EvenementComboType();

    }
    @FXML
    public void EvenementComboType() {
        ObservableList<EvenementType> EvenementTypeList =  new EvenementTypeServices().GetAll();
        List<String> listP = new ArrayList<>();

        EvenementTypeList.forEach((data) -> {
            listP.add(data.getType());
        });

        ObservableList listData = FXCollections.observableArrayList(listP);
        Event_EventType.setItems(listData);
    }

    @FXML
    public void EvenementAdd() {

        try {
            Alert alert;
            String regexInt = "^[0-9]+$";
            if (Event_EventType.getSelectionModel().getSelectedItem() == null
                    || !(Event_EventId.getText().isEmpty())
                    || Event_EventBeginDate.getValue() == null
                   // || !(Event_EventCapacite.getText().matches(regexInt))
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields but not the id");
                //alert.setContentText("capacite dois etre un nombre");

                alert.showAndWait();
            } else {

//                 boolean exist = new EvenementTypeServices().checkEventTypeExist(Event_EventType.getValue());

//                if (exist) {
//                    alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Type: " + Event_EventType.getValue() + " was already exist!");
//                    alert.showAndWait();
//                } else {
               String nom, lieu, description, capacite, prix;
                String beginAt, finishAt;

                nom = Event_EventNom.getText();
                lieu = Event_EventLieu.getText();
                description = Event_EventDescription.getText();
                capacite = Event_EventCapacite.getText();
                prix = Event_EventPrix.getText();

                /**
                 * Begin At*
                 */
                // récupérer la date sélectionnée dans le DatePicker
                LocalDate dateB = Event_EventBeginDate.getValue();




                /*// convertir la valeur de dateTime en une chaîne de caractères avec la méthode toString()
    String dateTimeString = dateTime.toString();*/
                // ou bien, formater la valeur de dateTime avec la méthode format() pour afficher la date et l'heure dans un format spécifique
               // beginAt = dateB.format(DateTimeFormatter.ofPattern("yyyy-MM-dd "));
                beginAt = dateB.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                /**
                 * finish At*
                 */
                // récupérer la date sélectionnée dans le DatePicker
                LocalDate dateF = Event_EventFinishDate.getValue();


                /*// convertir la valeur de dateTime en une chaîne de caractères avec la méthode toString()
    String dateTimeString = dateTime.toString();*/
                // ou bien, formater la valeur de dateTime avec la méthode format() pour afficher la date et l'heure dans un format spécifique
                finishAt = dateF.format(DateTimeFormatter.ofPattern("yyyy-MM-dd "));

                EvenementType et = new EvenementTypeServices().recupererEventTypeByType(Event_EventType.getSelectionModel().getSelectedItem().toString());
                Evenement e = new Evenement(nom, lieu, description, beginAt, finishAt, Integer.parseInt(capacite), Float.parseFloat(prix), et);
                boolean add = new Evenementservice().ajouterEvenement(e);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();

                EvenementShowListData();
                //EvenementTypeReset();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * *******Ajout Event *************
     */
    /*-----------------------------------------------------------------------*/
    /**
     * *******Modifier Event *************
     */
    @FXML
    public void EvenementUpdate() {

        try {
            Alert alert;
            if (Event_EventId.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                boolean exist = new Evenementservice().checkEventExistById(Event_EventId.getText());

                if (!exist) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID: " + Event_EventId.getText() + " does not exist!");
                    alert.showAndWait();
                } else {

                    String nom, lieu, description, capacite, prix, id;
                    String beginAt, finishAt;

                    id = Event_EventId.getText();
                    nom = Event_EventNom.getText();
                    lieu = Event_EventLieu.getText();
                    description = Event_EventDescription.getText();
                    capacite = Event_EventCapacite.getText();
                    prix = Event_EventPrix.getText();

                    /**
                     * Begin At*
                     */
                    // récupérer la date sélectionnée dans le DatePicker
                    LocalDate dateB = Event_EventBeginDate.getValue();



                    /*// convertir la valeur de dateTime en une chaîne de caractères avec la méthode toString()
    String dateTimeString = dateTime.toString();*/
                    // ou bien, formater la valeur de dateTime avec la méthode format() pour afficher la date et l'heure dans un format spécifique
                    beginAt = dateB.format(DateTimeFormatter.ofPattern("yyyy-MM-dd "));

                    /**
                     * finish At*
                     */
                    // récupérer la date sélectionnée dans le DatePicker
                    LocalDate dateF = Event_EventFinishDate.getValue();



                    /*// convertir la valeur de dateTime en une chaîne de caractères avec la méthode toString()
    String dateTimeString = dateTime.toString();*/
                    // ou bien, formater la valeur de dateTime avec la méthode format() pour afficher la date et l'heure dans un format spécifique
                    finishAt = dateF.format(DateTimeFormatter.ofPattern("yyyy-MM-dd "));

                    EvenementType et = new EvenementTypeServices().recupererEventTypeByType(Event_EventType.getSelectionModel().getSelectedItem().toString());
                    // System.out.println(et);
                    Evenement e = new Evenement(Integer.parseInt(id),nom, lieu, description, beginAt, finishAt, Integer.parseInt(capacite),  Float.parseFloat(prix), et);
                    boolean add = new Evenementservice().modifierEvenement(e);
                     System.out.println(add);
                      System.out.println(e);



                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated!");
                    alert.showAndWait();

                    EvenementShowListData();

                    // EvenementTypeReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * *******Modifier Event *************
     */
    /*-----------------------------------------------------------------------*/
    /**
     * **********Delete Type Event****************************
     */
    @FXML
    public void EvenementDelete() {

        try {

            Alert alert;
            if (Event_EventNom.getText().isEmpty()
                    || Event_EventId.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Event Nom: " + Event_EventNom.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    /**
                     * **************
                     */
                    boolean exist = new Evenementservice().checkEventExistById(Event_EventId.getText());
                    if (exist) {
                        String nom, lieu, description, capacite, prix, id;
                        String beginAt, finishAt;

                        id = Event_EventId.getText();
                        nom = Event_EventNom.getText();
                        lieu = Event_EventLieu.getText();
                        description = Event_EventDescription.getText();
                        capacite = Event_EventCapacite.getText();
                        prix = Event_EventPrix.getText();

                        /**
                         * Begin At*
                         */
                        // récupérer la date sélectionnée dans le DatePicker
                        LocalDate dateB = Event_EventBeginDate.getValue();



                        /*// convertir la valeur de dateTime en une chaîne de caractères avec la méthode toString()
    String dateTimeString = dateTime.toString();*/
                        // ou bien, formater la valeur de dateTime avec la méthode format() pour afficher la date et l'heure dans un format spécifique
                        beginAt = dateB.format(DateTimeFormatter.ofPattern("yyyy-MM-dd "));

                        /**
                         * finish At*
                         */
                        // récupérer la date sélectionnée dans le DatePicker
                        LocalDate dateF = Event_EventFinishDate.getValue();



                        /*// convertir la valeur de dateTime en une chaîne de caractères avec la méthode toString()
    String dateTimeString = dateTime.toString();*/
                        // ou bien, formater la valeur de dateTime avec la méthode format() pour afficher la date et l'heure dans un format spécifique
                        finishAt = dateF.format(DateTimeFormatter.ofPattern("yyyy-MM-dd "));

                      //  EvenementType et = new EvenementTypeServices().recupererEventTypeByType(Event_EventType.getValue().toString());
                     //   Evenement e = new Evenement(nom, lieu, description, beginAt, finishAt, Integer.parseInt(capacite),  Float.parseFloat(prix), et);
                        boolean add = new Evenementservice().supprimerEvenement(Integer.parseInt(id));
                        System.out.println(add);
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Deleted!");
                        alert.showAndWait();

                      // EvenementTypeShowListData();
                      //  EvenementTypeReset();

                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("This ID" + Event_EventId.getText() + "Does not exist");
                        alert.showAndWait();

                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * **********Delete Type Event****************************
     */
    /*-----------------------------------------------------------------------*/
    /*Afficher la liste des Event Dans le tableau*/
    public void EvenementShowListData() {
        ObservableList<Evenement> EvenementList = (ObservableList<Evenement>) new Evenementservice().GetAll();

        Event_col_EventId.setCellValueFactory(new PropertyValueFactory<>("id"));
        // System.out.println(new PropertyValueFactory<>("aze"));
        Event_col_Type.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Evenement, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Evenement, String> param) {
                return new SimpleStringProperty(param.getValue().getType().getType());

            }
        });
        Event_col_Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Event_col_Lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        Event_col_BeginAt.setCellValueFactory(new PropertyValueFactory<>("beginat"));
        Event_col_FinishAt.setCellValueFactory(new PropertyValueFactory<>("finishat"));
        Event_col_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Event_col_Capacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        Event_col_Prix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        // System.out.println(EvenementList);
        Event_tableView.setItems(EvenementList);


    }

    /*Afficher la liste des Event Dans le tableau*/
    /*----------------------------------------------------------------------------------------------------*/
    /*Selectionner une ligne dans le tableau de Type Event*/

        @FXML
        public void EvenementSelect() {
            Evenement evenement = Event_tableView.getSelectionModel().getSelectedItem();
            int num = Event_tableView.getSelectionModel().getSelectedIndex();

            if ((num - 1) < -1) {
                return;
            }

            Event_EventId.setText(String.valueOf(evenement.getId()));
            Event_EventNom.setText(String.valueOf(evenement.getNom()));
            Event_EventLieu.setText(String.valueOf(evenement.getLieu()));
            Event_EventDescription.setText(String.valueOf(evenement.getDescription()));
            Event_EventCapacite.setText(String.valueOf(evenement.getCapacite()));
            Event_EventPrix.setText(String.valueOf(evenement.getPrix()));
            Event_EventType.setValue(String.valueOf(evenement.getType().getType()));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            String date = "16/08/2016";

            //convert String to LocalDate
            LocalDate localDate = LocalDate.parse(date, formatter);

            Event_EventFinishDate.setValue(localDate);
            Event_EventBeginDate.setValue(localDate);


        }
    @FXML
    public void goTypeEvent(ActionEvent event) throws IOException {
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





    /*Selectionner une ligne dans le tableau de Type Event*/

/*----------------------------------------------------------------------------------------------------*/

}


