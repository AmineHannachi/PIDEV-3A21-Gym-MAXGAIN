package tn.esprit.entities;
import javax.persistence.*;
import java.util.Date;

@Entity
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Date birthdate;
    private String email;
    private String gender;
    private String confirmPass;
    private int phone;
    private byte[] salt;

    public User() {
    }
    public User( String username,String email,  Date birthdate,  String gender,  int phone , Role role) {
        this.username = username;
        this.role = role;
        this.birthdate = birthdate;
        this.email = email;
        this.gender = gender;
        this.phone = phone;

    }
    public User(Long id, String username, String password, Role role, Date birthdate, String email, String gender, String confirmPass, int phone, byte[] salt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.birthdate = birthdate;
        this.email = email;
        this.gender = gender;
        this.confirmPass = confirmPass;
        this.phone = phone;
        this.salt = salt;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
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






    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", birthdate=" + birthdate +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", confirmPass='" + confirmPass + '\'' +
                ", phone=" + phone +
                '}';
    }
}
