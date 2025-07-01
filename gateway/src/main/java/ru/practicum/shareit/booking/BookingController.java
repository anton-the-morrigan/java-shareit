package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingClient bookingClient;
    private final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestHeader(userIdHeader) Long userId, @RequestBody BookingDto bookingDto) {
        return bookingClient.createBooking(userId, bookingDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateBookingStatus(@RequestHeader(userIdHeader) Long userId, @PathVariable Long id, @RequestParam(name = "approved") Boolean approved) {
        return bookingClient.updateBookingStatus(userId, id, approved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> showBooking(@RequestHeader(userIdHeader) Long userId, @PathVariable Long id) {
        return bookingClient.showBooking(userId, id);
    }

    @GetMapping
    public ResponseEntity<Object> showBookingsByBooker(@RequestHeader(userIdHeader) Long userId, @RequestParam(required = false, defaultValue = "ALL") String state) {
        STATUS status = STATUS.from(state).get();
        return bookingClient.showBookingsByBooker(userId, status);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> showBookingsByOwner(@RequestHeader(userIdHeader) Long userId, @RequestParam(required = false, defaultValue = "ALL") String state) {
        STATUS status = STATUS.from(state).get();
        return bookingClient.showBookingsByOwner(userId, status);
    }
}
