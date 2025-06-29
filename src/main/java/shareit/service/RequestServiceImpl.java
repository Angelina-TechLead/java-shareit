package shareit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shareit.dto.ItemRequestDto;
import shareit.model.Item;
import shareit.model.ItemRequest;
import shareit.model.User;
import shareit.repository.ItemRepository;
import shareit.repository.RequestRepository;
import shareit.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService{
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto create(ItemRequestDto itemRequestDto, Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        var itemRequest = new ItemRequest(null,
                itemRequestDto.getDescription(),
                owner,
                LocalDateTime.now(),
                null);
        requestRepository.save(itemRequest);

        return ItemRequestDto.toDto(itemRequest);
    }

    @Override
    public void delete(Long id) {
       var request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Запрос не найден!"));

        requestRepository.delete(request);
    }

    @Override
    public List<ItemRequestDto> getAll(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        var requests = requestRepository
                .findAllByRequestorId(userId)
                .or(() -> Optional.of(requestRepository.findAll()))
                .orElseThrow(() -> new RuntimeException("Запрос не найден!"));

        return requests.stream()
                .map(ItemRequestDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto getById(Long itemRequestId, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        ItemRequest request = requestRepository
                .findByIdAndRequestorIdWithItems(itemRequestId, userId)
                .or(() -> requestRepository.findById(itemRequestId))
                .orElseThrow(() -> new RuntimeException("Запрос не найден!"));

        List<Item> items = itemRepository.getAllByRequestId(itemRequestId)
                .orElse(Collections.emptyList());

        request.setItems(items);

        return ItemRequestDto.toDto(request);
    }
}
