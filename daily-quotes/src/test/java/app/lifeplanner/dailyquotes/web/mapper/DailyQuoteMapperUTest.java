package app.lifeplanner.dailyquotes.web.mapper;

import app.lifeplanner.dailyquotes.model.DailyQuote;
import app.lifeplanner.dailyquotes.web.dto.AddDailyQuoteRequest;
import app.lifeplanner.dailyquotes.web.dto.EditDailyQuotesRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DailyQuoteMapperUTest {

    @Test
    void givenDailyQuoteEntity_whenFromEntity_thenReturnAddDailyQuoteRequest() {
        // Given
        UUID id = UUID.randomUUID();
        String quoteImage = "sampleImage.png";
        UUID userId = UUID.randomUUID();
        DailyQuote entity = DailyQuote.builder()
                .id(id)
                .quoteImage(quoteImage)
                .userId(userId)
                .build();

        // When
        AddDailyQuoteRequest dto = DailyQuoteMapper.fromEntity(entity);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.getId());
        assertEquals(quoteImage, dto.getQuoteImage());
        assertEquals(userId, dto.getUserId());
    }

    @Test
    void givenAddDailyQuoteRequest_whenToEntity_thenReturnDailyQuote() {
        // Given
        UUID id = UUID.randomUUID();
        String quoteImage = "sampleImage.png";
        UUID userId = UUID.randomUUID();
        AddDailyQuoteRequest dto = AddDailyQuoteRequest.builder()
                .id(id)
                .quoteImage(quoteImage)
                .userId(userId)
                .build();

        // When
        DailyQuote entity = DailyQuoteMapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(quoteImage, entity.getQuoteImage());
        assertEquals(userId, entity.getUserId());
    }

    @Test
    void givenNullAddDailyQuoteRequest_whenToEntity_thenReturnNull() {
        // When & Then
        assertNull(DailyQuoteMapper.toEntity((AddDailyQuoteRequest) null));
    }

    @Test
    void givenDailyQuoteEntity_whenFromEntityToEdit_thenReturnEditDailyQuotesRequest() {
        // Given
        UUID id = UUID.randomUUID();
        String quoteImage = "sampleImage.png";
        UUID userId = UUID.randomUUID();
        DailyQuote entity = DailyQuote.builder()
                .id(id)
                .quoteImage(quoteImage)
                .userId(userId)
                .build();

        // When
        EditDailyQuotesRequest editDto = DailyQuoteMapper.fromEntityToEdit(entity);

        // Then
        assertNotNull(editDto);
        assertEquals(id, editDto.getId());
        assertEquals(quoteImage, editDto.getQuoteImage());
        assertEquals(userId, editDto.getUserId());
    }

    @Test
    void givenEditDailyQuotesRequest_whenToEntity_thenReturnDailyQuote() {
        // Given
        UUID id = UUID.randomUUID();
        String quoteImage = "sampleImage.png";
        UUID userId = UUID.randomUUID();
        EditDailyQuotesRequest editDto = EditDailyQuotesRequest.builder()
                .id(id)
                .quoteImage(quoteImage)
                .userId(userId)
                .build();

        // When
        DailyQuote entity = DailyQuoteMapper.toEntity(editDto);

        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(quoteImage, entity.getQuoteImage());
        assertEquals(userId, entity.getUserId());
    }

    @Test
    void givenNullEditDailyQuotesRequest_whenToEntity_thenReturnNull() {
        // When & Then
        assertNull(DailyQuoteMapper.toEntity((EditDailyQuotesRequest) null));
    }
}