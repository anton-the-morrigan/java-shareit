package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemDto toItemDto(Item item) {
        return new ItemDto(item.getName(), item.getDescription(), item.getAvailable(), item.getRequest());
    }

    public Item toItem(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setRequest(itemDto.getRequest());
        return item;
    }

    public ItemWithComments toItemWithComments(Item item) {
        return new ItemWithComments(item.getId(), item.getName(), item.getDescription(), item.getAvailable(), item.getOwner(), item.getRequest(), null, null, null);
    }

    public Item toSimpleItem(ItemWithComments itemWithComments) {
        Item item = new Item();
        item.setId(itemWithComments.getId());
        item.setName(itemWithComments.getName());
        item.setDescription(itemWithComments.getDescription());
        item.setAvailable(itemWithComments.getAvailable());
        item.setOwner(itemWithComments.getOwner());
        item.setRequest(itemWithComments.getRequest());
        return item;
    }
}