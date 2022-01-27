package com.sparta.projectapi.repositories;

import com.sparta.projectapi.entities.ListRow;
import com.sparta.projectapi.entities.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRowRepository extends JpaRepository<ListRow, Integer> {
    java.util.List<ListRow> getAllByList(List list);
}