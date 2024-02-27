package tn.esprit.entities;

import java.util.Objects;

public class ResrvationSalle {
    private int id;
    private String adresse;
    private String disponibilite;
    private Salle salle;

    public ResrvationSalle() {
    }

    public ResrvationSalle( String adresse, String disponibilite) {
        this.adresse = adresse;
        this.disponibilite = disponibilite;
    }

    public ResrvationSalle(int id, String adresse, String disponibilite) {
        this.id = id;
        this.adresse = adresse;
        this.disponibilite = disponibilite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResrvationSalle that = (ResrvationSalle) o;
        return id == that.id && Objects.equals(adresse, that.adresse) && Objects.equals(disponibilite, that.disponibilite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adresse, disponibilite);
    }
}
