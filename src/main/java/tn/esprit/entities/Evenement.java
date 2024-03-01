package tn.esprit.entities;

public class Evenement {


    private String nom,lieu,description;
    private String beginAt,finishAt;
    private int capacite,id;
    private float prix;
    private EvenementType type;
    public Evenement() {
    }

    public Evenement(String nom, String lieu, String description, String beginAt, String finishAt, int capacite, float prix, EvenementType type) {
        this.nom = nom;
        this.lieu = lieu;
        this.description = description;
        this.beginAt = beginAt;
        this.finishAt = finishAt;
        this.capacite = capacite;
        this.prix = prix;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }

    public String getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(String finishAt) {
        this.finishAt = finishAt;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public EvenementType getType() {
        return type;
    }

    public void setType(EvenementType type) {
        this.type = type;
    }
}
