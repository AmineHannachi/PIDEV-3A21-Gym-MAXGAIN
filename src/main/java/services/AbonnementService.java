package services;

import models.Abonnement;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbonnementService implements IService<Abonnement> {

    private Connection connection;

    public AbonnementService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Abonnement abonnement) throws SQLException {
        String sql = "insert into abonnement (offre_id, salle, date_d, mpayement, email, name) " +
                "values(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, abonnement.getOffreId()); // Use 1-based index
            preparedStatement.setString(2, abonnement.getSalle());
            preparedStatement.setDate(3, Date.valueOf(abonnement.getDate()));
            preparedStatement.setString(4, abonnement.getMpayement());
            preparedStatement.setString(5, abonnement.getEmail());
            preparedStatement.setString(6, abonnement.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void modifier(Abonnement abonnement) throws SQLException {
        String sql = "update abonnement set offre_id = ?, salle = ?, date_d = ?, mpayement = ?, email = ?, name = ? where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, abonnement.getOffreId()); // Use 1-based index
            preparedStatement.setString(2, abonnement.getSalle());
            preparedStatement.setDate(3, Date.valueOf(abonnement.getDate()));
            preparedStatement.setString(4, abonnement.getMpayement());
            preparedStatement.setString(5, abonnement.getEmail());
            preparedStatement.setString(6, abonnement.getName());
            preparedStatement.setInt(7, abonnement.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from abonnement where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public List<Abonnement> recuperer() throws SQLException {
        String sql = "SELECT abonnement.*, offres.description AS offreDescription " +
                "FROM abonnement " +
                "JOIN offres ON abonnement.offre_id = offres.id";

        List<Abonnement> abonnements = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Abonnement abonnement = new Abonnement();
                abonnement.setId(rs.getInt("id"));
                abonnement.setOffreId(rs.getInt("offre_id"));
                abonnement.setSalle(rs.getString("salle"));
                abonnement.setDate(rs.getDate("date_d").toLocalDate());
                abonnement.setMpayement(rs.getString("mpayement"));
                abonnement.setEmail(rs.getString("email"));
                abonnement.setName(rs.getString("name"));
                abonnement.setOfferDescription(rs.getString("offreDescription")); // Assuming you have added this property to your Abonnement class

                abonnements.add(abonnement);
            }
        }

        return abonnements;
    }

}
