package com.sparta.projectapi.repositories;

import com.sparta.projectapi.entities.Itemtype;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<Itemtype, Integer> {
}