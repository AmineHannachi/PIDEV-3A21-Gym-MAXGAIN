package tn.esprit.entities;

    public class Terrain {
        private int id;
        private String nom;
        private String description;
        private String adresse;

        private double prix;

        private String image;
        public Terrain() {
        }
        public Terrain(int id, String nom,double prix,  String image) {
            this.id = id;
            this.nom = nom;
            this.prix = prix;
            this.image = image;
        }
        public Terrain(int id, String nom,String description, String adresse, double prix,  String image) {
            this.id = id;
            this.nom = nom;
            this.description=description;
            this.adresse = adresse;
            this.prix = prix;
            this.image = image;


        }

        public Terrain(String nom, String description, String adresse,double prix, String image) {
            this.nom = nom;
            this.description=description;
            this.adresse = adresse;
            this.prix = prix;
            this.image = image;

        }


        public Terrain(String nom) {
            this.nom = nom;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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
                    ", description='" + description + '\'' +
                    ", prix=" + prix +
                    ", image='" + image + '\'' +
                    '}';
        }
    }

