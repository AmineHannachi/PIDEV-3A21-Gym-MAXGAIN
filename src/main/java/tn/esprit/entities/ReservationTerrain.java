package tn.esprit.entities;


import java.sql.Time;
import java.util.Date;

public class ReservationTerrain {
    private int id;

    private Date dateR;
    private Time heure;
    private int id_user;
    private int id_terrain;
    private Terrain terrain;
    private User user;
    public ReservationTerrain() {
    }



    public ReservationTerrain(Date dateR, Time heure, int id_terrain, int id_user) {
        this.dateR = dateR;
        this.heure = heure;
        this.id_terrain = id_terrain;
        this.id_user = id_user;

    }

    public Terrain getTerrain() {
        return terrain;
    }

    public ReservationTerrain(int id, Date dateR, Time heure, int id_user, int id_terrain, Terrain terrain, User user) {
        this.id = id;
        this.dateR = dateR;
        this.heure = heure;
        this.id_user = id_user;
        this.id_terrain = id_terrain;
        this.terrain = terrain;
        this.user = user;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return dateR;
    }

    public void setDate(Date dateR) {
        this.dateR = dateR;
    }

    public Time getHeure() {
        return this.heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_terrain() {
        return id_terrain;
    }

    public void setId_terrain(int id_terrain) {
        this.id_terrain = id_terrain;
    }

    @Override
    public String toString() {
        return "ReservationTerrain{" +
                "id=" + id +
                ", date=" + dateR +
                ", heure=" + heure +
                ", id_terrain=" + id_terrain +
                ", id_user=" + id_user +

                                '}';
    }
}
