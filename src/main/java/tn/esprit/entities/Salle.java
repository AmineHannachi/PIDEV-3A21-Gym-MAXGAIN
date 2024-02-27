package tn.esprit.entities;



public class Salle {
    private int id;
    private String nom;
    private String adresse;
    private String image;
    private String description ;

    public Salle() {
    }

    public Salle(int id, String adresse, String nom, String description , String image) {
        this.id = id;
        this.adresse = adresse;
        this.nom = nom;
        this.description = description;
        this.image = image;
    }

    public Salle(String adresse, String nom, String description, String image) {

        this.adresse = adresse;
        this.nom = nom;
        this.description = description;
        this.image = image;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "salle{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }




}

