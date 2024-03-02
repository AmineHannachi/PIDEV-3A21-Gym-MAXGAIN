package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.ReservationTerrain;
import tn.esprit.entities.Terrain;
import tn.esprit.utilis.DataSource;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationService {
    private Connection conn;
    //pour lancer une requete
    private static Statement ste;
    private PreparedStatement Preste;
    private ResultSet rs;

    public ReservationService() {
        conn = DataSource.getInstance().getCnx();
    }


    public void add(ReservationTerrain sa) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateDebutStr = sdf.format(sa.getDate());

        String requete = "insert into Reservation (dateR,heure,id_Terrain,id_User) values (?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, dateDebutStr);
            pst.setTime(2, sa.getHeure());
            pst.setInt(3, sa.getId_terrain());
            pst.setInt(4, sa.getId_user());

            pst.executeUpdate();
            System.out.println("Reservation ajoutée!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while adding reservation: " + e.getMessage());
        }
    }
        public ObservableList<ReservationTerrain> readAll2 () {

            ObservableList<ReservationTerrain> obv = FXCollections.observableArrayList();
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from reservation ");
                while (rs.next()) {
                    ReservationTerrain rt = new ReservationTerrain();
                    rt.setId(rs.getInt("id"));
                    rt.setDate(rs.getDate("dateR"));
                    rt.setHeure(rs.getTime("heure"));
                    rt.setId_terrain(rs.getInt("id_terrain"));
                    rt.setId_user(rs.getInt("id_user"));
                    obv.add(rt);

                }
            } catch (SQLException e) {
                 System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return obv;
        }



    public boolean checkTerrainExist(int idTerrain, Date selectedDate, Time selectedTime) {

        String req = "select id_terrain from reservation where id_terrain= '" + idTerrain + "' AND dateR = '" + selectedDate + "' AND heure='"+ selectedTime+"'";
        try {
            ste = conn.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
