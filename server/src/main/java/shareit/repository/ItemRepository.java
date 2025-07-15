package shareit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shareit.model.Item;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwnerId(Long ownerId);
    Optional<List<Item>> getAllByRequestId(Long requestId);
    List<Item> findByAvailableTrueAndNameContainingIgnoreCaseOrAvailableTrueAndDescriptionContainingIgnoreCase(String text, String text1);
}
