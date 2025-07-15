package shareit.item;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shareit.item.dto.CommentRequestDto;
import shareit.item.dto.ItemRequestDto;
import static shareit.common.Constants.USER_ID_HEADER;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping
    public ResponseEntity<Object> getAllItems(@RequestHeader(value = USER_ID_HEADER, required = false) Long userId) {
        log.info("Get all items, userId={}", userId);
        return itemClient.getAllItems(userId);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader(USER_ID_HEADER) long userId,
            @RequestBody @Valid ItemRequestDto requestDto) {
        log.info("Creating item {}, userId={}", requestDto, userId);
        return itemClient.createItem(userId, requestDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable Long itemId) {
        log.info("Get item {}", itemId);
        return itemClient.getItem(itemId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@PathVariable Long itemId,
                                           @RequestHeader(USER_ID_HEADER) long userId,
                                           @RequestBody ItemRequestDto requestDto) {
        log.info("Updating item {}, itemId={}, userId={}", requestDto, itemId, userId);
        return itemClient.updateItem(itemId, userId, requestDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestParam(required = false) String text) {
        log.info("Search items with text={}", text);
        return itemClient.searchItems(text);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> deleteItem(@PathVariable Long itemId) {
        log.info("Delete item {}", itemId);
        return itemClient.deleteItem(itemId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> postComment(@PathVariable Long itemId,
                                            @RequestHeader(USER_ID_HEADER) long userId,
                                            @RequestBody @Valid CommentRequestDto requestDto) {
        log.info("Creating comment for item {}, userId={}", itemId, userId);
        return itemClient.postComment(itemId, userId, requestDto);
    }
} 
