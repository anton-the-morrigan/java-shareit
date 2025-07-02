package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping
    public Item createItem(@RequestHeader(userIdHeader) Long userId, @RequestBody ItemDto itemDto) {
        return itemService.createItem(userId, itemDto);
    }

    @PatchMapping("/{id}")
    public Item updateItem(@RequestHeader(userIdHeader) Long userId, @RequestBody ItemDto itemDto, @PathVariable Long id) {
        return itemService.updateItem(userId, itemDto, id);
    }

    @GetMapping("/{id}")
    public ItemWithComments showItem(@RequestHeader(userIdHeader) Long userId, @PathVariable Long id) {
        return itemService.showItem(userId, id);
    }

    @GetMapping
    public Collection<Item> showAllItems(@RequestHeader(userIdHeader) Long userId) {
        return itemService.showAllItems(userId);
    }

    @GetMapping("/search")
    public Collection<Item> findItems(@RequestParam String text) {
        return itemService.findItems(text);
    }

    @PostMapping("/{id}/comment")
    public Comment createComment(@RequestHeader(userIdHeader) Long userId, @RequestBody Comment comment, @PathVariable Long id) {
        return itemService.createComment(userId, comment, id);
    }
}