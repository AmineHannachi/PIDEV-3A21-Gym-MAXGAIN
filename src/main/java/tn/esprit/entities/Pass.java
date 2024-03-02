package tn.esprit.entities;

public class Pass {
   private int id;
    private Evenement evenement;
    private int client_id;
    private String createdAt;

    public Pass() {
    }

    public Pass( Evenement evenement, int client_id, String createdAt) {
        this.evenement = evenement;
        this.client_id = client_id;
        this.createdAt = createdAt;
    }

    public Pass(int id, Evenement evenement, int client_id, String createdAt) {
        this.id = id;
        this.evenement = evenement;
        this.client_id = client_id;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
