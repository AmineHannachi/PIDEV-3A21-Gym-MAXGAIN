package tn.esprit.utilis;

import java.sql.*;

public class DataSource {
    private static final String URL = "jdbc:mysql://localhost:3306/maxgain";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static DataSource instance;
    private Connection connection;

    private DataSource() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getCnx() {
        return connection;
    }

    public void saveVerificationCode(String email, String code) {
        String sql = "INSERT INTO password_reset (email, code) VALUES ('" + email + "', '" + code + "')";
        try (Statement stmt = getCnx().createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isVerificationCodeCorrect(String email, String code) {
        String sql = "SELECT code FROM password_reset WHERE email = ? AND code = ?";
        try (Connection conn = getCnx();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, code);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void updatePassword(String email, String newPassword) {
        String sql = "UPDATE user SET password = '" + newPassword + "' WHERE email = '" + email + "'";
        try (Statement stmt = getCnx().createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getVerificationCode(String email) {
        String sql = "SELECT code FROM password_reset WHERE email = '" + email + "'";
        try (Statement stmt = getCnx().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getString("code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
