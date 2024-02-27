package models;

import java.time.LocalDate;

public class Abonnement {
    private int id;
    private int offreId;
    private String salle;
    private LocalDate date;
    private String mpayement;
    private String email;
    private String name;
    private String offerDescription; // Added offerDescription property

    // Constructors, getters, and setters...

    public Abonnement() {
        // Default constructor
    }

    public Abonnement(int id, int offreId, String salle, LocalDate date, String mpayement, String email, String name) {
        this.id = id;
        this.offreId = offreId;
        this.salle = salle;
        this.date = date;
        this.mpayement = mpayement;
        this.email = email;
        this.name = name;
    }

    public Abonnement(String name, String email, String salle, String mpayement, LocalDate date, int offreId, String offerDescription) {
        this.name = name;
        this.email = email;
        this.salle = salle;
        this.mpayement = mpayement;
        this.date = date;
        this.offreId = offreId;
        this.offerDescription = offerDescription; // Added this line to set offerDescription
    }

    // Getters and setters for each field...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOffreId() {
        return offreId;
    }

    public void setOffreId(int offreId) {
        this.offreId = offreId;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMpayement() {
        return mpayement;
    }

    public void setMpayement(String mpayement) {
        this.mpayement = mpayement;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "id=" + id +
                ", offreId=" + offreId +
                ", salle='" + salle + '\'' +
                ", date=" + date +
                ", mpayement='" + mpayement + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", offerDescription='" + offerDescription + '\'' +
                '}';
    }

}
