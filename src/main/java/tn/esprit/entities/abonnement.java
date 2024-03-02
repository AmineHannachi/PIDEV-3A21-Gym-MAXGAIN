package tn.esprit.entities;

import java.util.Objects;

public class abonnement {
    private int id;
    private float paiement;
    private String email;
    private String  username;
    private  Offre offre;
    private Salle salle;

    public abonnement() {
    }

    public abonnement(float paiement, String email, String username) {
        this.paiement = paiement;
        this.email = email;
        this.username = username;
    }

    public abonnement(int id, float paiement, String email, String username) {
        this.id = id;
        this.paiement = paiement;
        this.email = email;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPaiement() {
        return paiement;
    }

    public void setPaiement(float paiement) {
        this.paiement = paiement;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "abonnement{" +
                "id=" + id +
                ", paiement=" + paiement +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        abonnement that = (abonnement) o;
        return id == that.id && Float.compare(paiement, that.paiement) == 0 && Objects.equals(email, that.email) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paiement, email, username);
    }
}
