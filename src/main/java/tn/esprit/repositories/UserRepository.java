package tn.esprit.repositories;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.entities.User;

import java.util.List;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT u.role FROM User u")
    List<String> findAllRoles();
}
