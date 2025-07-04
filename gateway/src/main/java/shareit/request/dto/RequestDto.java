package shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shareit.booking.dto.BookingDatesValid;
import shareit.item.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@BookingDatesValid
public class RequestDto {
    private Long id;
    private String description;
    private LocalDateTime created;
    private List<ItemRequestDto> items;
}
