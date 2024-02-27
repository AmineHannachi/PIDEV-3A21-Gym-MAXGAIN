package tn.esprit.entities;

    public class Terrain {
        private int id;
        private String nom;
        private String adresse;
        private double prix;
        private String disponibilite;
        private String image;
        public Terrain() {
        }

        public Terrain(int id, String nom, String adresse, double prix, String disponibilite, String image) {
            this.id = id;
            this.nom = nom;
            this.adresse = adresse;
            this.prix = prix;
            this.disponibilite = disponibilite;
            this.image = image;
        }

        public Terrain(String nom, String adresse, double prix, String disponibilite, String image) {
            this.nom = nom;
            this.adresse = adresse;
            this.prix = prix;
            this.disponibilite = disponibilite;
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getAdresse() {
            return adresse;
        }

        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }

        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
            this.prix = prix;
        }

        public String getDisponibilite() {
            return  disponibilite;
        }

        public void setDisponibilite(String disponibilite) {
            this.disponibilite = disponibilite;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public String toString() {
            return "Terrain{" +
                    "id=" + id +
                    ", nom='" + nom + '\'' +
                    ", adresse='" + adresse + '\'' +
                    ", prix=" + prix +
                    ", disponibilite=" + disponibilite +
                    ", image='" + image + '\'' +
                    '}';
        }
    }

