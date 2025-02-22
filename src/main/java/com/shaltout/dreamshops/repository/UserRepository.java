package com.shaltout.dreamshops.repository;

import com.shaltout.dreamshops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String username);
}
