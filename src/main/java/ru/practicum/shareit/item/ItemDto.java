package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;

@AllArgsConstructor
@Data
public class ItemDto {
    String name;
    String description;
    Boolean available;
    ItemRequest request;
}