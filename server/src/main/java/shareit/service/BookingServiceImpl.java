package shareit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shareit.dto.BookingDto;
import shareit.dto.BookingMapper;
import shareit.exception.BadRequestException;
import shareit.exception.NotFoundRequestException;
import shareit.exception.UnauthorizedRequestException;
import shareit.model.Booking;
import shareit.model.BookingState;
import shareit.model.BookingStatus;
import shareit.model.Item;
import shareit.repository.BookingRepository;
import shareit.repository.ItemRepository;
import shareit.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Booking create(BookingDto bookingDto, Long userId) {
        var item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item не найден!"));

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        if (!item.getAvailable()) {
            throw new BadRequestException("Предмет не доступен для бронирования");
        }

        var conflicts = bookingRepository.findOverlappingBookings(
                item.getId(), bookingDto.getStart(), bookingDto.getEnd());

        if (!conflicts.isEmpty()) {
            throw new BadRequestException("Предмет уже забронирован на выбранные даты");
        }

        var booking = new Booking(null,
                bookingDto.getStart(),
                bookingDto.getEnd(),
                item,
                user,
                BookingStatus.WAITING);
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public Booking update(Long id, Long userId, boolean approved) {
        var booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Бронирование не найдено!"));

        userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("Пользователь не найден!"));

        var item = booking.getItem();
        if (!item.getOwner().getId().equals(userId)) {
            throw new UnauthorizedRequestException("Только владелец предмета может подтверждать бронирование");
        }

        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    public void delete(Long id) {
        var booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Бронирование не найдено!"));

        bookingRepository.delete(booking);
    }

    @Override
    public Booking getById(Long id, Long userId) {
        var booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Бронирование не найден!"));

        userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("Пользователь не найден!"));

        return booking;
    }

    @Override
    public List<Booking> getAll(Long userId) {
        if (userId != null) {
            if (userId == 0) { // Если заголовок есть, но значение пустое
                return Collections.emptyList();
            }
            return bookingRepository.findByBookerId(userId);
        }

        return bookingRepository.findAll();
    }

    @Override
    public List<BookingDto> getOwnerBookings(Long ownerId, String stateText) {
        userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundRequestException("Пользователь не найден!"));

        BookingState state;
        try {
            state = BookingState.valueOf(stateText.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Unknown state: " + stateText);
        }

        var ownerItems = itemRepository.findByOwnerId(ownerId);
        if (ownerItems.isEmpty()) return List.of();

        List<Long> itemIds = ownerItems.stream()
                .map(Item::getId)
                .toList();

        LocalDateTime now = LocalDateTime.now();
        List<Booking> bookings;

        switch (state) {
            case CURRENT -> bookings = bookingRepository.findCurrentForItems(itemIds, now);
            case PAST -> bookings = bookingRepository.findPastForItems(itemIds, now);
            case FUTURE -> bookings = bookingRepository.findFutureForItems(itemIds, now);
            case WAITING -> bookings = bookingRepository.findByItemIdInAndStatusOrderByStartDesc(itemIds, BookingStatus.WAITING);
            case REJECTED -> bookings = bookingRepository.findByItemIdInAndStatusOrderByStartDesc(itemIds, BookingStatus.REJECTED);
            case ALL -> bookings = bookingRepository.findByItemIdInOrderByStartDesc(itemIds);
            default -> throw new BadRequestException("Unsupported booking state");
        }

        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .toList();
    }
}
