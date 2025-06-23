package shareit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shareit.dto.CommentDto;
import shareit.dto.CommentMapper;
import shareit.dto.ItemDto;
import shareit.dto.ItemMapper;
import shareit.exception.BadRequestException;
import shareit.exception.NotFoundRequestException;
import shareit.model.Comment;
import shareit.model.Item;
import shareit.model.User;
import shareit.repository.BookingRepository;
import shareit.repository.CommentRepository;
import shareit.repository.ItemRepository;
import shareit.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto create(ItemDto itemDto, Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        Item item = new Item(null, itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable(), owner, null, null);
        itemRepository.save(item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto getById(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Вещь не найдена!"));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getAll(Long userId) {
        if (userId != null) {
            if (userId == 0) { // Если заголовок есть, но значение пустое
                return Collections.emptyList();
            }
            List<Item> items = itemRepository.findByOwnerId(userId);
            return items.stream()
                    .map(ItemMapper::toItemDto)
                    .collect(Collectors.toList());
        }

        List<Item> allItems = itemRepository.findAll(); // Если заголовка нет, вернуть весь список
        return allItems.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto update(Long itemId, ItemDto itemDto, Long userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Вещь не найдена!"));

        if (item.getOwner() == null || !item.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Редактировать может только владелец!");
        }

        if (itemDto.getName() != null && !itemDto.getName().isBlank()) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank()) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }

        itemRepository.save(item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public void delete(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        itemRepository.delete(item);
    }

    @Override
    public List<ItemDto> search(String text) {
        List<Item> items = itemRepository.findByAvailableTrueAndNameContainingIgnoreCaseOrAvailableTrueAndDescriptionContainingIgnoreCase(text, text);
        return items.stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto addComment(Long itemId, Long userId, CommentDto commentDto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundRequestException("Предмет не найден"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundRequestException("Пользователь не найден"));

        boolean hasCompletedBooking = bookingRepository.existsByItemIdAndBookerIdAndEndBefore(
                itemId, userId, LocalDateTime.now());

        if (!hasCompletedBooking) {
            throw new BadRequestException("Пользователь не может оставить комментарий без завершённого бронирования");
        }

        var comment = new Comment(null,
                commentDto.getText(),
                item,
                user,
                LocalDateTime.now());

        commentRepository.save(comment);
        return CommentMapper.toDto(comment);
    }
}
