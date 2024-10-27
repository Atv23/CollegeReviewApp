package com.college.rating.UserService.dao;

import com.college.rating.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    public Optional<User> findByUserId(String userId);

    boolean existsByUserEmail(String userEmail);

    User findByUserEmail(String email);
}
