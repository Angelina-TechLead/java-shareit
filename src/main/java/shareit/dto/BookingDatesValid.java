package shareit.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import shareit.validators.BookingDatesValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BookingDatesValidator.class)
@Documented
public @interface BookingDatesValid {
    String message() default "Дата начала должна быть до даты окончания";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
