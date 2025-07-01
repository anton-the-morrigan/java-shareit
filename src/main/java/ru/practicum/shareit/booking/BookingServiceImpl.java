package ru.practicum.shareit.booking;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.NoPermissionException;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.error.exception.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Service
@Data
public class BookingServiceImpl implements BookingService {
    UserService userService;
    BookingMapper bookingMapper;
    BookingRepository repository;

    public BookingServiceImpl(UserService userService, BookingMapper bookingMapper, BookingRepository repository) {
        this.userService = userService;
        this.bookingMapper = bookingMapper;
        this.repository = repository;
    }

    @Override
    public Booking createBooking(Long userId,BookingDto bookingDto) {
        User user = userService.showUser(userId);
        Booking booking = bookingMapper.toBooking(bookingDto);
        bookingValidator(booking);
        booking.setBooker(user);
        repository.save(booking);
        return booking;
    }

    @Override
    public Booking updateBookingStatus(Long userId,Long id, Boolean approved) {
        Booking booking = repository.findById(id).get();
        if (!Objects.equals(userId, booking.getItem().getOwner().getId())) {
            throw new NoPermissionException("Обновление статуса вещи может быть выполнено только владельцем");
        }
        User user = userService.showUser(userId);
        STATUS status = approved ? STATUS.APPROVED : STATUS.REJECTED;
        booking.setStatus(status);
        repository.save(booking);
        return booking;
    }

    @Override
    public Booking showBooking(Long userId,Long id) {
        User user = userService.showUser(userId);
        if (!repository.existsById(id)) {
            throw new NotFoundException(String.format("Бронирование с id %d не найдено", id));
        }
        return repository.findById(id).get();
    }

    @Override
    public Collection<Booking> showBookingsByBooker(Long userId, String state) {
        User user = userService.showUser(userId);
        Collection<Booking> bookings;
        return switch (state) {
            case "ALL" -> bookings = repository.findAllBookingsByBookerId(userId);
            case "CURRENT" -> bookings = repository.findAllCurrentBookingsByBookerId(userId, LocalDateTime.now());
            case "PAST" -> bookings = repository.findAllPastBookingsByBookerId(userId, LocalDateTime.now());
            case "FUTURE" -> bookings = repository.findAllFutureBookingsByBookerId(userId, LocalDateTime.now());
            case "WAITING" -> bookings = repository.findAllWaitingBookingsByBookerId(userId, LocalDateTime.now());
            case "REJECTED" -> bookings = repository.findAllRejectedBookingsByBookerId(userId);
            default -> throw new IllegalArgumentException("Неизвестный статус");
        };
    }

    @Override
    public Collection<Booking> showBookingsByOwner(Long userId, String state) {
        User user = userService.showUser(userId);
        Collection<Booking> bookings;
        return switch (state) {
            case "ALL" -> bookings = repository.findAllBookingsByOwnerId(userId);
            case "CURRENT" -> bookings = repository.findAllCurrentBookingsByOwnerId(userId, LocalDateTime.now());
            case "PAST" -> bookings = repository.findAllPastBookingsByOwnerId(userId, LocalDateTime.now());
            case "FUTURE" -> bookings = repository.findAllFutureBookingsByOwnerId(userId, LocalDateTime.now());
            case "WAITING" -> bookings = repository.findAllWaitingBookingsByOwnerId(userId, LocalDateTime.now());
            case "REJECTED" -> bookings = repository.findAllRejectedBookingsByOwnerId(userId);
            default -> throw new IllegalArgumentException("Неизвестный статус");
        };
    }

    private void bookingValidator(Booking bookingForValidation) {
        if (bookingForValidation.getItem() == null) {
            throw new NotFoundException("Предмет бронирования должен быть указан");
        }
        if (bookingForValidation.getStart() == null || bookingForValidation.getEnd() == null) {
            throw new ValidationException("Время бронирования должно быть указано");
        }
        if (bookingForValidation.getEnd().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Время окончания не может быть в прошлом");
        }
        if (bookingForValidation.getStart().isAfter(bookingForValidation.getEnd()) || bookingForValidation.getStart().isEqual(bookingForValidation.getEnd())) {
            throw new ValidationException("Дата окончания не может быть раньше даты начала");
        }
        if (bookingForValidation.getItem().getAvailable() == false) {
            throw new ValidationException("Этот предмет недоступен");
        }
    }
}
