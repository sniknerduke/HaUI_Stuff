package com.qlsv.demo.repository;

import com.qlsv.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndIsDeletedFalse(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
