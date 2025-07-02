package ru.practicum.shareit.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.User;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ItemRequest {
    Long id;

    String description;

    User requestor;

    Instant created = Instant.now();
}
