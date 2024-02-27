package models;

public class Offres {
    private int id; // This represents the primary key in the database
    private double prix;
    private int duree;
    private String description;

    public Offres(int id, double prix, int duree, String description) {
        this.id = id;
        this.prix = prix;
        this.duree = duree;
        this.description = description;
    }

    public Offres(double prix, int duree, String description) {
        this.prix = prix;
        this.duree = duree;
        this.description = description;
    }

    public Offres() {
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // You might want to override toString() for debugging purposes

    @Override
    public String toString() {
        return "Offres{" +
                "id=" + id +
                ", prix=" + prix +
                ", duree=" + duree +
                ", description='" + description + '\'' +
                '}';
    }
}
