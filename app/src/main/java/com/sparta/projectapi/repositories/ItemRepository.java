package com.sparta.projectapi.repositories;

import com.sparta.projectapi.entities.Item;
import com.sparta.projectapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    boolean existsByItemName(String name);
    Item getByItemName(String name);
}