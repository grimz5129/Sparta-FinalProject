package com.sparta.projectapi.repositories;

import com.sparta.projectapi.entities.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Integer> {
    boolean existsByUsername(String username);

    Login getByUsername(String username);
}