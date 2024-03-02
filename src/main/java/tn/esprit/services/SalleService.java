package tn.esprit.services;


import tn.esprit.entities.Salle;
import tn.esprit.utilis.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleService implements IService <Salle>{

        private Connection conn;
        //pour lancer une requete
        private Statement ste;
        private PreparedStatement Preste;
        public SalleService() {
            conn= DataSource.getInstance().getCnx();
        }
        @Override
        public void add(Salle sa) {
            String redrequete= "insert into salle (adresse,nom,description, image) values (?,?,?,?)";

            try{
                Preste=conn.prepareStatement(redrequete);
                Preste.setString(1,sa.getAdresse());
                Preste.setString(2, sa.getNom());
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
                    System.out.println("salle deleted successfully");
                } else {
                    System.out.println("Failed to delete salle");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }



    @Override
    public void update(int id_Modif, Salle sa) {

            String sql = "UPDATE salle SET adresse=?, nom=?, description=?, image=? WHERE id=?";
            try {
                PreparedStatement ste = conn.prepareStatement(sql);
                ste.setString(1, sa.getAdresse());
                ste.setString(2, sa.getNom());
                ste.setString(3, sa.getDescription());
                ste.setString(4, sa.getImage());
                ste.setInt(5, id_Modif); // Set the ID of the product to be modified

                int rowsUpdated = ste.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Produit modifié avec succès");
                } else {
                    System.out.println("Aucun produit n'a été modifié");
                }
            } catch (SQLException e) {
                System.err.format("Erreur lors de la modification du produit : %s%n", e.getMessage());
            }

        }

    @Override
    public void Update(Salle salle) {

    }

    @Override
        public List<Salle> readAll() {

            String requete="select * from salle";
            List<Salle>list=new ArrayList<>();
            try {
                ste=conn.createStatement();
                ResultSet rs=ste.executeQuery(requete);
                while(rs.next()){
                    list.add(new Salle(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
                }
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return list;
        }

        @Override
        public Salle readByID(int id) {
            return null;
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
    }

