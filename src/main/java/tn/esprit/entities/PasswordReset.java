package tn.esprit.entities;

import java.sql.Timestamp;

public class PasswordReset {
        private int id;
        private String email;
        private String verification_code;
        private Timestamp created_at;

    public PasswordReset() {
    }

    public PasswordReset(int id, String email, String verification_code, Timestamp created_at) {
        this.id = id;
        this.email = email;
        this.verification_code = verification_code;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "PasswordReset{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", verification_code='" + verification_code + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
