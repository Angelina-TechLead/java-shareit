package shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shareit.booking.dto.BookingDatesValid;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@BookingDatesValid
public class UserRequestDto {
    private Long id;
    private String name;
    private String email;
}
