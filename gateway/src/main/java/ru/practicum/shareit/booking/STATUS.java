package ru.practicum.shareit.booking;

import java.util.Optional;

public enum STATUS {
    ALL, CURRENT, PAST, FUTURE, WAITING, APPROVED, REJECTED, CANCELED;

    public static Optional<STATUS> from(String stringState) {
        for (STATUS state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
