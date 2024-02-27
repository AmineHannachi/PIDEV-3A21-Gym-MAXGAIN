package Services;

import Entities.Evenement;
import Entities.EvenementType;
import Interfaces.IEvenement;
import Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Evenementservice implements IEvenement {
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    public Evenementservice() {
        try {
            cnx = DataSource.getInstance().getCnx();
            ste = cnx.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean ajouterEvenement(Evenement c) {
        String req = "INSERT INTO `evenement`"
                + "( `IdType`, `nom`, `lieu`, `beginat`, `finishat`, `description`, `capacite`, `prix`) "
                + "VALUES (?,?,?,?,?,?,?,?)";
        try {
            pst = cnx.prepareStatement(req);


            pst.setInt(1, c.getType().getId());

            pst.setString(2, c.getNom());
            pst.setString(3, c.getLieu());
            pst.setString(4, c.getBeginat());
            pst.setString(5, c.getFinishat());
            pst.setString(6, c.getDescription());
            pst.setInt(7, c.getCapacite());
            pst.setFloat(8, c.getPrix());

            pst.executeUpdate();
            System.out.println("l'evenement est ajoutee avec succes!!!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Evenement> GetAll() {String req = "select * from evenement";
        ObservableList<Evenement> EvenementList = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Evenement c = new Evenement();
                EvenementType e = new EvenementTypeServices().recupererEventTypeById(rs.getInt("idtype"));
                c.setId(rs.getInt("id"));
                c.setType(e);
                c.setNom(rs.getString("nom"));
                c.setLieu(rs.getString("lieu"));
                c.setBeginat(rs.getString("beginat"));
                c.setFinishat(rs.getString("finishat"));
                c.setDescription(rs.getString("description"));
                c.setCapacite(rs.getInt("capacite"));
                c.setPrix(rs.getFloat("prix"));

                EvenementList.add(c);

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return EvenementList;

    }

    @Override
    public boolean modifierEvenement(Evenement c) {
        String req = "UPDATE `evenement`"
                + " SET `IdType`=?,"
                + "`nom`=?,"
                + "`lieu`=?,"
                + "`beginat`=?"
                + ",`finishat`=?,"
                + "`description`=?,"
                + "`capacite`=?,"
                + "`prix`=? "
                + "WHERE id = ?";
        try {
            pst = cnx.prepareStatement(req);

            pst.setInt(1, c.getType().getId());
            pst.setString(2, c.getNom());
            pst.setString(3, c.getLieu());
            pst.setString(4, c.getBeginat());
            pst.setString(5, c.getFinishat());
            pst.setString(6, c.getDescription());
            pst.setInt(7, c.getCapacite());
            pst.setFloat(8, c.getPrix());
            pst.setInt(9, c.getId());

            pst.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean supprimerEvenement(int c) {
        String req = "delete from evenement where id=?";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, c );
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkEventExistById(String t) {
        String req = "select id from evenement where  id = '" + t + "'";

        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Evenementservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public Evenement recupererEventById(int t) {
        String req = "select * from evenement where  id = '" + t + "'";

        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                int id = rs.getInt("id");
                String type_e = rs.getString("Idtype");
                String lieu = rs.getString("lieu");
                String description = rs.getString("description");
                String beginAt = rs.getString("beginat");
                String finishAt = rs.getString("finishat");
                int capacite = rs.getInt("capacite");
                float prix = rs.getFloat("prix");

                if (type_e != null) {
                    EvenementType et = new EvenementTypeServices().recupererEventTypeByType(type_e);
                }
                EvenementType et = new EvenementType();

                String nom = rs.getString("nom");

                Evenement e = new Evenement(nom, lieu, description, beginAt, finishAt,
                        capacite, prix, et
                );
                return e;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Evenementservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        Evenement e = new Evenement();

        return e;
    }

    public Evenement recupererEventByName(String t) {
        String req = "select * from evenement where  nom = '" + t + "'";

        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                int id = rs.getInt("id");
                String type_e = rs.getString("idtype");
                String lieu = rs.getString("lieu");
                String description = rs.getString("description");
                String beginAt = rs.getString("beginat");
                String finishAt = rs.getString("finishat");
                int capacite = rs.getInt("capacite");
                float prix = rs.getFloat("prix");

                EvenementType et = new EvenementTypeServices().recupererEventTypeByType(type_e);
                String nom = rs.getString("nom");

                Evenement e = new Evenement(id,nom, lieu, description, beginAt, finishAt,
                        capacite,  prix, et
                );
                System.out.println(e);
                return e;

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        Evenement e = new Evenement();
        System.out.println(e);
        return e;
    }

    public int recupererCapaciteById(int t) {
        String req = "select `capacite` from evenement where  id = '" + t + "'";

        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                int capacite = rs.getInt("capacite");

                return capacite;

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        Evenement e = new Evenement();

        return 0;
    }

    public boolean MAJpass(int c) {
        int capacite = recupererCapaciteById(c);
        String req =  "UPDATE evenement SET capacite = ? WHERE id = ?";
        try {
            pst = cnx.prepareStatement(req);

            pst.setInt(1, capacite - 1);
            pst.setInt(2, c);

            pst.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    }


