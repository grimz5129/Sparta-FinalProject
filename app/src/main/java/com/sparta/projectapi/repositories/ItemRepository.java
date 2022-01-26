package com.sparta.projectapi.repositories;

import com.sparta.projectapi.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}