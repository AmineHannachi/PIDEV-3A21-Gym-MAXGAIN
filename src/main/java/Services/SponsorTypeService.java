package Services;

import Entities.SponsorType;
import Interfaces.ISponsorType;
import Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SponsorTypeService implements ISponsorType {
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public SponsorTypeService() {
        try {
            cnx = DataSource.getInstance().getCnx();
            ste = cnx.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    @Override
    public boolean ajouterSponsorType(SponsorType c) {
        String req = "INSERT INTO `type_contrat`(`type_c`) VALUES (?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, c.getType());
            pst.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ObservableList<SponsorType> GetAll() {
        String req = "select * from type_contrat";
        ObservableList<SponsorType> sponsorTypeList = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                SponsorType c = new SponsorType();
                c.setId(rs.getInt("id"));
                c.setType(rs.getString("type_c"));

                sponsorTypeList.add(c);

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sponsorTypeList;
    }

    @Override
    public boolean modifierSponsorType(SponsorType c) {
        String req = "UPDATE type_contrat SET type_c=? WHERE id=?";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, c.getType());
            pst.setInt(2, c.getId());
            pst.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean supprimerSponsorType(SponsorType c) {
        String req = "delete from type_contrat where id=?";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, c.getId());
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkSponsorTypeExist(String t) {
        String req = "select type_c from type_contrat where  type_c = '" + t + "'";

        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(SponsorTypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean checkSponsorTypeExistById(String t) {
        String req = "select id from type_contrat where  id = '" + t + "'";

        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(SponsorTypeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public SponsorType recupererSponsorTypeByType(String t) {
        String req = "select * from type_contrat where  type_c = '" + t + "'";

        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                int id = rs.getInt("id");
                String type_c = rs.getString("type_c");
                SponsorType e = new SponsorType(id, type_c);
                return e;

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        SponsorType e = new SponsorType(0, "");

        return e;
    }


    public SponsorType recupererSponsorTypeById(int t) {
        String req = "select * from type_contrat where  id = '" + t + "'";

        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                int id = rs.getInt("id");
                String type_c = rs.getString("type_c");
                SponsorType e = new SponsorType(id, type_c);
                return e;

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        SponsorType e = new SponsorType(0, "");

        return e;
    }


}
