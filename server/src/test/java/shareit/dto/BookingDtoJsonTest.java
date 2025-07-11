package shareit.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.context.annotation.Import;
import shareit.config.JacksonConfig;
import shareit.model.BookingStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@Import(JacksonConfig.class)
class BookingDtoJsonTest {

    @Autowired
    private JacksonTester<BookingDto> json;

    private LocalDateTime start;
    private LocalDateTime end;
    private BookingDto bookingDto;

    @BeforeEach
    void setUp() {
        start = LocalDateTime.of(2024, 3, 20, 12, 0);
        end = LocalDateTime.of(2024, 3, 21, 12, 0);

        bookingDto = new BookingDto(
                1L,
                start,
                end,
                2L,
                3L,
                BookingStatus.WAITING
        );
    }

    @Test
    void testBookingDtoSerialization() throws Exception {
        JsonContent<BookingDto> result = json.write(bookingDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo("2024-03-20T12:00");
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo("2024-03-21T12:00");
        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(2);
        assertThat(result).extractingJsonPathNumberValue("$.bookerId").isEqualTo(3);
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo("WAITING");
    }

    @Test
    void testBookingDtoDeserialization() throws Exception {
        String jsonContent = "{" +
                "\"id\": 1," +
                "\"start\": \"2024-03-20T12:00\"," +
                "\"end\": \"2024-03-21T12:00\"," +
                "\"itemId\": 2," +
                "\"bookerId\": 3," +
                "\"status\": \"WAITING\"" +
                "}";

        BookingDto result = json.parse(jsonContent).getObject();

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStart()).isEqualTo(start);
        assertThat(result.getEnd()).isEqualTo(end);
        assertThat(result.getItemId()).isEqualTo(2L);
        assertThat(result.getBookerId()).isEqualTo(3L);
        assertThat(result.getStatus()).isEqualTo(BookingStatus.WAITING);
    }

    @Test
    void testBookingDtoValidation() throws Exception {
        String jsonContent = "{" +
                "\"id\": 1," +
                "\"start\": \"invalid-date\"," +
                "\"end\": \"2024-03-21T12:00\"," +
                "\"itemId\": 2," +
                "\"bookerId\": 3," +
                "\"status\": \"WAITING\"" +
                "}";

        try {
            json.parse(jsonContent);
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("invalid-date");
        }
    }
} 