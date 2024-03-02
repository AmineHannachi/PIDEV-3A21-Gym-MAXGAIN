package tn.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Promotion {
    private int id;
    private int pourcentage;
    private Date  dateDebut;
    private Date dateFin;

    public Promotion() {
    }

    public Promotion(int pourcentage, Date dateDebut, Date dateFin) {
        this.pourcentage = pourcentage;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Promotion(int id, int pourcentage, Date dateDebut, Date dateFin) {
        this.id = id;
        this.pourcentage = pourcentage;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(int pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", pourcentage=" + pourcentage +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return id == promotion.id && pourcentage == promotion.pourcentage && Objects.equals(dateDebut, promotion.dateDebut) && Objects.equals(dateFin, promotion.dateFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pourcentage, dateDebut, dateFin);
    }
}
