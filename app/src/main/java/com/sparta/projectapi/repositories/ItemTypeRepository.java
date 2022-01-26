package com.sparta.projectapi.repositories;

import com.sparta.projectapi.entities.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<ItemType, Integer> {
    boolean existsByTypeDescription(String typeDescription);
}