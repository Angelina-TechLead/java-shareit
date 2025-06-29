package shareit.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shareit.model.ItemRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<ItemRequest, Long> {
    Optional<List<ItemRequest>> findAllByRequestorId(Long requestorId);

    @EntityGraph(attributePaths = "items")
    Optional<ItemRequest> findById(Long id);

    @Query(value = """
        SELECT ir.* 
        FROM item_requests ir
        WHERE ir.id = :requestId AND ir.requestor_id = :requestorId
    """, nativeQuery = true)
    Optional<ItemRequest> findByIdAndRequestorIdWithItems(@Param("requestId") Long requestId,
                                                          @Param("requestorId") Long requestorId);
}
