package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping
    public Item createItem(@RequestHeader(userIdHeader) Long userId, @RequestBody ItemDto itemDto) {
        return itemService.createItem(itemDto, userId);
    }

    @PatchMapping("/{id}")
    public Item updateItem(@RequestHeader(userIdHeader) Long userId, @RequestBody ItemDto itemDto, @PathVariable Long id) {
        return itemService.updateItem(userId, itemDto, id);
    }

    @GetMapping("/{id}")
    public Item showItem(@PathVariable Long id) {
        return itemService.showItem(id);
    }

    @GetMapping
    public Collection<Item> showAllItems(@RequestHeader(userIdHeader) Long userId) {
        return itemService.showAllItems(userId);
    }

    @GetMapping("/search")
    public Collection<Item> findItems(@RequestParam String text) {
        if (text.isBlank()) {
            return Collections.emptyList();
        } else {
            return itemService.findItems(text);
        }
    }
}