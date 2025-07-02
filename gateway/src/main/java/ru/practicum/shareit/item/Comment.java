package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.User;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Comment {
    Long id;

    String text;

    Item item;

    User author;

    String authorName;

    Instant created = Instant.now();
}
