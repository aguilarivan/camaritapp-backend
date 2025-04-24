package com.gaulab.weshoot.repository;

import com.gaulab.weshoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods can be defined here if needed
    Optional<User> findByUsername(String username);
}
