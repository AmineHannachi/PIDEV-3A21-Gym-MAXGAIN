package Entities;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Sponsor implements Initializable {
    int id;
    String nom,logo,email;
    SponsorType type;
    Evenement event;

    public Sponsor() {
    }

    public Sponsor(int id) {
        this.id = id;
    }


    public Sponsor(String nom, String logo, String email, SponsorType type) {
        this.nom = nom;
        this.logo = logo;
        this.email = email;
        this.type = type;
    }

    public Sponsor(String nom, String logo, String email, SponsorType type, Evenement event) {
        this.nom = nom;
        this.logo = logo;
        this.email = email;
        this.type = type;
        this.event = event;
    }

    public Sponsor(int id, String nom, String logo, String email, SponsorType type, Evenement event) {
        this.id = id;
        this.nom = nom;
        this.logo = logo;
        this.email = email;
        this.type = type;
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SponsorType getType() {
        return type;
    }

    public void setType(SponsorType type) {
        this.type = type;
    }

    public Evenement getEvent() {
        return event;
    }

    public void setEvent(Evenement event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Sponsor{" + "id=" + id + ", nom=" + nom + ", logo=" + logo + ", email=" + email + ", type=" + type + ", event=" + event + '}';
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
