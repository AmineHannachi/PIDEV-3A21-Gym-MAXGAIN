package tn.esprit.services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Role;
import tn.esprit.entities.User;
import tn.esprit.repositories.IService;
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
    public void update(String oldUsername, User newUser) {
        String query = "UPDATE user SET username = ?, email = ?, birthdate = ?, gender = ?, phone = ?, password = ? WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newUser.getUsername());
            stmt.setString(2, newUser.getEmail());
            stmt.setDate(3, new java.sql.Date(newUser.getBirthdate().getTime()));
            stmt.setString(4, newUser.getGender());
            stmt.setInt(5, newUser.getPhone());
            stmt.setString(6, newUser.getPassword());
            stmt.setString(7, oldUsername);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(User user) {
        String query = "DELETE FROM user WHERE id = ?"; // Supposons que l'identifiant soit utilisé
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, user.getId()); // Utiliser l'identifiant de l'utilisateur
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM user WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id");
                String email = rs.getString("email");
                Date birthdate = rs.getDate("birthdate");
                String gender = rs.getString("gender");
                int phone = rs.getInt("phone");
                String password = rs.getString("password");
                // Supprimer la récupération du confirmPass
                Role role = Role.valueOf(rs.getString("role"));
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setEmail(email);
                user.setBirthdate(birthdate);
                user.setGender(gender);
                user.setPhone(phone);
                user.setPassword(password);
                user.setRole(role);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public User recherche(User user) {
        // Définir la requête SQL de base
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM user WHERE ");

        // Ajouter les conditions dynamiquement en fonction des propriétés de l'utilisateur
        boolean hasCondition = false;
        if (user.getUsername() != null) {
            queryBuilder.append("username = '").append(user.getUsername()).append("'");
            hasCondition = true;
        }
        if (user.getEmail() != null) {
            if (hasCondition) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append("email = '").append(user.getEmail()).append("'");
            hasCondition = true;
        }
        // Ajoutez des conditions pour d'autres champs si nécessaire

        // Exécutez la requête si au moins une condition est définie
        if (hasCondition) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(queryBuilder.toString());
                // Vérifiez s'il y a des résultats
                if (rs.next()) {
                    // Construisez un objet User à partir des résultats
                    User foundUser = new User();
                    foundUser.setId(rs.getLong("id"));
                    foundUser.setUsername(rs.getString("username"));
                    foundUser.setEmail(rs.getString("email"));
                    foundUser.setBirthdate(rs.getDate("birthdate"));
                    foundUser.setGender(rs.getString("gender"));
                    foundUser.setPhone(rs.getInt("phone"));
                    foundUser.setPassword(rs.getString("password"));
                    foundUser.setConfirmPass(rs.getString("confirmPass"));
                    foundUser.setRole(Role.valueOf(rs.getString("role")));
                    // Retournez l'utilisateur trouvé
                    return foundUser;
                }
            } catch (SQLException e) {
                // Gérez l'exception SQLException ici (par exemple, en la lançant à l'appelant)
                e.printStackTrace();
                throw new RuntimeException("Erreur lors de la recherche de l'utilisateur", e);
            }
        } else {
            System.out.println("Aucun critère de recherche spécifié.");
        }
        // Aucun utilisateur trouvé, retournez null
        return null;
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
                String gender = rs.getString("gender");
                int phone = rs.getInt("phone");
                Role role = Role.valueOf(rs.getString("role"));
                list.add(new User(username, email, birthdate, gender, phone,  role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<User> getAllClients() {
        ObservableList<User> clients = FXCollections.observableArrayList();
        String query = "SELECT * FROM user WHERE role = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, Role.CLIENT.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                Date birthdate = rs.getDate("birthdate");
                String gender = rs.getString("gender");
                int phone = rs.getInt("phone");
                clients.add(new User(username, email, birthdate, gender, phone, Role.CLIENT));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
    public ObservableList<User> getAllCoach() {
        ObservableList<User> clients = FXCollections.observableArrayList();
        String query = "SELECT * FROM user WHERE role = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, Role.COACH.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                Date birthdate = rs.getDate("birthdate");
                String gender = rs.getString("gender");
                int phone = rs.getInt("phone");
                clients.add(new User(username, email, birthdate, gender, phone, Role.COACH));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }





}
