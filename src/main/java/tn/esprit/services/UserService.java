package tn.esprit.services;

import tn.esprit.entities.Role;
import tn.esprit.entities.User;
import tn.esprit.utilis.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {
    private Connection conn;

    public UserService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(User user) {
        String query = "INSERT INTO user (username, email, birthdate, gender, phone, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setDate(3, new java.sql.Date(user.getBirthdate().getTime()));
            stmt.setString(4, user.getGender());
            stmt.setInt(5, user.getPhone());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getRole().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(String username) {
        String query = "DELETE FROM user WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully");
            } else {
                System.out.println("No user deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String oldUsername, User newUser) {
        String query = "UPDATE user SET username = ?, email = ?, birthdate = ?, gender = ?, phone = ?, password = ?, confirmPass = ? WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newUser.getUsername());
            stmt.setString(2, newUser.getEmail());
            stmt.setDate(3, new java.sql.Date(newUser.getBirthdate().getTime()));
            stmt.setString(4, newUser.getGender());
            stmt.setInt(5, newUser.getPhone());
            stmt.setString(6, newUser.getPassword());
            stmt.setString(7, newUser.getConfirmPass());
            stmt.setString(8, oldUsername);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                Date birthdate = rs.getDate("birthdate");
                String gender = rs.getString("gender");
                int phone = rs.getInt("phone");
                String password = rs.getString("password");
                String confirmPass = rs.getString("confirmPass");
                Role role = Role.valueOf(rs.getString("role"));
                return new User( username, email, birthdate, gender, phone, password, confirmPass, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM user WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                Date birthdate = rs.getDate("birthdate");
                String gender = rs.getString("gender");
                int phone = rs.getInt("phone");
                String password = rs.getString("password");
                String confirmPass = rs.getString("confirmPass");
                Role role = Role.valueOf(rs.getString("role"));
                return new User(username, email, birthdate, gender, phone, password, confirmPass, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void recherche(User user) {
        // Ajoutez la logique de recherche ici
    }

    @Override
    public List<User> readAll() {
        String query = "SELECT * FROM user";
        List<User> list = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                Date birthdate = rs.getDate("birthdate");
                String gendre = rs.getString("gender");
                String password = rs.getString("password");
                String confirmPass = rs.getString("confirmPass");
                int phone = rs.getInt("phone");
                Role role = Role.valueOf(rs.getString("role"));
                list.add(new User(username,email, birthdate,  gendre,phone, password, confirmPass, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
