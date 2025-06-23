package shareit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shareit.model.Booking;
import shareit.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerId(Long userId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.id = :itemId " +
            "AND b.status = 'APPROVED' " +
            "AND b.start < :end " +
            "AND b.end > :start")
    List<Booking> findOverlappingBookings(@Param("itemId") Long itemId,
                                          @Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end);

    List<Booking> findByItemIdInOrderByStartDesc(List<Long> itemIds);
    List<Booking> findByItemIdInAndStatusOrderByStartDesc(List<Long> itemIds, BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.item.id IN :itemIds " +
            "AND b.start <= :now AND b.end >= :now ORDER BY b.start DESC")
    List<Booking> findCurrentForItems(@Param("itemIds") List<Long> itemIds, @Param("now") LocalDateTime now);

    @Query("SELECT b FROM Booking b WHERE b.item.id IN :itemIds " +
            "AND b.end < :now ORDER BY b.start DESC")
    List<Booking> findPastForItems(@Param("itemIds") List<Long> itemIds, @Param("now") LocalDateTime now);

    @Query("SELECT b FROM Booking b WHERE b.item.id IN :itemIds " +
            "AND b.start > :now ORDER BY b.start DESC")
    List<Booking> findFutureForItems(@Param("itemIds") List<Long> itemIds, @Param("now") LocalDateTime now);
    boolean existsByItemIdAndBookerIdAndEndBefore(Long itemId, Long bookerId, LocalDateTime now);
}
