package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;

@RequiredArgsConstructor
@Component
public class BookingMapper {
    private final ItemRepository itemRepository;

    public BookingDto toBookingDto(Booking booking) {
        return new BookingDto(booking.getItem().getId(), booking.getStart(), booking.getEnd());
    }

    public Booking toBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        if (!itemRepository.existsById(bookingDto.getItemId())) {
            throw new NotFoundException(String.format("Предмет с id %d не найден", bookingDto.getItemId()));
        }
        booking.setItem(itemRepository.findById(bookingDto.getItemId()).get());
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        return booking;
    }
}
