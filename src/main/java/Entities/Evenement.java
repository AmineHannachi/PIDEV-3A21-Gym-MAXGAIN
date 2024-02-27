package Entities;

public class Evenement {
    private String nom,lieu,description;
    private String beginat,finishat;
    private int capacite,id;
    private float prix;
    private EvenementType type ;
    public Evenement() {
    }

    public Evenement(String nom, String lieu, String description, String beginat, String finishat, int capacite, float prix, EvenementType type) {
        this.nom = nom;
        this.lieu = lieu;
        this.description = description;
        this.beginat = beginat;
        this.finishat = finishat;
        this.capacite = capacite;
       // this.id = id;
        this.prix = prix;
        this.type = type;
    }

    public Evenement(int id,String nom, String lieu, String description, String beginat, String finishat, int capacite, float prix, EvenementType type) {
        this.nom = nom;
        this.lieu = lieu;
        this.description = description;
        this.beginat = beginat;
        this.finishat = finishat;
        this.capacite = capacite;
        this.id = id;
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

    public String getBeginat() {
        return beginat;
    }

    public void setBeginat(String beginat) {
        this.beginat = beginat;
    }

    public String getFinishat() {
        return finishat;
    }

    public void setFinishat(String finishat) {
        this.finishat = finishat;
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

    @Override
    public String toString() {
        return "Evenement{" +
                "nom='" + nom + '\'' +
                ", lieu='" + lieu + '\'' +
                ", description='" + description + '\'' +
                ", beginat='" + beginat + '\'' +
                ", finishat='" + finishat + '\'' +
                ", capacite=" + capacite +
                ", id=" + id +
                ", prix=" + prix +
                ", type=" + type +
                '}';
    }



}




