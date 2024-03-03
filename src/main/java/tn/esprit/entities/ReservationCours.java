package tn.esprit.entities;

import java.sql.Time;
import java.util.Date;


public class ReservationCours {
    private int id;

    private int id_cours;
     private int id_client;


    private cours cours;
    private User user;
    private Date date_c;

    private Time heure;

    public ReservationCours() {
    }

    public ReservationCours(int id,  int id_cours, cours cours, User user, Date date_c,int id_client, Time heure) {
        this.id = id;
        this.id_client = id_client;
        this.id_cours = id_cours;
        this.cours = cours;
        this.user = user;
        this.date_c = date_c;
        this.heure = heure;
    }

    public ReservationCours(int id_cours, Date date_c,int id_client,  Time heure) {

        this.id_cours = id_cours;
        this.date_c = date_c;
        this.id_client = id_client;

        this.heure = heure;
    }


    public int getId() {
        return id;
    }

    public int getId_client() {
        return id_client;
    }

    public int getId_cours() {
        return id_cours;
    }

    public cours getcours() {
        return cours;
    }

    public User getUser() {
        return user;
    }

    public Date getDate_c() {
        return date_c;
    }

    public Time getHeure() {
        return heure;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    public void setCours(tn.esprit.entities.cours cours) {
        this.cours = cours;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDate_c(Date date_c) {
        this.date_c = date_c;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }
}



