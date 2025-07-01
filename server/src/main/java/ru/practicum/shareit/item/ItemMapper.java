package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.ItemRequestRepository;

@RequiredArgsConstructor
@Component
public class ItemMapper {
    private final ItemRequestRepository itemRequestRepository;

    public ItemDto toItemDto(Item item) {
        return new ItemDto(item.getName(), item.getDescription(), item.getAvailable(), item.getRequest().getId());
    }

    public Item toItem(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        if (itemDto.requestId != null) {
            item.setRequest(itemRequestRepository.findById(itemDto.requestId).get());
        }
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