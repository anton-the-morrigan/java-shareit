package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }

    public ResponseEntity<Object> createBooking(Long userId, BookingDto bookingDto) {
        return post("", userId, bookingDto);
    }

    public ResponseEntity<Object> updateBookingStatus(Long userId, Long id, Boolean approved) {
        Map<String, Object> parameters = Map.of("approved", approved);
        return patch("/" + id + "?approved={approved}", userId, parameters, null);
    }

    public ResponseEntity<Object> showBooking(Long userId, Long id) {
        return get("/" + id, userId);
    }

    public ResponseEntity<Object> showBookingsByBooker(Long userId, STATUS status) {
        Map<String, Object> parameters = Map.of("state", status.name());
        return get("", userId, parameters);
    }

    public ResponseEntity<Object> showBookingsByOwner(Long userId, STATUS status) {
        Map<String, Object> parameters = Map.of("state", status.name());
        return get("/owner?state={state}", userId, parameters);
    }
}
