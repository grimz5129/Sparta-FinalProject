package com.sparta.projectapi.repositories;

import com.sparta.projectapi.entities.ListRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRowRepository extends JpaRepository<ListRow, Integer> {
}