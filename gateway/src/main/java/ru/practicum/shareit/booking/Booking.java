package ru.practicum.shareit.booking;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Booking {
    Long id;

    LocalDateTime start;

    LocalDateTime end;

    Item item;

    User booker;

    STATUS status = STATUS.WAITING;
}