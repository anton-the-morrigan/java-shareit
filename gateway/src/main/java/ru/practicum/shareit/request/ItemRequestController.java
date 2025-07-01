package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;
    private final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader(userIdHeader) Long userId, @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestClient.createRequest(userId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> showYourRequests(@RequestHeader(userIdHeader) Long userId) {
        return itemRequestClient.showYourRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> showAllRequests() {
        return itemRequestClient.showAllRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> showRequest(@PathVariable Long id) {
        return itemRequestClient.showRequest(id);
    }
}
