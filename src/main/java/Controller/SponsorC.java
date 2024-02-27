package Controller;

import Entities.Evenement;
import Entities.Sponsor;
import Entities.SponsorType;
import Interfaces.MyListner;
import Services.Evenementservice;
import Services.SponsorService;
import Services.SponsorTypeService;
import Utils.getData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.eclipse.jetty.server.Authentication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SponsorC implements Initializable {

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
    private ImageView Sponsor_image;


    @FXML
    private AnchorPane main_form;



    @FXML
    private GridPane sponsorGrid;


    @FXML
    public void sponsorTypeCombo() {
        ObservableList<SponsorType> SponsorTypeList = new SponsorTypeService().GetAll();
        List<String> listP = new ArrayList<>();

        SponsorTypeList.forEach((data) -> {
            listP.add(data.getType());
        });

        ObservableList listData = FXCollections.observableArrayList(listP);
        Sponsor_SponsorType.setItems(listData);
    }

    @FXML
    public void EvenementCombo() {
        ObservableList<Evenement> EvenementList = (ObservableList<Evenement>) new Evenementservice().GetAll();
        List<String> listP = new ArrayList<>();

        EvenementList.forEach((data) -> {
            listP.add(data.getNom());
        });

        ObservableList listData = FXCollections.observableArrayList(listP);
        Sponsor_Evenement.setItems(listData);
    }

    /*-----------------------------------------------------------------------*/

    /**
     * **********Add Sponsor****************************
     */
    @FXML
    public void SponsorAdd() {
        String regexNom = "^[a-zA-Z0-9]+$";
        String regexMail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        try {
            Sponsor_SponsorId.setText(String.valueOf(""));

            Alert alert;
            if (Sponsor_SponsorType.getSelectionModel().getSelectedItem() == null
                    || !(Sponsor_SponsorId.getText().isEmpty())
                    || Sponsor_SponsorName.getText().isEmpty()
                    || Sponsor_SponsorMail.getText().isEmpty()
                    || !(Sponsor_SponsorName.getText().matches(regexNom))
                    || !(Sponsor_SponsorMail.getText().matches(regexMail))
                    || getData.path == null || getData.path == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields but not the id");
                alert.setContentText("le nom ne peut avoir que des caracteres et des chiffres");
                alert.setContentText("l' email doit etre comme suit 'exemple@exemple.com'");
                alert.showAndWait();
            } else {

                /* boolean exist = new EvenementTypeService().checkEventTypeExist(TypeEvent_TypeEvent.getText());

                if (exist) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Type: " + TypeEvent_TypeEvent.getText() + " was already exist!");
                    alert.showAndWait();
                } else {*/
                String Type, nom, email, event;

                nom = Sponsor_SponsorName.getText();

                email = Sponsor_SponsorMail.getText();
                // System.out.println(getData.path);

                SponsorType et = new SponsorTypeService().recupererSponsorTypeByType(Sponsor_SponsorType.getSelectionModel().getSelectedItem().toString());
                //  System.out.println(et);
                Evenement ev = new Evenementservice().recupererEventByName(Sponsor_Evenement.getSelectionModel().getSelectedItem().toString());
                  System.out.println(ev);
                Sponsor s = new Sponsor(nom, getData.path, email, et, ev);

                boolean add = new SponsorService().ajouterSponsor(s);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();

                SponsorAfficher();

            }
            //}

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * **********Add Sponsor****************************
     */
    /*-----------------------------------------------------------------------*/
    /**
     * **********Afficher Sponsor****************************
     */
    private MyListner myListner;

    @FXML
    public void SponsorAfficher() {
        ObservableList<Sponsor> sponsorList = new SponsorService().GetAll();
        System.out.println(sponsorList);
        if (sponsorList.size() > 0) {
            //setChosenSponsor(sponsorList.get(0));
            myListner = new MyListner() {

                @Override
                public void onClickListener(Sponsor sponsor) {

                    setChosenSponsor(sponsor);
                }
            };
        }

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < sponsorList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/itemSponsorBack.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ItemSponsorBackController   itemController = fxmlLoader.getController();

                //System.out.println(myListener);
                itemController.setData(sponsorList.get(i), myListner);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                sponsorGrid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                sponsorGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                sponsorGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                sponsorGrid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                sponsorGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                sponsorGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                sponsorGrid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * **********Afficher Sponsor****************************
     */
    /*-----------------------------------------------------------------------*/
    /**
     * **********setChosen Sponsor****************************
     */
    private Image image;

    private void setChosenSponsor(Sponsor sponsor) {
        Sponsor_SponsorId.setText(String.valueOf(sponsor.getId()));
        Sponsor_SponsorName.setText(sponsor.getNom());
        Sponsor_SponsorMail.setText(sponsor.getEmail());
        if (sponsor.getType().getType() != null) {
            Sponsor_SponsorType.setValue(sponsor.getType().getType());

        }
        if (sponsor.getEvent().getNom() != null) {
            Sponsor_Evenement.setValue(sponsor.getEvent().getNom());

        }
        //System.out.println(getClass().getResourceAsStream(sponsor.getLogo()));

        File file = new File(sponsor.getLogo());
        //System.out.println(file.toURI().toString());
        image = new Image(file.toURI().toString(), 101, 127, false, true);
        // System.out.println(image);
        Sponsor_image.setImage(image);

        /* if (getClass().getResourceAsStream(sponsor.getLogo()) != null) {
           // System.out.println(sponsor.getLogo());
            image = new Image(getClass().getResourceAsStream(sponsor.getLogo()));
            Sponsor_image.setImage(image);
        }*/
        //System.out.println(sponsor.getLogo());
        getData.path = sponsor.getLogo();

    }

    /**
     * **********setChosen Sponsor****************************
     */


    @FXML
    public String SponsorInsertImage() {
        Image image;
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            getData.path = file.getAbsolutePath();
            // System.out.println(getData.path);
            image = new Image(file.toURI().toString(), 101, 127, false, true);
            Sponsor_image.setImage(image);
            return getData.path;
        }
        return "";
    }

    /*-----------------------------------------------------------------------*/

    /**
     * *********************Delete Sponsor************************************
     */
    @FXML
    public void SponsorDelete() {

        try {

            Alert alert;
            if (Sponsor_SponsorId.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select item to delete");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Sponsor : " + Sponsor_SponsorName.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    /**
                     * **************
                     */

                    Sponsor e = new Sponsor(Integer.parseInt(Sponsor_SponsorId.getText()));
                    boolean delete = new SponsorService().supprimerSponsor(e);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    SponsorAfficher();
                    // SponsorTypeReset();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sponsorTypeCombo();
        EvenementCombo();
        SponsorAfficher();

    }
    @FXML
    public void updateSponsor() {
        try {
            // Retrieve the updated information from UI components
            int sponsorId = Integer.parseInt(Sponsor_SponsorId.getText());
            String updatedName = Sponsor_SponsorName.getText();
            String updatedEmail = Sponsor_SponsorMail.getText();
            String selectedType = Sponsor_SponsorType.getSelectionModel().getSelectedItem();
            String selectedEvent = Sponsor_Evenement.getSelectionModel().getSelectedItem();

            // Perform any validation or error handling as needed

            // Update the sponsor
            Sponsor sponsorToUpdate = new Sponsor();
            sponsorToUpdate.setId(sponsorId);
            sponsorToUpdate.setNom(updatedName);
            sponsorToUpdate.setEmail(updatedEmail);

            // Retrieve the selected sponsor type and event from your services
            SponsorType sponsorType = new SponsorTypeService().recupererSponsorTypeByType(selectedType);
            Evenement event = new Evenementservice().recupererEventByName(selectedEvent);

            sponsorToUpdate.setType(sponsorType);
            sponsorToUpdate.setEvent(event);

            // Perform the update operation
            boolean success = new SponsorService().modifierSponsor(sponsorToUpdate);

            if (success) {
                // Display success message to the user
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Sponsor updated successfully!");
                alert.showAndWait();

                // Refresh the view or update UI as needed
                SponsorAfficher();
            } else {
                // Display error message if update failed
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update sponsor!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions or errors that occur during the update process
            // Display appropriate error messages to the user if needed
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



    /**
     * *********************Delete Sponsor************************************
     */

    /*-----------------------------------------------------------------------*/
    /**
     * *********************Fin Sponsor************************************
     */
}