package tn.esprit.entities;

import java.util.Date;

public class Planning {
    private int id;
    private Date date;
    private String cours;

    public Planning() {
    }

    public Planning(Date date, String cours) {
        this.date = date;
        this.cours = cours;
    }

    public Planning(int id, Date date, String cours) {
        this.id = id;
        this.date = date;
        this.cours = cours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCours() {
        return cours;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

    @Override
    public String toString() {
        return "Planning{" +
                "id=" + id +
                ", date=" + date +
                ", cours='" + cours + '\'' +
                '}';
    }
}
