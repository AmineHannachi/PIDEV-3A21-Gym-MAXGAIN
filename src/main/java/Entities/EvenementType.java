package Entities;

public class EvenementType {
    int id;
    String type;

    public EvenementType(String type ) {
        this.type = type;
    }

    public EvenementType() {
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
        return "EvenementType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    public EvenementType(int id, String type) {
        this.id = id;
        this.type = type;
    }
}
