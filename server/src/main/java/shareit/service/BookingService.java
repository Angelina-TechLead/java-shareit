package shareit.service;

import shareit.dto.BookingDto;
import shareit.model.Booking;

import java.util.List;

public interface BookingService {
    Booking create(BookingDto bookingDto, Long userId);
    Booking update(Long id, Long userId, boolean approved);
    void delete(Long id);
    Booking getById(Long id, Long userId);
    List<Booking> getAll(Long userId);
    List<BookingDto> getOwnerBookings(Long ownerId, String state);
}
