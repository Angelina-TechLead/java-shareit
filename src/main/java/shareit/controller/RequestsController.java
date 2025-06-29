package shareit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shareit.dto.ItemRequestDto;
import shareit.model.ItemRequest;
import shareit.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestsController {
    private final RequestService requestService;

    @GetMapping
    public List<ItemRequestDto> getRequests(@RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return requestService.getAll(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequests(@PathVariable Long requestId,
                                      @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return requestService.getById(requestId, userId);
    }

    @PostMapping
    public ItemRequestDto createRequests(@Valid @RequestBody ItemRequestDto itemRequestDto,
                                         @RequestHeader("X-Sharer-User-Id") Long userId)
    {
        return requestService.create(itemRequestDto, userId);
    }
}
