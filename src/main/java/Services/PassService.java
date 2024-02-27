/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Interfaces.iPass;
import Entities.Pass;
import Utils.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PassService implements iPass {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public PassService() {

        try {
            cnx = DataSource.getInstance().getCnx();
            ste = cnx.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(PassService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean ajouterPass(Pass c) {
        String req = "INSERT INTO `pass`( `createdAt`, `event_id`, `client_id`) VALUES (?,?,?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, c.getCreatedAt());
            pst.setInt(2, c.getEvent_id());
            pst.setInt(3, c.getClient_id());

            pst.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ObservableList<Pass> GetAll() {
        String req = "select * from pass";
        ObservableList<Pass> passList = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Pass c = new Pass();
                c.setId(rs.getInt("id"));
                c.setCreatedAt(rs.getString("createdAt"));
                c.setEvent_id(rs.getInt("event_id"));
                c.setClient_id(rs.getInt("client_id"));

                passList.add(c);

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passList;
    }

    public ObservableList<Pass> GetMyPasses(int id_client) {
        String req = "select * from pass where client_id = '" + id_client + "'";
        ObservableList<Pass> passList = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Pass c = new Pass();
                c.setId(rs.getInt("id"));
                c.setCreatedAt(rs.getString("createdAt"));
                c.setEvent_id(rs.getInt("event_id"));
                c.setClient_id(rs.getInt("client_id"));

                passList.add(c);

            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementTypeServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passList;
    }

    public String recupererNameClientById(int t) {
        String req = "select nom from user where  id = '" + t + "'";

        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            if (rs.next()) {

                String nom = rs.getString("nom");



                return nom;

            }
        } catch (SQLException ex) {
            Logger.getLogger(PassService.class.getName()).log(Level.SEVERE, null, ex);
        }


        return "client inexistant";
    }


}
