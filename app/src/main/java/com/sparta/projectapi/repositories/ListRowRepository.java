package com.sparta.projectapi.repositories;

import com.sparta.projectapi.entities.Item;
import com.sparta.projectapi.entities.List;
import com.sparta.projectapi.entities.ListRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRowRepository extends JpaRepository<ListRow, Integer> {
    java.util.List<ListRow> getAllByList(List list);
    ListRow getByItem(Item item);
    boolean existsByItem(Item item);
}