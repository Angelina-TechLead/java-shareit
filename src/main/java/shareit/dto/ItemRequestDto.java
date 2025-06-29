package shareit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shareit.model.Item;
import shareit.model.ItemRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private Long id;
    private String description;
    private LocalDateTime created;
    private List<ItemDto> items;

    public static ItemRequestDto toDto(ItemRequest itemRequest) {
        var items = Optional.ofNullable(itemRequest.getItems())
                .orElse(List.of())
                .stream()
                .map(ItemMapper::toItemDto)
                .toList();

        return new ItemRequestDto(
                itemRequest.getId(),
                itemRequest.getDescription(),
                itemRequest.getCreated(),
                items
        );
    }
}
