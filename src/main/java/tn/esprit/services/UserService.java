package tn.esprit.services;

import tn.esprit.entities.Role;
import tn.esprit.entities.User;
import tn.esprit.utilis.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class UserService  implements IService<User> {
        private Connection conn;
        private Statement ste;
        private PreparedStatement Preste;
        public UserService() {
            conn = DataSource.getInstance().getCnx();
        }




        @Override
        public void add(User user) {
            String redrequete= "insert into user (username,name,prenom,age,email,password,telephone,role) values (?,?,?,?,?,?,?,?)";

            try{
                Preste=conn.prepareStatement(redrequete);
                Preste.setString(1, user.getUsername());
                Preste.setString(2, user.getName());
                Preste.setString(3,user.getPrenom());
                Preste.setInt(4,user.getAge());
                Preste.setString(5,user.getEmail());
                Preste.setString(6,user.getPassword());
                Preste.setInt(7,user.getTelephone());
                Preste.setString(8,user.getRole().name());
                Preste.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM User WHERE id=?";
            try (PreparedStatement ste = conn.prepareStatement(sql)) {
                ste.setInt(1, id);
                int rowsDeleted = ste.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("user supprimé avec succès");
                } else {
                    System.out.println("Aucun user n'a été supprimé");
                }
            } catch (SQLException e) {
                System.err.format("Erreur lors de la suppression du produit : %s%n", e.getMessage());
            }
        }

        @Override
        public void update(int id_Modif, User nouvelle_valeurs) {
            String sql = "UPDATE User SET username=?, name=?, prenom=?,age=?, email=?, password=?, telephone=? ,role=? WHERE id=?";
            try {
                PreparedStatement ste = conn.prepareStatement(sql);
                ste.setString(1, nouvelle_valeurs.getUsername());
                ste.setString(2, nouvelle_valeurs.getName());
                ste.setString(3, nouvelle_valeurs.getPrenom());
                ste.setInt(4, nouvelle_valeurs.getAge());
                ste.setString(5, nouvelle_valeurs.getEmail());
                ste.setString(6, nouvelle_valeurs.getPassword());
                ste.setInt(7, nouvelle_valeurs.getTelephone());
                ste.setString(8, nouvelle_valeurs.getRole().name());
                ste.setInt(9, id_Modif); // Set the ID of the product to be modified

                int rowsUpdated = ste.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("User modifié avec succès");
                } else {
                    System.out.println("Aucun user n'a été modifié");
                }
            } catch (SQLException e) {
                System.err.format("Erreur lors de la modification d'un user : %s%n", e.getMessage());
            }

        }

        @Override
        public void recherche(User user) {

        }

        @Override
        public List<User> readAll() {
            String requete = "SELECT * FROM user";
            List<User> list = new ArrayList<>();
            try {
                ste = conn.createStatement();
                ResultSet rs = ste.executeQuery(requete);
                while (rs.next()) {
                    // Utiliser rs.getString("role") pour récupérer le rôle en tant que chaîne de caractères
                    // Puis convertir cette chaîne en Enum Role
                    Role role = Role.valueOf(rs.getString("role"));
                    // Utiliser le constructeur approprié pour créer un nouvel objet User
                    list.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("name"), rs.getString("prenom"),
                            rs.getInt("age"), rs.getString("email"), rs.getString("password"), rs.getInt("telephone"),
                            role)); // Utilisation de l'enum Role au lieu de rs.getString("role")
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return list;
        }


        @Override
        public User readByID(int id)
        {
            String query = "SELECT * FROM user WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // Récupération des données de l'utilisateur à partir du ResultSet
                    int userId = rs.getInt("id");
                    String username = rs.getString("username");
                    String name = rs.getString("name");
                    String prenom = rs.getString("prenom");
                    int age = rs.getInt("age");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    int telephone = rs.getInt("telephone");
                    String role = rs.getString("role");

                    // Création et retour de l'objet User
                    return new User(userId, username, name, prenom, age, email, password, telephone, Role.valueOf(role));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null; // Retourne null si aucun utilisateur avec l'ID spécifié n'est trouvé
        }

        @Override
        public void Update(User user) {

        }

        @Override
        public int obtenirIdSalle(String nomSalle) {
            return 0;
        }

        @Override
        public String obtenirNomSalle(int idSalle) {
            return null;
        }

    }






