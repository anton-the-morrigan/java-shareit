package ru.practicum.shareit.item;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.error.exception.NoPermissionException;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.error.exception.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Data
public class ItemServiceImpl implements ItemService {
    UserService userService;
    ItemMapper itemMapper;
    ItemRepository repository;
    CommentRepository commentRepository;
    BookingRepository bookingRepository;

    public ItemServiceImpl(UserService userService, ItemMapper itemMapper, ItemRepository repository, CommentRepository commentRepository, BookingRepository bookingRepository) {
        this.userService = userService;
        this.itemMapper = itemMapper;
        this.repository = repository;
        this.commentRepository = commentRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Item createItem(ItemDto itemDto, Long userId) {
        User user = userService.showUser(userId);
        Item item = itemMapper.toItem(itemDto);
        itemValidator(item);
        item.setOwner(user);
        repository.save(item);
        return item;
    }

    @Override
    public Item updateItem(Long userId, ItemDto itemDto, Long id) {
        Item oldItem = repository.findById(id).get();
        if (!userService.showUser(userId).equals(oldItem.getOwner())) {
            throw new NoPermissionException("Только владелец вещи может её редактировать");
        }
        Item newItem = itemMapper.toItem(itemDto);
        if (newItem.getName() != null) {
            oldItem.setName(newItem.getName());
        }
        if (newItem.getDescription() != null) {
            oldItem.setDescription(newItem.getDescription());
        }
        if (newItem.getAvailable() != null) {
            oldItem.setAvailable(newItem.getAvailable());
        }
        itemValidator(oldItem);
        repository.save(oldItem);
        return oldItem;
    }

    @Override
    public ItemWithComments showItem(Long userId, Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(String.format("Предмет с id %d не найден", id));
        }
        Item item = repository.findById(id).get();
        ItemWithComments itemWithComments = itemMapper.toItemWithComments(item);

        Booking lastBooking = null;
        Booking nextBooking = null;

        boolean isOwner = item.getOwner().getId().equals(userId);

        if (isOwner) {
            lastBooking = bookingRepository.findFirstByItemIdAndEndBeforeOrderByEndDesc(id, LocalDateTime.now());
            nextBooking = bookingRepository.findFirstByItemIdAndStartAfterOrderByStartAsc(id, LocalDateTime.now());
        }

        itemWithComments.setLastBooking(lastBooking);
        itemWithComments.setNextBooking(nextBooking);
        Collection<Comment> comments = commentRepository.findAllByItemId(id);
        itemWithComments.setComments(comments);

        return itemWithComments;
    }

    @Override
    public Collection<Item> showAllItems(Long userId) {
        Collection<Item> ownedItems = new ArrayList<>();
        for (Item item : repository.findAll()) {
            if (Objects.equals(item.getOwner().getId(), userId)) {
                ownedItems.add(item);
            }
        }
        return ownedItems;
    }

    @Override
    public Collection<Item> findItems(String text) {
        return repository.search(text);
    }

    @Override
    public Comment createComment(Long userId, Comment comment, Long id) {
        Item item = itemMapper.toSimpleItem(showItem(userId, id));
        comment.setItem(item);
        comment.setAuthor(userService.showUser(userId));
        comment.setAuthorName(userService.showUser(userId).getName());
        boolean hasBooked = bookingRepository.existsByItemIdAndBookerIdAndEndBefore(comment.getItem().getId(), comment.getAuthor().getId(), LocalDateTime.now());
        if (!hasBooked) {
            throw new ValidationException("Только те, кто арендовал предмет, могут оставить отзыв");
        }
        commentRepository.save(comment);
        return comment;
    }

    private void itemValidator(Item itemForValidation) {
        if (itemForValidation.getName().isBlank() || itemForValidation.getDescription().isBlank()) {
            throw new ValidationException("Название предмета и описания не должны быть пусты");
        }
        if (itemForValidation.getAvailable() == null) {
            throw new ValidationException("Доступность вещи должна быть задана");
        }
    }
}