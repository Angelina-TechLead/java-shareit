package shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import shareit.item.dto.ItemRequestDto;
import shareit.item.dto.CommentRequestDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @Mock
    private ItemClient itemClient;

    @InjectMocks
    private ItemController itemController;

    private ItemRequestDto itemRequestDto;
    private CommentRequestDto commentRequestDto;
    private static final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        itemRequestDto = ItemRequestDto.builder()
                .name("Test Item")
                .description("Test Description")
                .available(true)
                .build();

        commentRequestDto = CommentRequestDto.builder()
                .text("Test Comment")
                .build();
    }

    @Test
    void getAllItems_ShouldCallClient() {
        when(itemClient.getAllItems(anyLong())).thenReturn(ResponseEntity.ok().build());
        itemController.getAllItems(USER_ID);
        verify(itemClient).getAllItems(USER_ID);
    }

    @Test
    void createItem_ShouldCallClient() {
        when(itemClient.createItem(anyLong(), any())).thenReturn(ResponseEntity.ok().build());
        itemController.createItem(USER_ID, itemRequestDto);
        verify(itemClient).createItem(USER_ID, itemRequestDto);
    }

    @Test
    void getItem_ShouldCallClient() {
        Long itemId = 1L;
        when(itemClient.getItem(anyLong())).thenReturn(ResponseEntity.ok().build());
        itemController.getItem(itemId);
        verify(itemClient).getItem(itemId);
    }

    @Test
    void updateItem_ShouldCallClient() {
        Long itemId = 1L;
        when(itemClient.updateItem(anyLong(), anyLong(), any())).thenReturn(ResponseEntity.ok().build());
        itemController.updateItem(itemId, USER_ID, itemRequestDto);
        verify(itemClient).updateItem(itemId, USER_ID, itemRequestDto);
    }

    @Test
    void searchItems_ShouldCallClient() {
        String text = "search";
        when(itemClient.searchItems(anyString())).thenReturn(ResponseEntity.ok().build());
        itemController.searchItems(text);
        verify(itemClient).searchItems(text);
    }

    @Test
    void deleteItem_ShouldCallClient() {
        Long itemId = 1L;
        when(itemClient.deleteItem(anyLong())).thenReturn(ResponseEntity.ok().build());
        itemController.deleteItem(itemId);
        verify(itemClient).deleteItem(itemId);
    }

    @Test
    void postComment_ShouldCallClient() {
        Long itemId = 1L;
        when(itemClient.postComment(anyLong(), anyLong(), any())).thenReturn(ResponseEntity.ok().build());
        itemController.postComment(itemId, USER_ID, commentRequestDto);
        verify(itemClient).postComment(itemId, USER_ID, commentRequestDto);
    }
} 