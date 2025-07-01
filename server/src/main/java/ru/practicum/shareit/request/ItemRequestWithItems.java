package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.Instant;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemRequestWithItems {
    Long id;
    String description;
    User requestor;
    Collection<Item> items;
    Instant created;
}
