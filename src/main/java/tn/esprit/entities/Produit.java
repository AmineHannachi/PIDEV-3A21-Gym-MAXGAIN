package tn.esprit.entities;

public class Produit {
    private int id;
    private String nomp;
    private String typep;
    private float prix;
    private int stock;
    private String image;
    private CategorieP categorie;
    private Panier panier;

    public Produit() {
    }

    public Produit( String nomp, String typep, float prix, int stock, String image, CategorieP categorie, Panier panier) {
        this.nomp = nomp;
        this.typep = typep;
        this.prix = prix;
        this.stock = stock;
        this.image = image;
        this.categorie = categorie;
        this.panier = panier;
    }

    public Produit(int id, String nomp, String typep, float prix, int stock, String image, CategorieP categorie, Panier panier) {
        this.id = id;
        this.nomp = nomp;
        this.typep = typep;
        this.prix = prix;
        this.stock = stock;
        this.image = image;
        this.categorie = categorie;
        this.panier = panier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomp() {
        return nomp;
    }

    public void setNomp(String nomp) {
        this.nomp = nomp;
    }

    public String getTypep() {
        return typep;
    }

    public void setTypep(String typep) {
        this.typep = typep;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CategorieP getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieP categorie) {
        this.categorie = categorie;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nomp='" + nomp + '\'' +
                ", typep='" + typep + '\'' +
                ", prix=" + prix +
                ", stock=" + stock +
                ", image='" + image + '\'' +
                ", categorie=" + categorie +
                ", panier=" + panier +
                '}';
    }
}
