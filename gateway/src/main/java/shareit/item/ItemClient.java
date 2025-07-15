package shareit.item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import shareit.client.BaseClient;
import shareit.item.dto.CommentRequestDto;
import shareit.item.dto.ItemRequestDto;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getAllItems(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> createItem(long userId, ItemRequestDto requestDto) {
        return post("", userId, requestDto);
    }

    public ResponseEntity<Object> getItem(Long itemId) {
        return get("/" + itemId);
    }

    public ResponseEntity<Object> updateItem(Long itemId, long userId, ItemRequestDto requestDto) {
        return patch("/" + itemId, userId, requestDto);
    }

    public ResponseEntity<Object> searchItems(String text) {
        if (text == null || text.isBlank()) {
            return get("/search");
        }
        return get("/search?text=" + text);
    }

    public ResponseEntity<Object> deleteItem(Long itemId) {
        return delete("/" + itemId);
    }

    public ResponseEntity<Object> postComment(Long itemId, long userId, CommentRequestDto requestDto) {
        return post("/" + itemId + "/comment", userId, requestDto);
    }
}