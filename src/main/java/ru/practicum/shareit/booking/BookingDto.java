package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.Item;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class BookingDto {
    Long itemId;
    LocalDateTime start;
    LocalDateTime end;
}
