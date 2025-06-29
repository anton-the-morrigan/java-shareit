package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i " +
            "from Item as i " +
            "where i.available = true and " +
            "(lower(i.name) like lower(concat('%', ?1, '%') ) or " +
            "lower(i.description) like lower(concat('%', ?1, '%') ))")
    Collection<Item> search(String text);
}
