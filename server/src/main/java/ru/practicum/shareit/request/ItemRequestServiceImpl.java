package ru.practicum.shareit.request;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;

import java.util.Collection;

@Service
@Data
public class ItemRequestServiceImpl implements ItemRequestService {
    ItemRequestMapper itemRequestMapper;
    ItemRequestRepository repository;
    ItemRepository itemRepository;

    public ItemRequestServiceImpl(ItemRequestMapper itemRequestMapper, ItemRequestRepository repository, ItemRepository itemRepository) {
        this.itemRequestMapper = itemRequestMapper;
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemRequest createRequest(Long userId, ItemRequestDto itemRequestDto) {
        ItemRequest itemRequest = itemRequestMapper.toItemRequest(itemRequestDto, userId);
        repository.save(itemRequest);
        return itemRequest;
    }

    @Override
    public Collection<ItemRequest> showYourRequests(Long userId) {
        return repository.findByRequestorIdOrderByCreatedDesc(userId);
    }

    @Override
    public Collection<ItemRequest> showAllRequests() {
        return repository.findAllByOrderByCreatedDesc();
    }

    @Override
    public ItemRequestWithItems showRequest(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(String.format("Запрос с id %d не найден", id));
        }
        ItemRequest itemRequest = repository.findById(id).get();
        Collection<Item> items = itemRepository.findByRequestId(id);
        return itemRequestMapper.toItemRequestWithItems(itemRequest, items);
    }
}
