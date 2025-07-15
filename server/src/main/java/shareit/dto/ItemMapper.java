package shareit.dto;

import shareit.model.Item;

import java.util.Collections;
import java.util.Optional;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : null,
                Optional.ofNullable(item.getComments())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(CommentMapper::toDto)
                        .toList(),
                null,
                null
        );
    }
}
