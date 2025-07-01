package ru.practicum.shareit.request;

import java.util.Collection;

public interface ItemRequestService {
    ItemRequest createRequest(Long userId, ItemRequestDto itemRequestDto);

    Collection<ItemRequest> showYourRequests(Long userId);

    Collection<ItemRequest> showAllRequests();

    ItemRequestWithItems showRequest(Long id);
}
