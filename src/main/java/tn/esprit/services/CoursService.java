package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Salle;
import tn.esprit.entities.cours;
import tn.esprit.entities.User;
import tn.esprit.utilis.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursService implements IService<cours> {

    private Connection conn;
    //pour lancer une requete
    private Statement ste;
    private PreparedStatement Preste;

    public CoursService() {
        conn = DataSource.getInstance().getCnx();
    }

    public void add(cours cours) {
        String requete = "INSERT INTO cours (type, id_salle) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(requete);
            preparedStatement.setString(1, cours.getType());
            preparedStatement.setInt(2, cours.getId_salle()); // Assurez-vous que getIdSalle() retourne l'ID de la salle associée à ce cours

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int obtenirIdSalle(String nomSalle) {
      return  0;
    }

    @Override
    public String obtenirNomSalle(int idSalle) {
        return null;
    }


    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM cours WHERE id = ?";
            Preste = conn.prepareStatement(sql);
            Preste.setInt(1, id);
            int rowsDeleted = Preste.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("cours deleted successfully");
            } else {
                System.out.println("Failed to delete cours");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(int id_Modif, cours cours) {

    }


    @Override
    public ObservableList<cours> readAll() {

        ObservableList<cours> obv = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from cours");
            while(rs.next()){
                cours c =new cours();
                c.setId((rs.getInt("id")));
                c.setType((rs.getString("type")));
                c.setId_salle((rs.getInt("id_salle")));

                obv.add(c);

            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obv;
    }


    public ObservableList<cours> readAll2() {

        ObservableList<cours> obv = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from cours JOIN salle ON salle.id=cours.id_salle");
            while(rs.next()){
                cours c =new cours();
                Salle s = new Salle();

                c.setId(rs.getInt(1));
                c.setType(rs.getString(2));

                s.setId(rs.getInt(4));
                s.setNom(rs.getString(5));
                s.setAdresse(rs.getString(6));
                s.setDescription(rs.getString(7));
                s.setImage(rs.getString(8));
                c.setSalle(s);
                c.setId_salle(s.getId());
                obv.add(c);

            }
        }catch (SQLException e) {
          //  System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return obv;
    }




    @Override
    public cours readByID(int id) {
        return null;
    }


    @Override
    public void Update(cours c) {

        String sql = "UPDATE cours SET type=?, id_Salle=? WHERE id=?";
        try {
            PreparedStatement ste = conn.prepareStatement(sql);

            ste.setString(1, c.getType()); // Assurez-vous que getIdSalle() retourne l'ID de la salle associée à ce cours
            ste.setInt(2, c.getId_salle());
            ste.setInt(3, c.getId());


            int rowsUpdated = ste.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cours modifié avec succès");
            } else {
                System.out.println("Aucun cours n'a été modifié");
            }
        } catch (SQLException e) {
            System.err.format("Erreur lors de la modification du cours : %s%n", e.getMessage());
        }

    }




    @Override
    public void recherche(cours c) {

//            String sql = "SELECT * FROM cours WHERE id_salle = ?";
//
//            try {
//                // Préparation de la requête
//                PreparedStatement statement = conn.prepareStatement(sql);
//                statement.setInt(1, c.getId_salle()); // Supposons que vous avez une méthode getNom() dans la classe Salle
//
//                // Exécution de la requête
//                ResultSet resultSet = statement.executeQuery();
//
//                // Traitement des résultats
//                while (resultSet.next()) {
//                    // Lecture des données de la salle
//                    int id = resultSet.getInt("id");
//                    int id_salle = resultSet.getInt("id_salle");
//                    // Lire d'autres colonnes si nécessaire
//
//                    // Faire quelque chose avec les données récupérées
//                    System.out.println("ID: " + id + ", id_salle: " + id_salle);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                // Gérer l'exception selon vos besoins
//            }
    }
    }

