package ru.practicum.shareit.user;

import java.util.Collection;

public interface UserService {
    public User createUser(UserDto userDto);

    public User updateUser(UserDto userDto, Long id);

    public User showUser(Long id);

    public Collection<User> showAllUsers();

    public void deleteUser(Long id);
}