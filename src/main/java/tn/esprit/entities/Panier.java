package tn.esprit.entities;

public class Panier {
    private int id ;
    private int quantite;
    private float totalePanier;

    public Panier() {
    }

    public Panier(int quantite, float totalePanier) {
        this.quantite = quantite;
        this.totalePanier = totalePanier;
    }

    public Panier(int id, int quantite, float totalePanier) {
        this.id = id;
        this.quantite = quantite;
        this.totalePanier = totalePanier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public float getTotalePanier() {
        return totalePanier;
    }

    public void setTotalePanier(float totalePanier) {
        this.totalePanier = totalePanier;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", quantite=" + quantite +
                ", totalePanier=" + totalePanier +
                '}';
    }
}
