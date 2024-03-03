package tn.esprit.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entities.Terrain;
import tn.esprit.utilis.DataSource;

    public class TerrainService implements IService <Terrain>{

        private Connection conn;
        //pour lancer une requete
        private static Statement ste;
        private PreparedStatement Preste;
        public TerrainService() {
            conn= DataSource.getInstance().getCnx();
        }

        @Override
        public void add(Terrain ter) {
            String redrequete= "insert into Terrain (nom,adresse,prix,disponibilite,image) values (?,?,?,?,?)";

            try {
                Preste=conn.prepareStatement(redrequete);
                Preste.setString(1,ter.getNom());
                Preste.setString(2,ter.getAdresse());
                Preste.setDouble(3,ter.getPrix());
                Preste.setString(4,ter.getDisponibilite());
                Preste.setString(5,ter.getImage());
                Preste.executeUpdate();
            } catch (SQLException e) {
//                throw new RuntimeException(e);
            }
        }
        @Override
        public void delete(int idt) {
            try {
                String sql = "DELETE FROM terrain WHERE id = ?";
                Preste= conn.prepareStatement(sql);
                Preste.setInt(1, idt);
                int rowsDeleted = Preste.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Terrain supprimé avec succès");
                } else {
                    System.out.println("Échec de la suppression du terrain");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }


        @Override
        public  void update(int id_Modif, Terrain newterrain) {

            String sql = "UPDATE Terrain SET nom=?, adresse=?, prix=?, disponibilite=?, image=? WHERE id=?";
            try {
                PreparedStatement ste = conn.prepareStatement(sql);
                ste.setString(1, newterrain.getNom());
                ste.setString(2, newterrain.getAdresse());
                ste.setDouble(3, newterrain.getPrix());
                ste.setString(4, newterrain.getDisponibilite());
                ste.setString(5, newterrain.getImage());
                ste.setInt(6, id_Modif); // Set the ID of the product to be modified

                int rowsUpdated = ste.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println(" modifié avec succès");
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
        public List<Terrain> readAll() {

            String requete="select * from Terrain";
            List<Terrain>list=new ArrayList<>();
            try {
                ste=conn.createStatement();
                ResultSet rs=ste.executeQuery(requete);
                while(rs.next()){
                    list.add(new Terrain (rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6)));
                }
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return list;
        }

        @Override
        public Terrain readByID(int id) {
            return null;
        }

        @Override
        public void Update(Terrain terrain) {

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


