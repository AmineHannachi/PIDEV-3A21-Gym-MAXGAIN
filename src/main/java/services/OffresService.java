package services;

import models.Offres;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffresService implements IService<Offres> {

    private Connection connection;

    public OffresService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Offres offres) throws SQLException {
        String sql = "INSERT INTO offres (prix, duree, description) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, offres.getPrix());
            preparedStatement.setInt(2, offres.getDuree());
            preparedStatement.setString(3, offres.getDescription());
            preparedStatement.executeUpdate();

            // Retrieve the generated ID if needed
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                offres.setId(generatedKeys.getInt(1));
            }
        }
    }

    @Override
    public void modifier(Offres offres) throws SQLException {
        String sql = "UPDATE offres SET prix = ?, duree = ?, description = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, offres.getPrix());
            preparedStatement.setInt(2, offres.getDuree());
            preparedStatement.setString(3, offres.getDescription());
            preparedStatement.setInt(4, offres.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM offres WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Offres> recuperer() throws SQLException {
        String sql = "SELECT * FROM offres";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            List<Offres> offresList = new ArrayList<>();
            while (rs.next()) {
                Offres offres = new Offres();
                offres.setId(rs.getInt("id"));
                offres.setPrix(rs.getDouble("prix"));
                offres.setDuree(rs.getInt("duree"));
                offres.setDescription(rs.getString("description"));

                offresList.add(offres);
            }
            return offresList;
        }
    }

}
