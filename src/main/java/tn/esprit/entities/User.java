
package tn.esprit.entities;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;


@Entity
    public class User {


    @Id
    private Long id;
        private String username;
        private Date birthdate;
        private String email;
        private String gender;
        private String password;
        private  String confirmPass;

        private int phone;

        @Enumerated
        private  Role role;
        public User() {
        }

        public User( String username, String email,Date birthdate , String gender,  int phone,String password, String confirmPass, Role role) {
            this.username = username;
            this.email = email;
            this.birthdate = birthdate;
            this.gender = gender;
            this.phone = phone;
            this.password = password;
            this.confirmPass = confirmPass;
            this.role = role;
        }


        public User( String username, String email,Date birthdate, String gender, int phone) {
            this.username = username;
            this.email = email;
            this.birthdate = birthdate;
            this.gender = gender;
            this.phone = phone;
        }

    public User(Long id, String username, Date birthdate, String email, String gender, String password, String confirmPass, int phone, Role role) {
        this.id = id;
        this.username = username;
        this.birthdate = birthdate;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.confirmPass = confirmPass;
        this.phone = phone;
        this.role = role;
    }

    public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Date getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(Date birthdate ) {
            this.birthdate =birthdate;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirmPass() {
            return confirmPass;
        }

        public void setConfirmPass(String confirmPass) {
            this.confirmPass = confirmPass;
        }

        public int getPhone() {
            return phone;
        }

        public void setPhone(int phone) {
            this.phone = phone;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", birthdate=" + birthdate +
                    ", email='" + email + '\'' +
                    ", gender='" + gender + '\'' +
                    ", password='" + password + '\'' +
                    ",confirmPass='" + confirmPass + '\'' +
                    ", phone=" + phone +
                    ", role=" + role +
                    '}';
        }




    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}


