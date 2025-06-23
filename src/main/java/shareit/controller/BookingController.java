package shareit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shareit.dto.BookingDto;
import shareit.model.Booking;
import shareit.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public List<Booking> getAll(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId) {
        return bookingService.getAll(userId);
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable Long id,
                                 @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getById(id, userId);
    }


    @GetMapping("/owner")
    public List<BookingDto> getOwnerBookings(@RequestParam(name = "state", defaultValue = "ALL") String state,
                                             @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getOwnerBookings(userId, state);
    }

    @PostMapping
    public Booking create(@Valid @RequestBody BookingDto bookingDto,
                          @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.create(bookingDto, userId);
    }

    @PatchMapping("/{id}")
    public Booking update(@PathVariable Long id,
                          @RequestParam("approved") boolean approved,
                          @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.update(id, userId, approved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookingService.delete(id);
    }
}
