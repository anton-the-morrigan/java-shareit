package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }

    public ResponseEntity<Object> createItem(Long userId, ItemDto itemDto) {
        return post("", userId, itemDto);
    }

    public ResponseEntity<Object> updateItem(Long userId, ItemDto itemDto, Long id) {
        return patch("/" + id, userId, itemDto);
    }

    public ResponseEntity<Object> showItem(Long userId, Long id) {
        return get("/" + id, userId);
    }

    public ResponseEntity<Object> showAllItems(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> findItems(String text) {
        return get("/search?text=" + text);
    }

    public ResponseEntity<Object> createComment(Long userId, Comment comment, Long id) {
        return post(String.format("/%d/comment", id), userId, comment);
    }
}
