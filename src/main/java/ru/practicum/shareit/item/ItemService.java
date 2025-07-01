package ru.practicum.shareit.item;

import java.util.Collection;

public interface ItemService {
    public Item createItem(ItemDto itemDto, Long userId);

    public Item updateItem(Long userId, ItemDto itemDto, Long id);

    public ItemWithComments showItem(Long userId, Long id);

    public Collection<Item> showAllItems(Long userId);

    public Collection<Item> findItems(String text);

    public Comment createComment(Long userId, Comment comment, Long id);
}