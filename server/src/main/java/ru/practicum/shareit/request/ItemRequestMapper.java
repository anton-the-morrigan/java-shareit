package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.UserRepository;

import java.util.Collection;

@RequiredArgsConstructor
@Component
public class ItemRequestMapper {
    private final UserRepository userRepository;

    public ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return new ItemRequestDto(itemRequest.getDescription());
    }

    public ItemRequest toItemRequest(ItemRequestDto itemRequestDto, Long userId) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(itemRequestDto.getDescription());
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", userId));
        }
        itemRequest.setRequestor(userRepository.findById(userId).get());
        return itemRequest;
    }

    public ItemRequestWithItems toItemRequestWithItems(ItemRequest itemRequest, Collection<Item> items) {
        ItemRequestWithItems itemRequestWithItems = new ItemRequestWithItems();
        itemRequestWithItems.setId(itemRequest.getId());
        itemRequestWithItems.setDescription(itemRequest.getDescription());
        itemRequestWithItems.setRequestor(itemRequest.getRequestor());
        itemRequestWithItems.setItems(items);
        itemRequestWithItems.setCreated(itemRequest.getCreated());
        return itemRequestWithItems;
    }
}
