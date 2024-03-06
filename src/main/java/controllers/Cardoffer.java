    package controllers;


    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.scene.control.Label;
    import javafx.scene.layout.AnchorPane;
    import models.Offres;
    import services.MyListener;
    import services.OffresService;

    public class Cardoffer {

        @FXML
        private AnchorPane card_form;

        @FXML
        private Label Description;

        @FXML
        private Label prix;

        @FXML
        private Label Date;
        private MyListener MyListener;

        private OffresService offresService;
        private ObservableList<Offres> offresList;
        private int offreId;


        public ObservableList<Offres> getOffresList() {
            return offresList;
        }


        Offres Offres = new Offres();

        public void setData(Offres Offres,MyListener MyListener) {

        this.Offres=Offres;

            offreId = Offres.getId();
            Description.setText(Offres.getDescription());
            prix.setText(String.valueOf(Offres.getPrix()));
            Date.setText(String.valueOf(Offres.getDuree()));
        }
    }
