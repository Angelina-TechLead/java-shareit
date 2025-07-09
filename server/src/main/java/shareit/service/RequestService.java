package shareit.service;

import shareit.dto.ItemDto;
import shareit.dto.ItemRequestDto;
import shareit.model.ItemRequest;

import java.util.List;

public interface RequestService {
    ItemRequestDto create(ItemRequestDto itemRequestDto, Long userId);
    void delete(Long id);
    List<ItemRequestDto> getAll(Long userId);
    ItemRequestDto getById(Long itemRequestId, Long userId);
}
