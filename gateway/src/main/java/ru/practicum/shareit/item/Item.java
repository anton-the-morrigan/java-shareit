package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Item {
    Long id;

    String name;

    String description;

    Boolean available;

    User owner;

    ItemRequest request;
}