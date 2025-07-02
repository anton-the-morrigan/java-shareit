package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import java.util.Collection;

@AllArgsConstructor
@Data
public class ItemWithComments {
    Long id;
    String name;
    String description;
    Boolean available;
    User owner;
    ItemRequest request;
    Booking lastBooking;
    Booking nextBooking;
    Collection<Comment> comments;
}
