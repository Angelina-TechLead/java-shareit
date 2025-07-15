package shareit.booking.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@BookingDatesValid
public class BookItemRequestDto {
	private Long id;

	@NotNull
	@FutureOrPresent
	private LocalDateTime start;

	@NotNull
	@Future
	private LocalDateTime end;

	private Long itemId;
	private Long bookerId;
	private BookingState status;
}
