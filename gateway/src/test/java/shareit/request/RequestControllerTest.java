package shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import shareit.request.dto.RequestDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestControllerTest {

    @Mock
    private RequestClient requestClient;

    @InjectMocks
    private RequestController requestController;

    private RequestDto requestDto;
    private static final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        requestDto = RequestDto.builder()
                .description("Test Description")
                .build();
    }

    @Test
    void createRequest_ShouldCallClient() {
        when(requestClient.createRequest(anyLong(), any())).thenReturn(ResponseEntity.ok().build());
        requestController.createRequest(USER_ID, requestDto);
        verify(requestClient).createRequest(USER_ID, requestDto);
    }

    @Test
    void getRequest_ShouldCallClient() {
        Long requestId = 1L;
        when(requestClient.getRequest(anyLong(), anyLong())).thenReturn(ResponseEntity.ok().build());
        requestController.getRequest(requestId, USER_ID);
        verify(requestClient).getRequest(requestId, USER_ID);
    }

    @Test
    void getRequests_ShouldCallClient() {
        when(requestClient.getRequests(anyLong())).thenReturn(ResponseEntity.ok().build());
        requestController.getRequests(USER_ID);
        verify(requestClient).getRequests(USER_ID);
    }
} 