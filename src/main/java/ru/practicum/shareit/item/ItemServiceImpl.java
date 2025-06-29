package ru.practicum.shareit.item;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.NoPermissionException;
import ru.practicum.shareit.error.exception.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

@Service
@Data
public class ItemServiceImpl implements ItemService {
    UserService userService;
    ItemMapper itemMapper;

    HashMap<Long, Item> items = new HashMap<>();

    public ItemServiceImpl(UserService userService, ItemMapper itemMapper) {
        this.userService = userService;
        this.itemMapper = itemMapper;
    }

    public Item createItem(ItemDto itemDto, Long userId) {
        User user = userService.showUser(userId);
        Item item = itemMapper.toItem(itemDto);
        itemValidator(item);
        item.setId(getNextId());
        item.setOwner(user);
        items.put(item.getId(), item);
        return item;
    }

    public Item updateItem(Long userId, ItemDto itemDto, Long id) {
        Item oldItem = items.get(id);
        if (!userService.showUser(userId).equals(oldItem.getOwner())) {
            throw new NoPermissionException("Только владелец вещи может её редактировать");
        }
        Item newItem = itemMapper.toItem(itemDto);
        if (newItem.getName() != null) {
            oldItem.setName(newItem.getName());
        }
        if (newItem.getDescription() != null) {
            oldItem.setDescription(newItem.getDescription());
        }
        if (newItem.getAvailable() != null) {
            oldItem.setAvailable(newItem.getAvailable());
        }
        itemValidator(oldItem);
        return oldItem;
    }

    public Item showItem(Long id) {
        return items.get(id);
    }

    public Collection<Item> showAllItems(Long userId) {
        Collection<Item> ownedItems = new ArrayList<>();
        for (Item item : items.values()) {
            if (Objects.equals(item.getOwner().getId(), userId)) {
                ownedItems.add(item);
            }
        }
        return ownedItems;
    }

    public Collection<Item> findItems(String text) {
        Collection<Item> foundItems = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.getAvailable() == true && (item.getName().toLowerCase().contains(text.toLowerCase()) || item.getDescription().toLowerCase().contains(text.toLowerCase()))) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    private void itemValidator(Item itemForValidation) {
        if (itemForValidation.getName().isBlank() || itemForValidation.getDescription().isBlank()) {
            throw new ValidationException("Название предмета и описания не должны быть пусты");
        }
        if (itemForValidation.getAvailable() == null) {
            throw new ValidationException("Доступность вещи должна быть задана");
        }
    }

    private long getNextId() {
        long currentMaxId = items.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }
}