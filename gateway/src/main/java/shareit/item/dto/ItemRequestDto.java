package shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shareit.booking.dto.BookItemRequestDto;
import shareit.booking.dto.BookingDatesValid;
import shareit.comment.dto.CommentRequestDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@BookingDatesValid
public class ItemRequestDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;
    private List<CommentRequestDto> comments;
    private BookItemRequestDto lastBooking;
    private BookItemRequestDto nextBooking;
}

