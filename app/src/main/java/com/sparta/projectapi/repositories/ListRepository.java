package com.sparta.projectapi.repositories;

import com.sparta.projectapi.entities.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Integer> {
}