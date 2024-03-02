
package tn.esprit.entities;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

    public class User {

        private int id;
        private String username;
        private String nom;
        private String prenom;
        private int age;
        private String email;
        private String password;

        private int telephone;
        @Enumerated(EnumType.STRING)
        private  Role role;
        public User() {
        }

        public User( String username, String nom, String prenom, int age, String email, String password, int telephone) {
            this.username = username;
            this.nom = nom;
            this.prenom = prenom;
            this.age = age;
            this.email = email;
            this.password = password;
            this.telephone = telephone;

        }

        public User(int id, String username, String name, String prenom, int age, String email, String password, int telephone, Role role) {
            this.id = id;
            this.username = username;
            this.nom = nom;
            this.prenom = prenom;
            this.age = age;
            this.email = email;
            this.password = password;
            this.telephone = telephone;
            this.role = role;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return nom;
        }

        public void setName(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getTelephone() {
            return telephone;
        }

        public void setTelephone(int telephone) {
            this.telephone = telephone;
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
                    ", name='" + nom + '\'' +
                    ", prenom='" + prenom + '\'' +
                    ", age=" + age +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", telephone=" + telephone +
                    ", role=" + role +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            tn.esprit.entities.User user = (tn.esprit.entities.User) o;
            return id == user.id && age == user.age && telephone == user.telephone && Objects.equals(username, user.username) && Objects.equals(nom, user.nom) && Objects.equals(prenom, user.prenom) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, username, nom, prenom, age, email, password, telephone, role);
        }
    }


