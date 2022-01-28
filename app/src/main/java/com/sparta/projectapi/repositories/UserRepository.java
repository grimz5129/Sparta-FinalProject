package com.sparta.projectapi.repositories;

import com.sparta.projectapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}