package Services;

import Entities.Evenement;
import Entities.Sponsor;
import Entities.SponsorType;
import Interfaces.ISponsor;
import Utils.DataSource;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SponsorService implements ISponsor {
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public SponsorService() {

        try {
            cnx = DataSource.getInstance().getCnx();
            ste = cnx.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public boolean ajouterSponsor(Sponsor c) {
        String req = "INSERT INTO `sponsor`"
                + "(  `type_id`, `nom`, `logo`, `email`, `event_id`) "
                + "VALUES (?,?,?,?,?)";
        try {
            pst = cnx.prepareStatement(req);

            pst.setInt(1, c.getType().getId());
            pst.setString(2, c.getNom());
            pst.setString(3, c.getLogo());
            pst.setString(4, c.getEmail());
            pst.setInt(5, c.getEvent().getId());


            pst.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
   public ObservableList<Sponsor> GetAll() {
        String req = "select * from sponsor";
        ObservableList<Sponsor> SponsorList = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Sponsor c = new Sponsor();
                Evenement e = new Evenementservice().recupererEventById(rs.getInt("event_id"));
                SponsorType st = new SponsorTypeService().recupererSponsorTypeById(rs.getInt("type_id"));
                c.setId(rs.getInt("id"));
                c.setType(st);
                c.setLogo(rs.getString("logo"));
                c.setEmail(rs.getString("email"));
                c.setNom(rs.getString("nom"));
                c.setEvent(e);

                SponsorList.add(c);

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
      }
       return SponsorList;
   }

    @Override
    public boolean modifierSponsor(Sponsor c) {
        String req = "UPDATE sponsor SET type_id=?, nom=?, logo=?, email=?, event_id=? WHERE id=?";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, c.getType().getId());
            pst.setString(2, c.getNom());
            pst.setString(3, c.getLogo());
            pst.setString(4, c.getEmail());
            pst.setInt(5, c.getEvent().getId());
            pst.setInt(6, c.getId());

            int rowsUpdated = pst.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;




    }

    @Override
    public boolean supprimerSponsor(Sponsor c) {
        String req = "delete from sponsor where id=?";
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
    public ObservableList<Sponsor> recupererSponsorsByEventId(int t) {
        String req = "select * from sponsor where  event_id = '" + t + "'";
        ObservableList<Sponsor> SponsorList = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Sponsor c = new Sponsor();
                Evenement e = new Evenementservice().recupererEventById(rs.getInt("event_id"));
                SponsorType st = new SponsorTypeService().recupererSponsorTypeById(rs.getInt("type_id"));
                c.setId(rs.getInt("id"));
                c.setType(st);
                c.setLogo(rs.getString("logo"));
                c.setEmail(rs.getString("email"));
                c.setNom(rs.getString("nom"));
                c.setEvent(e);

                SponsorList.add(c);

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SponsorList;
    }



}
