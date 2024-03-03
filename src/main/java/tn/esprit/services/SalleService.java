package tn.esprit.services;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Salle;
import tn.esprit.utilis.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalleService implements IService<Salle> {

        private Connection conn;
        //pour lancer une requete
        private Statement ste;
        private PreparedStatement Preste;
        private ResultSet rs;
        public SalleService() {
            conn= DataSource.getInstance().getCnx();
        }
        @Override
        public void add(Salle sa) {
            String redrequete= "insert into salle (nom,adresse,description, image) values (?,?,?,?)";

            try{
                Preste=conn.prepareStatement(redrequete);
                Preste.setString(1,sa.getNom());
                Preste.setString(2, sa.getAdresse());
                Preste.setString(3, sa.getDescription());
                Preste.setString(4, sa.getImage());
                Preste.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }


    @Override
        public void delete(int ids) {
            try {
                String sql = "DELETE FROM salle WHERE id = ?";
                Preste= conn.prepareStatement(sql);
                Preste.setInt(1, ids);
                int rowsDeleted = Preste.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("salle supprimée avec succès");
                } else {
                    System.out.println("Échec de la suppression de la salle");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    @Override
    public void update(int id_Modif, Salle salle) {

    }


    @Override
        public  ObservableList<Salle> readAll() {
            ObservableList<Salle> obv = FXCollections.observableArrayList();
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from salle");
                while(rs.next()){
                    Salle sa =new Salle();
                    sa.setId((rs.getInt("id")));
                    sa.setNom((rs.getString("nom")));
                    sa.setAdresse((rs.getString("adresse")));
                    sa.setDescription((rs.getString("description")));
                    sa.setImage((rs.getString("image")));
                    obv.add(sa);

                }
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
                return obv;
            }

        @Override
        public Salle readByID(int id) {
            return null;
        }

    @Override
    public void Update(Salle salle) {

        String sql = "UPDATE salle SET nom=?, adresse=?, description=?, image=? WHERE id=?";
        try {
            PreparedStatement ste = conn.prepareStatement(sql);
            ste.setString(1, salle.getNom());
            ste.setString(2, salle.getAdresse());
            ste.setString(3, salle.getDescription());
            ste.setString(4, salle.getImage());
            ste.setInt(5, salle.getId()); // Set the ID of the product to be modified

            int rowsUpdated = ste.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Salle modifié avec succès");
            } else {
                System.out.println("Aucun salle n'a été modifié");
            }
        } catch (SQLException e) {
            System.err.format("Erreur lors de la modification du salle : %s%n", e.getMessage());
        }

    }

    @Override
    public int obtenirIdSalle(String nomSalle) {
        int idSalle = -1; // par défaut, si l'ID n'est pas trouvé, nous retournons -1

        try {

            String sql = "SELECT id FROM salle WHERE nom = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nomSalle);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                idSalle = resultSet.getInt("id");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return idSalle;

    }



    @Override
    public String obtenirNomSalle(int idSalle) {
        String nomSalle = null; // par défaut, si l'ID n'est pas trouvé, nous retournons -1

        try {

            String sql = "SELECT nom FROM salle WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idSalle);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nomSalle = resultSet.getString("nom");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }


        return nomSalle;

    }




    @Override
        public void recherche(Salle sa){
            String sql = "SELECT * FROM salle WHERE nom = ?";

            try {
                // Préparation de la requête
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, sa.getNom()); // Supposons que vous avez une méthode getNom() dans la classe Salle

                // Exécution de la requête
                ResultSet resultSet = statement.executeQuery();

                // Traitement des résultats
                while (resultSet.next()) {
                    // Lecture des données de la salle
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    // Lire d'autres colonnes si nécessaire

                    // Faire quelque chose avec les données récupérées
                    System.out.println("ID: " + id + ", Nom: " + nom);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'exception selon vos besoins
            }
        }


    public boolean checkSalleExist(String t) {
        String req = "select nom from salle where nom= '" + t + "'";
        ObservableList<Salle> obv = FXCollections.observableArrayList();
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(SalleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}

