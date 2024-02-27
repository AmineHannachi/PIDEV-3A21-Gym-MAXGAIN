package Services;

import Entities.EvenementType;
import Interfaces.IEvenementType;
import Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvenementTypeServices implements IEvenementType {
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public EvenementTypeServices() {
        try {
            cnx = DataSource.getInstance().getCnx();
            ste = cnx.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean ajouterEvenementType(EvenementType c) {
        String req = "INSERT INTO `eevnementtype`(`type`) VALUES (?)";
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
    public ObservableList<EvenementType> GetAll() {
        String req = "select * from eevnementtype";
        ObservableList<EvenementType> EvenementTypeList = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                EvenementType c = new EvenementType();
                c.setId(rs.getInt("id"));
                c.setType(rs.getString("type"));

                EvenementTypeList.add(c);

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return EvenementTypeList;
    }

    @Override
    public boolean modifierEvenementType(EvenementType c) {
        String req = "UPDATE eevnementtype SET type=? WHERE id=?";
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
    public boolean supprimerEvenementType(int c) {
        String req = "delete from eevnementtype where id=?";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, c);
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }





    public EvenementType recupererEventTypeByType(String t) {
        String req = "select * from eevnementtype where  type = '" + t + "'";

        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                EvenementType e = new EvenementType(id, type);
                return e;

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        EvenementType e = new EvenementType(0, "");

        return e;
    }


    public EvenementType recupererEventTypeById(int t) {
        String req = "select * from eevnementtype where  id = '" + t + "'";

        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                EvenementType e = new EvenementType(id, type);
                return e;

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        EvenementType e = new EvenementType(0, "");

        return e;
    }
    public boolean checkEventTypeExist(String t) {
        String req = "select type from eevnementtype where  type = '" + t + "'";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean checkEventTypeExistById(String t) {
        String req = "select id from eevnementtype   where  id = '" + t + "'";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
