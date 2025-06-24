package ru.practicum.shareit.user;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.DuplicatedDataException;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.error.exception.ValidationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

@Service
@Data
public class UserServiceImpl implements UserService {
    UserMapper userMapper;

    HashMap<Long, User> users = new HashMap<>();

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        userValidator(user);
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(UserDto userDto, Long id) {
        User oldUser = users.get(id);
        User newUser = userMapper.toUser(userDto);
        if (newUser.getName() != null) {
            oldUser.setName(newUser.getName());
        }
        if (newUser.getEmail() != null) {
            oldUser.setEmail(newUser.getEmail());
        }
        userValidator(oldUser);
        return oldUser;

    }

    public User showUser(Long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return users.get(id);
    }

    public Collection<User> showAllUsers() {
        return users.values();
    }

    public void deleteUser(Long id) {
        users.remove(id);
    }

    private void userValidator(User userForValidation) {
        if (userForValidation.getName().isBlank() || userForValidation.getEmail().isBlank()) {
            throw new ValidationException("Имя пользователя и электронная почта не должны быть пусты");
        }
        if (!userForValidation.getEmail().contains("@")) {
            throw new ValidationException("Не верный формат электронной почты");
        }
        for (User user : users.values()) {
            if (user.getEmail().equals(userForValidation.getEmail()) && !Objects.equals(user.getId(), userForValidation.getId())) {
                throw new DuplicatedDataException("Данная электронная почта уже зарегестрированна");
            }
        }
    }

    private long getNextId() {
        long currentMaxId = users.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }
}