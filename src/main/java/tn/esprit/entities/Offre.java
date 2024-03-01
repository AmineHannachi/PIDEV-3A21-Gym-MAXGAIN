package tn.esprit.entities;

public class Offre {
    private int id;
    private  Categorie categorie;
    private Promotion promotion;
    private float prix;
    private int duree;
    private String description;

    public Offre() {
    }

    public Offre(Categorie categorie, Promotion promotion, float prix, int duree, String description) {
        this.categorie = categorie;
        this.promotion = promotion;
        this.prix = prix;
        this.duree = duree;
        this.description = description;
    }

    public Offre(int id, Categorie categorie, Promotion promotion, float prix, int duree, String description) {
        this.id = id;
        this.categorie = categorie;
        this.promotion = promotion;
        this.prix = prix;
        this.duree = duree;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
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

    @Override
    public String toString() {
        return "Offre{" +
                "id=" + id +
                ", categorie=" + categorie +
                ", promotion=" + promotion +
                ", prix=" + prix +
                ", duree=" + duree +
                ", description='" + description + '\'' +
                '}';
    }
}
