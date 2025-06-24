package ru.practicum.shareit.item;

import java.util.Collection;

public interface ItemService {
    public Item createItem(ItemDto itemDto, Long userId);

    public Item updateItem(Long userId, ItemDto itemDto, Long id);

    public Item showItem(Long id);

    public Collection<Item> showAllItems(Long userId);

    public Collection<Item> findItems(String text);
}