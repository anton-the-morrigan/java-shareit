package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;
    private final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping
    public ItemRequest createRequest(@RequestHeader(userIdHeader) Long userId, @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestService.createRequest(userId, itemRequestDto);
    }

    @GetMapping
    public Collection<ItemRequest> showYourRequests(@RequestHeader(userIdHeader) Long userId) {
        return itemRequestService.showYourRequests(userId);
    }

    @GetMapping("/all")
    public Collection<ItemRequest> showAllRequests() {
        return itemRequestService.showAllRequests();
    }

    @GetMapping("/{id}")
    public ItemRequestWithItems showRequest(@PathVariable Long id) {
        return itemRequestService.showRequest(id);
    }
}
