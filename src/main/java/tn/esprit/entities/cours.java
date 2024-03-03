package tn.esprit.entities;





public class cours {

    private int id;
    private String type;
    private Salle salle;
    private int id_salle;

    public cours(String type, int id_salle) {
        this.type = type;
        this.id_salle = id_salle;
    }

    public cours(int id, String type, int id_salle) {
        this.id = id;
        this.type = type;
        this.id_salle = id_salle;
    }

    public cours(int id, String type, Salle salle, int id_salle) {
        this.id = id;
        this.type = type;
        this.salle = salle;
        this.id_salle = id_salle;
    }

    public cours(String type, Salle salle, int id_salle) {
        this.type = type;
        this.salle = salle;
        this.id_salle = id_salle;
    }

    public cours() {
    }

    public int getId_salle() {
        return id_salle;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Salle getSalle() {
        return salle;
    }

    public int setId(int id) {
        this.id = id;
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    @Override
    public String toString() {
        return "cours{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", salle=" + salle +
                ", id_salle=" + id_salle +
                '}';
    }
}
