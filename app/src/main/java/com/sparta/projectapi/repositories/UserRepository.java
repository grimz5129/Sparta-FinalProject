package com.sparta.projectapi.repositories;

import com.sparta.projectapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
}