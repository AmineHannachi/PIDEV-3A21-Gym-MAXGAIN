package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Role;
import tn.esprit.entities.User;
import tn.esprit.utilis.DataSource;

import java.sql.*;
import java.util.Date;

public class ClientService {
    private UserService userService;
    private Connection conn;

    public ClientService() {
        this.userService = new UserService();
        this.conn = DataSource.getInstance().getCnx();
    }
//
//    public void addClient(String username, String email, Date birthdate, String gender, int phone, String password, String confirmPass) {
//        User client = new User(username, email, birthdate, gender, phone, password, confirmPass, Role.CLIENT);
//        userService.add(client);
//    }


    public void deleteClient(String username) {
        userService.delete(username);
    }

    public void updateClient(String oldUsername, String newUsername, String newEmail, Date newBirthdate, String newGender, int newPhone, String newPassword, String newConfirmPass) {
        // Créez un nouvel objet User avec les nouvelles valeurs
        User newUser = new User();
        newUser.setUsername(newUsername);
        newUser.setEmail(newEmail);
        newUser.setBirthdate(newBirthdate);
        newUser.setGender(newGender);
        newUser.setPhone(newPhone);
        newUser.setPassword(newPassword);
        newUser.setConfirmPass(newConfirmPass);

        // Appelez la méthode update de UserService pour mettre à jour l'utilisateur
        userService.update(oldUsername, newUser);
    }




//    public User getClientByUsername(String username) {
//        String query = "SELECT * FROM user WHERE username = ? AND role = ?";
//        try (PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, username);
//            stmt.setString(2, Role.CLIENT.name());
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                String email = rs.getString("email");
//                Date birthdate = rs.getDate("birthdate");
//                String gender = rs.getString("gender");
//                int phone = rs.getInt("phone");
//                String password = rs.getString("password");
//                String confirmPass = rs.getString("confirmPass");
//                return new User(username, email, birthdate, gender, phone, password, confirmPass, Role.CLIENT);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    // Dans votre service ClientService
//    public ObservableList<User> getAllClients() {
//        ObservableList<User> clients = FXCollections.observableArrayList();
//        String query = "SELECT * FROM user WHERE role = ?";
//        try (PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, Role.CLIENT.name());
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                String username = rs.getString("username");
//                String email = rs.getString("email");
//                Date birthdate = rs.getDate("birthdate");
//                String gender = rs.getString("gender");
//                int phone = rs.getInt("phone");
//                String password = rs.getString("password");
//                String confirmPass = rs.getString("confirmPass");
//                clients.add(new User(username, email, birthdate, gender, phone, password, confirmPass, Role.CLIENT));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return clients;
//    }



}
