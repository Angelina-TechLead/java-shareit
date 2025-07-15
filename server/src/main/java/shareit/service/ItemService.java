package shareit.service;

import shareit.dto.CommentDto;
import shareit.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(ItemDto itemDto, Long userId);
    ItemDto update(Long itemId, ItemDto itemDto, Long userId);
    void delete(Long id);
    ItemDto getById(Long itemId);
    List<ItemDto> getAll(Long userId);
    List<ItemDto> search(String text);
    CommentDto addComment(Long itemId, Long userId, CommentDto commentDto);
}
