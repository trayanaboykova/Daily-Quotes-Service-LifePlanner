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

    // Test addDailyQuote
    @Test
    void testAddDailyQuote() {
        DailyQuote quote = new DailyQuote();
        // Optionally set properties on quote, e.g., quote text or userId
        when(dailyQuoteRepository.save(quote)).thenReturn(quote);

        DailyQuote result = dailyQuoteService.addDailyQuote(quote);

        assertSame(quote, result, "The returned quote should be the same instance as saved");
        verify(dailyQuoteRepository, times(1)).save(quote);
    }

    // Test getQuotesByUserId
    @Test
    void testGetQuotesByUserId() {
        UUID userId = UUID.randomUUID();
        List<DailyQuote> expectedList = new ArrayList<>();
        DailyQuote q1 = new DailyQuote();
        DailyQuote q2 = new DailyQuote();
        expectedList.add(q1);
        expectedList.add(q2);

        when(dailyQuoteRepository.findByUserId(userId)).thenReturn(expectedList);

        List<DailyQuote> result = dailyQuoteService.getQuotesByUserId(userId);
        assertEquals(expectedList, result, "Returned list should match expected");
        verify(dailyQuoteRepository, times(1)).findByUserId(userId);
    }

    // Test getQuoteById when found
    @Test
    void testGetQuoteById_Found() {
        UUID quoteId = UUID.randomUUID();
        DailyQuote quote = new DailyQuote();
        quote.setId(quoteId);
        when(dailyQuoteRepository.findById(quoteId)).thenReturn(Optional.of(quote));

        Optional<DailyQuote> result = dailyQuoteService.getQuoteById(quoteId);
        assertTrue(result.isPresent(), "Quote should be present");
        assertEquals(quote, result.get(), "Returned quote should be equal to expected");
        verify(dailyQuoteRepository, times(1)).findById(quoteId);
    }

    // Test getQuoteById when not found
    @Test
    void testGetQuoteById_NotFound() {
        UUID quoteId = UUID.randomUUID();
        when(dailyQuoteRepository.findById(quoteId)).thenReturn(Optional.empty());

        Optional<DailyQuote> result = dailyQuoteService.getQuoteById(quoteId);
        assertFalse(result.isPresent(), "Quote should not be found");
        verify(dailyQuoteRepository, times(1)).findById(quoteId);
    }

    // Test updateDailyQuote (success path)
    @Test
    void testUpdateDailyQuote_Success() {
        UUID quoteId = UUID.randomUUID();
        DailyQuote quote = new DailyQuote();
        quote.setId(quoteId);
        when(dailyQuoteRepository.existsById(quoteId)).thenReturn(true);
        when(dailyQuoteRepository.save(quote)).thenReturn(quote);

        DailyQuote result = dailyQuoteService.updateDailyQuote(quote);
        assertEquals(quote, result, "Updated quote should match expected");
        verify(dailyQuoteRepository, times(1)).existsById(quoteId);
        verify(dailyQuoteRepository, times(1)).save(quote);
    }

    // Test updateDailyQuote (not found path)
    @Test
    void testUpdateDailyQuote_NotFound() {
        UUID quoteId = UUID.randomUUID();
        DailyQuote quote = new DailyQuote();
        quote.setId(quoteId);
        when(dailyQuoteRepository.existsById(quoteId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> dailyQuoteService.updateDailyQuote(quote),
                "Expected exception when updating non-existing quote");
        assertEquals("Quote not found with ID: " + quoteId, exception.getMessage());
        verify(dailyQuoteRepository, times(1)).existsById(quoteId);
        verify(dailyQuoteRepository, never()).save(any(DailyQuote.class));
    }

    // Test deleteDailyQuote (success path)
    @Test
    void testDeleteDailyQuote_Success() {
        UUID quoteId = UUID.randomUUID();
        when(dailyQuoteRepository.existsById(quoteId)).thenReturn(true);

        dailyQuoteService.deleteDailyQuote(quoteId);
        verify(dailyQuoteRepository, times(1)).existsById(quoteId);
        verify(dailyQuoteRepository, times(1)).deleteById(quoteId);
    }

    // Test deleteDailyQuote (not found path)
    @Test
    void testDeleteDailyQuote_NotFound() {
        UUID quoteId = UUID.randomUUID();
        when(dailyQuoteRepository.existsById(quoteId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> dailyQuoteService.deleteDailyQuote(quoteId),
                "Expected exception when deleting non-existing quote");
        assertEquals("Quote not found with ID: " + quoteId, exception.getMessage());
        verify(dailyQuoteRepository, times(1)).existsById(quoteId);
        verify(dailyQuoteRepository, never()).deleteById(any(UUID.class));
    }
}