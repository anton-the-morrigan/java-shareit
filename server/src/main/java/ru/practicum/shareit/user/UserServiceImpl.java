package ru.practicum.shareit.user;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.error.exception.ValidationException;

import java.util.Collection;

@Service
@Data
public class UserServiceImpl implements UserService {
    UserMapper userMapper;
    UserRepository repository;

    public UserServiceImpl(UserMapper userMapper, UserRepository repository) {
        this.userMapper = userMapper;
        this.repository = repository;
    }

    @Override
    public User createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        userValidator(user);
        repository.save(user);
        return user;
    }

    @Override
    public User updateUser(UserDto userDto, Long id) {
        User oldUser = repository.findById(id).get();
        User newUser = userMapper.toUser(userDto);
        if (newUser.getName() != null) {
            oldUser.setName(newUser.getName());
        }
        if (newUser.getEmail() != null) {
            oldUser.setEmail(newUser.getEmail());
        }
        userValidator(oldUser);
        repository.save(oldUser);
        return oldUser;
    }

    @Override
    public User showUser(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return repository.findById(id).get();
    }

    @Override
    public Collection<User> showAllUsers() {
        return repository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    private void userValidator(User userForValidation) {
        if (userForValidation.getName().isBlank() || userForValidation.getEmail().isBlank()) {
            throw new ValidationException("Имя пользователя и электронная почта не должны быть пусты");
        }
        if (!userForValidation.getEmail().contains("@")) {
            throw new ValidationException("Не верный формат электронной почты");
        }
    }
}