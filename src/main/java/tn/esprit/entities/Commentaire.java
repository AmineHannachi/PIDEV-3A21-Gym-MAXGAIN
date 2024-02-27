package tn.esprit.entities;

public class Commentaire {
    private int id;
    private User user;
    private Evenement event;
    private String description;

    public Commentaire() {
    }

    public Commentaire(User user, Evenement event, String description) {
           this.user = user;
        this.event = event;
        this.description = description;
    }

    public Commentaire(int id, User user, Evenement event, String description) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Evenement getEvent() {
        return event;
    }

    public void setEvent(Evenement event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", user=" + user +
                ", event=" + event +
                ", description='" + description + '\'' +
                '}';
    }
}
