package tn.esprit.entities;

public class SponsorType {
   private  int id;
    private String type;

    public SponsorType() {
    }

    public SponsorType( String type) {
        this.type = type;
    }

    public SponsorType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SponsorType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
