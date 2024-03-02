package tn.esprit.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Role;
import tn.esprit.entities.Terrain;
import tn.esprit.entities.User;
import tn.esprit.utilis.DataSource;

    public class TerrainService implements IService <Terrain> {

        private Connection conn;
        //pour lancer une requete
        private static Statement ste;
        private PreparedStatement Preste;
        private ResultSet rs;

        public TerrainService() {
            conn = DataSource.getInstance().getCnx();
        }

        private static final String API_KEY = "AIzaSyAuOYHXWIDs19k34sT7MfqKiRlU1_b-xfc";

        public String geocodeAddress(String address) throws IOException {
            // Construire l'URL de requête
            String urlStr = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + API_KEY;

            // Créer une connexion HTTP
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Lire la réponse
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Retourner la réponse
            return response.toString();
        }

        public String getMapURL(String address) {
            // Vous pouvez construire l'URL de la carte en fonction de l'adresse fournie
            // Par exemple, pour Google Maps :
            String mapURL = "https://www.google.com/maps?q=" + address;
            return mapURL;
        }
        @Override
        public void add(Terrain ter) {
            String redrequete = "insert into Terrain (nom,description,adresse,prix,image) values (?,?,?,?,?)";

            try {
                Preste = conn.prepareStatement(redrequete);

                Preste.setString(1, ter.getNom());
                Preste.setString(2, ter.getDescription());
                Preste.setString(3, ter.getAdresse());

                Preste.setDouble(4, ter.getPrix());
                Preste.setString(5, ter.getImage());

                Preste.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void delete(int idt) {
            try {
                String sql = "DELETE FROM terrain WHERE id = ?";
                Preste = conn.prepareStatement(sql);
                Preste.setInt(1, idt);
                int rowsDeleted = Preste.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Terrain deleted successfully");
                } else {
                    System.out.println("Failed to delete terrain");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void update(int id_modif, Terrain terrain) {
        }

        @Override
        public void Update(Terrain newterrain) {

            String sql = "UPDATE terrain SET nom=?,description=?, adresse=?, prix=?, image=? WHERE id=?";
            try {
                PreparedStatement ste = conn.prepareStatement(sql);
                ste.setString(1, newterrain.getNom());
                ste.setString(2, newterrain.getDescription());
                ste.setString(3, newterrain.getAdresse());
                ste.setDouble(4, newterrain.getPrix());
                ste.setString(5, newterrain.getImage());
                ste.setInt(6, newterrain.getId());
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
        public void recherche(Terrain nomTerrain) {

        }


        @Override
        public ObservableList<Terrain> readAll() {
            ObservableList<Terrain> obv = FXCollections.observableArrayList();
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from terrain");
                while (rs.next()) {
                    Terrain t = new Terrain();
                    t.setId((rs.getInt("id")));
                    t.setNom((rs.getString("nom")));
                    t.setAdresse((rs.getString("adresse")));
                    t.setDescription((rs.getString("description")));
                    t.setPrix((rs.getInt("prix")));
                    t.setImage((rs.getString("image")));
                    obv.add(t);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return obv;
        }

        public Terrain readByNom(String nomT) {
            String query = "SELECT * FROM terrain WHERE nom = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, nomT);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // Récupération des données de l'utilisateur à partir du ResultSet
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    String adresse = rs.getString("adresse");
                    String description = rs.getString("description");
                    int prix = rs.getInt("prix");
                    String image = rs.getString("image");


                    // Création et retour de l'objet User
                    return new Terrain(id, nom, adresse, description, prix, image);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Terrain readByID(int idT) {
            String query = "SELECT * FROM terrain WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, idT);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // Récupération des données de l'utilisateur à partir du ResultSet
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    String adresse = rs.getString("adresse");
                    String description = rs.getString("description");
                    int prix = rs.getInt("prix");
                    String image = rs.getString("image");


                    // Création et retour de l'objet User
                    return new Terrain(id, nom, adresse, description, prix, image);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null; // Retourne null si aucun utilisateur avec l'ID spécifié n'est trouvé
        }

        public int obtenirIdTerrain(String nomterrain) {
            int idterrain = -1; // par défaut, si l'ID n'est pas trouvé, nous retournons -1
            String sql = "SELECT id FROM Terrain WHERE nom = ?";

            try {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, nomterrain);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    idterrain = resultSet.getInt("id");
                    System.out.println(idterrain);
                }

                // Fermeture des ressources

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return idterrain;
        }


        public String obtenirNomSalle(int id) {
            String nom = null; // par défaut, si l'ID n'est pas trouvé, nous retournons -1
            String sql = "SELECT nom FROM Terrain WHERE id = ?";

            try {

                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    nom = resultSet.getString("nom");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return nom;

        }

        public boolean checkTerrainExist(String t) {
            String req = "select nom from terrain where nom= '" + t + "'";
            try {
                ste = conn.createStatement();
                rs = ste.executeQuery(req);
                if (rs.next()) {
                    return true;

                }
            } catch (SQLException ex) {
                Logger.getLogger(TerrainService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }

    }




