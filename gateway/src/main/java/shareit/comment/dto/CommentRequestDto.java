package shareit.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shareit.booking.dto.BookingDatesValid;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@BookingDatesValid
public class CommentRequestDto {
    private Long id;

    @NotBlank(message = "Комментарий не может быть пустым")
    private String text;

    private String authorName;
    private LocalDateTime created;
}
