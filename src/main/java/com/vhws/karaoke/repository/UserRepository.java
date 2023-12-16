package com.vhws.karaoke.repository;


import com.vhws.karaoke.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
    @Query("SELECT u FROM users u WHERE u.login = :login")
    User findByLoginUser(String login);
}