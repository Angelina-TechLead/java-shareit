package shareit.booking.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BookingDatesValidator implements ConstraintValidator<BookingDatesValid, BookItemRequestDto> {

    @Override
    public boolean isValid(BookItemRequestDto bookingDto, ConstraintValidatorContext context) {
        if (bookingDto.getStart() == null || bookingDto.getEnd() == null) return true;
        return bookingDto.getStart().isBefore(bookingDto.getEnd());
    }
}
