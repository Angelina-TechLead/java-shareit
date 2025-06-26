package shareit.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import shareit.dto.BookingDatesValid;
import shareit.dto.BookingDto;

public class BookingDatesValidator implements ConstraintValidator<BookingDatesValid, BookingDto> {

    @Override
    public boolean isValid(BookingDto bookingDto, ConstraintValidatorContext context) {
        if (bookingDto.getStart() == null || bookingDto.getEnd() == null) return true;
        return bookingDto.getStart().isBefore(bookingDto.getEnd());
    }
}
