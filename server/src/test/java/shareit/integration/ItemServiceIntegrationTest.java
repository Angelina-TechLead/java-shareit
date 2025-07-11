package shareit.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shareit.dto.ItemDto;
import shareit.model.User;
import shareit.repository.ItemRepository;
import shareit.repository.UserRepository;
import shareit.service.ItemService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private ItemDto testItemDto;

    @BeforeEach
    void setUp() {
        // Create test user
        testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("test@test.com");
        userRepository.save(testUser);

        // Create test item DTO
        testItemDto = new ItemDto();
        testItemDto.setName("Test Item");
        testItemDto.setDescription("Test Description");
        testItemDto.setAvailable(true);
    }

    @Test
    void createItem_Success() {
        // When
        ItemDto createdItem = itemService.create(testItemDto, testUser.getId());

        // Then
        assertNotNull(createdItem.getId());
        assertEquals(testItemDto.getName(), createdItem.getName());
        assertEquals(testItemDto.getDescription(), createdItem.getDescription());
        assertEquals(testItemDto.getAvailable(), createdItem.getAvailable());

        // Verify item is in database
        assertTrue(itemRepository.findById(createdItem.getId()).isPresent());
    }

    @Test
    void getAllUserItems_Success() {
        // Given
        ItemDto item1 = itemService.create(testItemDto, testUser.getId());
        
        testItemDto.setName("Second Item");
        ItemDto item2 = itemService.create(testItemDto, testUser.getId());

        // When
        List<ItemDto> userItems = itemService.getAll(testUser.getId());

        // Then
        assertEquals(2, userItems.size());
        assertTrue(userItems.stream().anyMatch(item -> item.getName().equals("Test Item")));
        assertTrue(userItems.stream().anyMatch(item -> item.getName().equals("Second Item")));
    }

    @Test
    void updateItem_Success() {
        // Given
        ItemDto createdItem = itemService.create(testItemDto, testUser.getId());
        
        // When
        createdItem.setName("Updated Name");
        createdItem.setDescription("Updated Description");
        ItemDto updatedItem = itemService.update(createdItem.getId(), createdItem, testUser.getId());

        // Then
        assertEquals("Updated Name", updatedItem.getName());
        assertEquals("Updated Description", updatedItem.getDescription());
        
        // Verify changes in database
        var itemFromDb = itemRepository.findById(updatedItem.getId()).orElseThrow();
        assertEquals("Updated Name", itemFromDb.getName());
        assertEquals("Updated Description", itemFromDb.getDescription());
    }

    @Test
    void searchItems_Success() {
        // Given
        itemService.create(testItemDto, testUser.getId());
        
        testItemDto.setName("Another Item");
        testItemDto.setDescription("Special Description");
        itemService.create(testItemDto, testUser.getId());

        // When
        List<ItemDto> searchResults = itemService.search("Special");

        // Then
        assertEquals(1, searchResults.size());
        assertEquals("Another Item", searchResults.get(0).getName());
        assertEquals("Special Description", searchResults.get(0).getDescription());
    }
} 