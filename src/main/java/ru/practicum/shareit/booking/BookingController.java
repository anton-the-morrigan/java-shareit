package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping
    public Booking createBooking(@RequestHeader(userIdHeader) Long userId, @RequestBody BookingDto bookingDto) {
        return bookingService.createBooking(userId, bookingDto);
    }

    @PatchMapping("/{id}")
    public Booking updateBookingStatus(@RequestHeader(userIdHeader) Long userId, @PathVariable Long id, @RequestParam(name = "approved") Boolean approved) {
        return bookingService.updateBookingStatus(userId, id, approved);
    }

    @GetMapping("/{id}")
    public Booking showBooking(@RequestHeader(userIdHeader) Long userId, @PathVariable Long id) {
        return bookingService.showBooking(userId, id);
    }

    @GetMapping
    public Collection<Booking> showBookingsByBooker(@RequestHeader(userIdHeader) Long userId, @RequestParam(required = false, defaultValue = "ALL") String state) {
        return bookingService.showBookingsByBooker(userId, state);
    }

    @GetMapping("/owner")
    public Collection<Booking> showBookingsByOwner(@RequestHeader(userIdHeader) Long userId, @RequestParam(required = false, defaultValue = "ALL") String state) {
        return bookingService.showBookingsByOwner(userId, state);
    }
}
