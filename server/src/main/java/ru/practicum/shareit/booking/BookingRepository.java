package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingRepository  extends JpaRepository<Booking, Long> {
    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE b.booker_id = ?1 " +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Collection<Booking> findAllBookingsByBookerId(Long userId);

    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE b.booker_id = ?1 " +
            "AND ?2 BETWEEN b.start_date AND b.end_date " +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Collection<Booking> findAllCurrentBookingsByBookerId(Long bookerId, LocalDateTime currentTime);

    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE b.booker_id = ?1 " +
            "AND b.end_date < ?2 " +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Collection<Booking> findAllPastBookingsByBookerId(Long bookerId, LocalDateTime currentTime);

    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE b.booker_id = ?1 " +
            "AND b.start_date > ?2 " +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Collection<Booking> findAllFutureBookingsByBookerId(Long bookerId, LocalDateTime currentTime);

    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE b.booker_id = ?1 " +
            "AND b.status = 'WAITING' " +
            "AND b.start_date > ?2 " +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Collection<Booking> findAllWaitingBookingsByBookerId(Long bookerId, LocalDateTime currentTime);

    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE b.booker_id = ?1 " +
            "AND b.status = 'REJECTED' " +
            "AND b.start_date > ?2 " +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Collection<Booking> findAllRejectedBookingsByBookerId(Long bookerId);

    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id  " +
            "WHERE i.owner_id = ?1 " +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Collection<Booking> findAllBookingsByOwnerId(Long ownerId);

    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE i.owner_id = ?1 " +
            "AND ?2 BETWEEN b.start_date AND b.end_date " +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Collection<Booking> findAllCurrentBookingsByOwnerId(Long ownerId, LocalDateTime currentTime);

    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE i.owner_id = ?1 " +
            "AND b.end_date < ?2 " +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Collection<Booking> findAllPastBookingsByOwnerId(Long ownerId, LocalDateTime currentTime);

    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE i.owner_id = ?1 " +
            "AND b.start_date > ?2 " +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Collection<Booking> findAllFutureBookingsByOwnerId(Long ownerId, LocalDateTime currentTime);

    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE i.owner_id = ?1 " +
            "AND b.status = 'WAITING' " +
            "AND b.start_date > ?2 " +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Collection<Booking> findAllWaitingBookingsByOwnerId(Long ownerId, LocalDateTime currentTime);

    @Query(value = "SELECT b.* FROM bookings as b " +
            "JOIN items as i ON i.id = b.item_id " +
            "WHERE i.owner_id = ?1 " +
            "AND b.status = 'REJECTED' " +
            "ORDER BY b.start_date DESC", nativeQuery = true)
    Collection<Booking> findAllRejectedBookingsByOwnerId(Long ownerId);

    Boolean existsByItemIdAndBookerIdAndEndBefore(Long itemId, Long bookerId, LocalDateTime time);

    Booking findFirstByItemIdAndEndBeforeOrderByEndDesc(Long itemId, LocalDateTime end);

    Booking findFirstByItemIdAndStartAfterOrderByStartAsc(Long itemId, LocalDateTime start);
}
