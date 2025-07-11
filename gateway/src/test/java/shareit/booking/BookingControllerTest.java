package shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import shareit.booking.dto.BookItemRequestDto;
import shareit.booking.dto.BookingState;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingClient bookingClient;

    @InjectMocks
    private BookingController bookingController;

    private BookItemRequestDto bookingRequestDto;
    private static final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        bookingRequestDto = BookItemRequestDto.builder()
                .itemId(1L)
                .start(now.plusHours(1))
                .end(now.plusDays(1))
                .build();
    }

    @Test
    void bookItem_ShouldCallClient() {
        when(bookingClient.bookItem(anyLong(), any())).thenReturn(ResponseEntity.ok().build());
        bookingController.bookItem(USER_ID, bookingRequestDto);
        verify(bookingClient).bookItem(USER_ID, bookingRequestDto);
    }

    @Test
    void approveBooking_ShouldCallClient() {
        Long bookingId = 1L;
        Boolean approved = true;
        when(bookingClient.approveBooking(anyLong(), anyLong(), any(Boolean.class)))
                .thenReturn(ResponseEntity.ok().build());
        bookingController.approveBooking(USER_ID, bookingId, approved);
        verify(bookingClient).approveBooking(USER_ID, bookingId, approved);
    }

    @Test
    void getBooking_ShouldCallClient() {
        Long bookingId = 1L;
        when(bookingClient.getBooking(anyLong(), anyLong())).thenReturn(ResponseEntity.ok().build());
        bookingController.getBooking(USER_ID, bookingId);
        verify(bookingClient).getBooking(USER_ID, bookingId);
    }

    @Test
    void getBookings_ShouldCallClient() {
        String stateParam = "ALL";
        Integer from = 0;
        Integer size = 10;
        when(bookingClient.getBookings(anyLong(), any(BookingState.class), anyInt(), anyInt()))
                .thenReturn(ResponseEntity.ok().build());
        bookingController.getBookings(USER_ID, stateParam, from, size);
        verify(bookingClient).getBookings(USER_ID, BookingState.ALL, from, size);
    }

    @Test
    void getOwnerBookings_ShouldCallClient() {
        String stateParam = "ALL";
        Integer from = 0;
        Integer size = 10;
        when(bookingClient.getOwnerBookings(anyLong(), any(BookingState.class), anyInt(), anyInt()))
                .thenReturn(ResponseEntity.ok().build());
        bookingController.getOwnerBookings(USER_ID, stateParam, from, size);
        verify(bookingClient).getOwnerBookings(USER_ID, BookingState.ALL, from, size);
    }

    @Test
    void deleteBooking_ShouldCallClient() {
        Long bookingId = 1L;
        when(bookingClient.deleteBooking(anyLong(), anyLong())).thenReturn(ResponseEntity.ok().build());
        bookingController.deleteBooking(USER_ID, bookingId);
        verify(bookingClient).deleteBooking(USER_ID, bookingId);
    }
} 