package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemClient itemClient;
    private final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader(userIdHeader) Long userId, @RequestBody ItemDto itemDto) {
        return itemClient.createItem(userId, itemDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@RequestHeader(userIdHeader) Long userId, @RequestBody ItemDto itemDto, @PathVariable Long id) {
        return itemClient.updateItem(userId, itemDto, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> showItem(@RequestHeader(userIdHeader) Long userId, @PathVariable Long id) {
        return itemClient.showItem(userId, id);
    }

    @GetMapping
    public ResponseEntity<Object> showAllItems(@RequestHeader(userIdHeader) Long userId) {
        return itemClient.showAllItems(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findItems(@RequestParam String text) {
        return itemClient.findItems(text);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader(userIdHeader) Long userId, @RequestBody Comment comment, @PathVariable Long id) {
        return itemClient.createComment(userId, comment, id);
    }
}