package tn.esprit.entities;

import java.util.Objects;

public class Categorie {
    private int id;
    private String name;
    private String description;

    public Categorie() {
    }

    public Categorie(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Categorie(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "categorie{" +
                "id=" + id +
                ",name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return id == categorie.id && Objects.equals(name, categorie.name) && Objects.equals(description, categorie.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
