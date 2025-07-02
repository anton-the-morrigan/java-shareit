package ru.practicum.shareit.booking;

import java.util.Collection;

public interface BookingService {
    public Booking createBooking(Long userId, BookingDto bookingDto);

    public Booking updateBookingStatus(Long userId,Long id, Boolean approved);

    public Booking showBooking(Long userId,Long id);

    public Collection<Booking> showBookingsByBooker(Long userId, String state);

    public Collection<Booking> showBookingsByOwner(Long userId, String state);
}
