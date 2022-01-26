package com.sparta.projectapi.repositories;

import com.sparta.projectapi.entities.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Integer> {
    Login getByUsername(String username);
    boolean existsByUsername(String username);
}