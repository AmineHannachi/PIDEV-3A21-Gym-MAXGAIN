package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Role;
import tn.esprit.entities.User;
import tn.esprit.utilis.DataSource;

import java.sql.*;
import java.util.Date;

public class CoachService {

    private UserService userService;
    private Connection conn;

    public CoachService() {
        this.userService = new UserService();
        this.conn = DataSource.getInstance().getCnx();
    }

//    public void addCoach(String username, String email, Date birthdate, String gender, int phone, String password, String confirmPass) {
//        User coach = new User(username, email, birthdate, gender, phone, password, confirmPass, Role.COACH);
//        userService.add(coach);
//    }




//    public User getCoachByUsername(String username) {
//        String query = "SELECT * FROM user WHERE username = ? AND role = ?";
//        try (PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, username);
//            stmt.setString(2, Role.COACH.name());
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                int id = rs.getInt("id");
//                String email = rs.getString("email");
//                Date birthdate = rs.getDate("birthdate");
//                String gender = rs.getString("gender");
//                int phone = rs.getInt("phone");
//                String password = rs.getString("password");
//                String confirmPass = rs.getString("confirmPass");
//                return new User( username, email, birthdate, gender, phone, password, confirmPass, Role.COACH);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public ObservableList<User> getAllCoach() {
//        ObservableList<User> coaches = FXCollections.observableArrayList();
//        String query = "SELECT * FROM user WHERE role = ?";
//        try (PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, Role.COACH.name());
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    String username = rs.getString("username");
//                    String email = rs.getString("email");
//                    Date birthdate = rs.getDate("birthdate");
//                    String gender = rs.getString("gender");
//                    int phone = rs.getInt("phone");
//                    String password = rs.getString("password");
//                    String confirmPass = rs.getString("confirmPass");
//                    User coach = new User(username, email, birthdate, gender, phone, password, confirmPass, Role.COACH);
//                    coaches.add(coach);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return coaches;
//    }

}
