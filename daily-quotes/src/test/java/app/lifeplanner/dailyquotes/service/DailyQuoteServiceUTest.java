package app.lifeplanner.dailyquotes.service;

import app.lifeplanner.dailyquotes.exception.EntityNotFoundException;
import app.lifeplanner.dailyquotes.model.DailyQuote;
import app.lifeplanner.dailyquotes.repository.DailyQuoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DailyQuoteServiceUTest {

    @Mock
    private DailyQuoteRepository dailyQuoteRepository;

    @InjectMocks
    private DailyQuoteService dailyQuoteService;

    @Test
    void givenDailyQuote_whenAddDailyQuote_thenReturnSavedQuote() {
        // Given
        DailyQuote quote = new DailyQuote();
        when(dailyQuoteRepository.save(quote)).thenReturn(quote);

        // When
        DailyQuote result = dailyQuoteService.addDailyQuote(quote);

        // Then
        assertSame(quote, result, "The returned quote should be the same instance as saved");
        verify(dailyQuoteRepository, times(1)).save(quote);
    }

    @Test
    void givenUserId_whenGetQuotesByUserId_thenReturnQuotesList() {
        // Given
        UUID userId = UUID.randomUUID();
        List<DailyQuote> expectedList = Arrays.asList(new DailyQuote(), new DailyQuote());
        when(dailyQuoteRepository.findByUserId(userId)).thenReturn(expectedList);

        // When
        List<DailyQuote> result = dailyQuoteService.getQuotesByUserId(userId);

        // Then
        assertEquals(expectedList, result, "Returned list should match expected");
        verify(dailyQuoteRepository, times(1)).findByUserId(userId);
    }

    @Test
    void givenExistingQuoteId_whenGetQuoteById_thenReturnQuote() {
        // Given
        UUID quoteId = UUID.randomUUID();
        DailyQuote quote = new DailyQuote();
        quote.setId(quoteId);
        when(dailyQuoteRepository.findById(quoteId)).thenReturn(Optional.of(quote));

        // When
        Optional<DailyQuote> result = dailyQuoteService.getQuoteById(quoteId);

        // Then
        assertTrue(result.isPresent(), "Quote should be present");
        assertEquals(quote, result.get(), "Returned quote should be equal to expected");
        verify(dailyQuoteRepository, times(1)).findById(quoteId);
    }

    @Test
    void givenNonExistingQuoteId_whenGetQuoteById_thenReturnEmptyOptional() {
        // Given
        UUID quoteId = UUID.randomUUID();
        when(dailyQuoteRepository.findById(quoteId)).thenReturn(Optional.empty());

        // When
        Optional<DailyQuote> result = dailyQuoteService.getQuoteById(quoteId);

        // Then
        assertFalse(result.isPresent(), "Quote should not be found");
        verify(dailyQuoteRepository, times(1)).findById(quoteId);
    }

    @Test
    void givenExistingQuote_whenUpdateDailyQuote_thenReturnUpdatedQuote() {
        // Given
        UUID quoteId = UUID.randomUUID();
        DailyQuote quote = new DailyQuote();
        quote.setId(quoteId);
        when(dailyQuoteRepository.existsById(quoteId)).thenReturn(true);
        when(dailyQuoteRepository.save(quote)).thenReturn(quote);

        // When
        DailyQuote result = dailyQuoteService.updateDailyQuote(quote);

        // Then
        assertEquals(quote, result, "Updated quote should match expected");
        verify(dailyQuoteRepository, times(1)).existsById(quoteId);
        verify(dailyQuoteRepository, times(1)).save(quote);
    }

    @Test
    void givenNonExistingQuote_whenUpdateDailyQuote_thenThrowEntityNotFoundException() {
        // Given
        UUID quoteId = UUID.randomUUID();
        DailyQuote quote = new DailyQuote();
        quote.setId(quoteId);
        when(dailyQuoteRepository.existsById(quoteId)).thenReturn(false);

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> dailyQuoteService.updateDailyQuote(quote),
                "Expected exception when updating non-existing quote");
        assertEquals("Quote not found with ID: " + quoteId, exception.getMessage());
        verify(dailyQuoteRepository, times(1)).existsById(quoteId);
        verify(dailyQuoteRepository, never()).save(any(DailyQuote.class));
    }

    @Test
    void givenExistingQuoteId_whenDeleteDailyQuote_thenDeleteSuccessfully() {
        // Given
        UUID quoteId = UUID.randomUUID();
        when(dailyQuoteRepository.existsById(quoteId)).thenReturn(true);

        // When
        dailyQuoteService.deleteDailyQuote(quoteId);

        // Then
        verify(dailyQuoteRepository, times(1)).existsById(quoteId);
        verify(dailyQuoteRepository, times(1)).deleteById(quoteId);
    }

    @Test
    void givenNonExistingQuoteId_whenDeleteDailyQuote_thenThrowEntityNotFoundException() {
        // Given
        UUID quoteId = UUID.randomUUID();
        when(dailyQuoteRepository.existsById(quoteId)).thenReturn(false);

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> dailyQuoteService.deleteDailyQuote(quoteId),
                "Expected exception when deleting non-existing quote");
        assertEquals("Quote not found with ID: " + quoteId, exception.getMessage());
        verify(dailyQuoteRepository, times(1)).existsById(quoteId);
        verify(dailyQuoteRepository, never()).deleteById(any(UUID.class));
    }
}