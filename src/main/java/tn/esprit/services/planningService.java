package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.ReservationCours;
import tn.esprit.entities.Salle;
import tn.esprit.entities.cours;
import tn.esprit.utilis.DataSource;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class planningService {

    private Connection conn;
    //pour lancer une requete
    private Statement ste;
    private PreparedStatement Preste;

    public planningService() {
        conn = DataSource.getInstance().getCnx();
    }

    private ResultSet rs;


//    public ObservableList<cours> readAll() {
//
//        ObservableList<cours> obv = FXCollections.observableArrayList();
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("select * from cours JOIN salle ON salle.id=cours.id_salle");
//            while(rs.next()){
//                cours c =new cours();
//                Salle s = new Salle();
//
//                c.setId(rs.getInt(1));
//                c.setType(rs.getString(2));
//
//                s.setId(rs.getInt(4));
//                s.setNom(rs.getString(5));
//                s.setAdresse(rs.getString(6));
//                s.setDescription(rs.getString(7));
//                s.setImage(rs.getString(8));
//                c.setSalle(s);
//                c.setId_salle(s.getId());
//                obv.add(c);
//
//            }
//        }catch (SQLException e) {
//            //  System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//        return obv;
//    }
//


    public int checkCoursExist(int idSalle, String selectednomCours) {
        String req = "SELECT id FROM cours WHERE id_salle =  '" + idSalle + "' AND type = '" + selectednomCours + "'";
        int ids = -1;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(req);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                ids = rs.getInt("id");

            }
        } catch (SQLException ex) {
            Logger.getLogger(planningService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ids;
    }


    public void add(ReservationCours rc) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateDebutStr = sdf.format(rc.getDate_c());

        String requete = "insert into reservcours  (id_cours,date_c,id_client,heure) values (?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, rc.getId_cours());

            pst.setString(2, dateDebutStr);
            pst.setInt(3, rc.getId_client());
            pst.setTime(4, rc.getHeure());


            pst.executeUpdate();
            System.out.println("Reservation ajoutée!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("reservation echoué: " + e.getMessage());
        }
    }



    public boolean checkCExist(int idcours , Date selectedDate, Time selectedTime) {

        String req = "select id_cours from reservcours where id_cours= '" + idcours + "' AND date_c = '" + selectedDate + "' AND heure='"+ selectedTime+"'";
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(planningService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }


    public ObservableList<ReservationCours> readAll2 () {

        ObservableList<ReservationCours> obv = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from reservcours ");
            while (rs.next()) {
                ReservationCours rt = new ReservationCours();
                rt.setId(rs.getInt("id"));
                rt.setId_cours(rs.getInt("id_cours"));
                rt.setDate_c(rs.getDate("date_c"));
                rt.setId_client(rs.getInt("id_client"));
                rt.setHeure(rs.getTime("heure"));


                obv.add(rt);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return obv;
    }


}



