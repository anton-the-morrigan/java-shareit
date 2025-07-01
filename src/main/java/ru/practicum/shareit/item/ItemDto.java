package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDto {
    String name;
    String description;
    Boolean available;
    Long requestId;
}