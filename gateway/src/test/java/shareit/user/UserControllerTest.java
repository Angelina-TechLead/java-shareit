package shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import shareit.user.dto.UserRequestDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserClient userClient;

    @InjectMocks
    private UserController userController;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setUp() {
        userRequestDto = UserRequestDto.builder()
                .name("Test User")
                .email("test@test.com")
                .build();
    }

    @Test
    void createUser_ShouldCallClient() {
        when(userClient.createUser(any())).thenReturn(ResponseEntity.ok().build());
        userController.createUser(userRequestDto);
        verify(userClient).createUser(userRequestDto);
    }

    @Test
    void updateUser_ShouldCallClient() {
        Long userId = 1L;
        when(userClient.updateUser(anyLong(), any())).thenReturn(ResponseEntity.ok().build());
        userController.updateUser(userId, userRequestDto);
        verify(userClient).updateUser(userId, userRequestDto);
    }

    @Test
    void getUser_ShouldCallClient() {
        Long userId = 1L;
        when(userClient.getUser(anyLong())).thenReturn(ResponseEntity.ok().build());
        userController.getUser(userId);
        verify(userClient).getUser(userId);
    }

    @Test
    void deleteUser_ShouldCallClient() {
        Long userId = 1L;
        when(userClient.deleteUser(anyLong())).thenReturn(ResponseEntity.ok().build());
        userController.deleteUser(userId);
        verify(userClient).deleteUser(userId);
    }
} 