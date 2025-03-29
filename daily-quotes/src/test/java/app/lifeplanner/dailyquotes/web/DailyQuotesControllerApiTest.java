package app.lifeplanner.dailyquotes.web;

import app.lifeplanner.dailyquotes.model.DailyQuote;
import app.lifeplanner.dailyquotes.service.DailyQuoteService;
import app.lifeplanner.dailyquotes.web.dto.AddDailyQuoteRequest;
import app.lifeplanner.dailyquotes.web.dto.EditDailyQuotesRequest;
import app.lifeplanner.dailyquotes.web.mapper.DailyQuoteMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DailyQuotesController.class)
public class DailyQuotesControllerApiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DailyQuoteService dailyQuoteService;

    @Autowired
    private ObjectMapper objectMapper;

    private DailyQuote createDummyQuote(UUID id, UUID userId) {
        DailyQuote quote = new DailyQuote();
        quote.setId(id);
        quote.setUserId(userId);
        return quote;
    }

    // GET /api/v1/daily-quotes/user/{userId}
    @Test
    public void testGetQuotesByUserId() throws Exception {
        UUID userId = UUID.randomUUID();
        DailyQuote quote1 = createDummyQuote(UUID.randomUUID(), userId);
        DailyQuote quote2 = createDummyQuote(UUID.randomUUID(), userId);
        List<DailyQuote> quotes = Arrays.asList(quote1, quote2);
        when(dailyQuoteService.getQuotesByUserId(userId)).thenReturn(quotes);

        mockMvc.perform(get("/api/v1/daily-quotes/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(quote1.getId().toString())))
                .andExpect(jsonPath("$[1].id", is(quote2.getId().toString())));

        verify(dailyQuoteService, times(1)).getQuotesByUserId(userId);
    }

    // GET /api/v1/daily-quotes/{id} - Found
    @Test
    public void testGetQuoteById_Found() throws Exception {
        UUID quoteId = UUID.randomUUID();
        DailyQuote quote = createDummyQuote(quoteId, UUID.randomUUID());
        when(dailyQuoteService.getQuoteById(quoteId)).thenReturn(Optional.of(quote));

        mockMvc.perform(get("/api/v1/daily-quotes/{id}", quoteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(quoteId.toString())));

        verify(dailyQuoteService, times(1)).getQuoteById(quoteId);
    }

    // GET /api/v1/daily-quotes/{id} - Not found
    @Test
    public void testGetQuoteById_NotFound() throws Exception {
        UUID quoteId = UUID.randomUUID();
        when(dailyQuoteService.getQuoteById(quoteId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/daily-quotes/{id}", quoteId))
                .andExpect(status().isNotFound());

        verify(dailyQuoteService, times(1)).getQuoteById(quoteId);
    }

    // POST /api/v1/daily-quotes
    @Test
    public void testAddDailyQuote() throws Exception {
        AddDailyQuoteRequest requestDto = AddDailyQuoteRequest.builder().build();
        DailyQuote inputEntity = DailyQuoteMapper.toEntity(requestDto);
        DailyQuote savedEntity = createDummyQuote(UUID.randomUUID(), inputEntity.getUserId());
        when(dailyQuoteService.addDailyQuote(ArgumentMatchers.any(DailyQuote.class)))
                .thenReturn(savedEntity);

        mockMvc.perform(post("/api/v1/daily-quotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(savedEntity.getId().toString())));

        verify(dailyQuoteService, times(1))
                .addDailyQuote(ArgumentMatchers.any(DailyQuote.class));
    }

    // PUT /api/v1/daily-quotes/{id} - Success scenario
    @Test
    public void testUpdateDailyQuote_Success() throws Exception {
        UUID quoteId = UUID.randomUUID();
        EditDailyQuotesRequest editRequest = EditDailyQuotesRequest.builder().build();
        UUID newUserId = UUID.randomUUID();
        editRequest.setUserId(newUserId);

        DailyQuote existing = createDummyQuote(quoteId, UUID.randomUUID());
        when(dailyQuoteService.getQuoteById(quoteId)).thenReturn(Optional.of(existing));

        DailyQuote updated = createDummyQuote(quoteId, newUserId);
        when(dailyQuoteService.updateDailyQuote(ArgumentMatchers.any(DailyQuote.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/v1/daily-quotes/{id}", quoteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(quoteId.toString())))
                .andExpect(jsonPath("$.userId", is(newUserId.toString())));

        verify(dailyQuoteService, times(1)).getQuoteById(quoteId);
        verify(dailyQuoteService, times(1))
                .updateDailyQuote(ArgumentMatchers.any(DailyQuote.class));
    }

    // PUT /api/v1/daily-quotes/{id} - Not found
    @Test
    public void testUpdateDailyQuote_NotFound() throws Exception {
        UUID quoteId = UUID.randomUUID();
        EditDailyQuotesRequest editRequest = EditDailyQuotesRequest.builder().build();
        when(dailyQuoteService.getQuoteById(quoteId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/daily-quotes/{id}", quoteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editRequest)))
                .andExpect(status().isNotFound());

        verify(dailyQuoteService, times(1)).getQuoteById(quoteId);
        verify(dailyQuoteService, never())
                .updateDailyQuote(ArgumentMatchers.any(DailyQuote.class));
    }

    // PUT /api/v1/daily-quotes/{id} - When editRequest.getUserId() is null
    @Test
    public void testUpdateDailyQuote_NullUserId() throws Exception {
        UUID quoteId = UUID.randomUUID();
        EditDailyQuotesRequest editRequest = EditDailyQuotesRequest.builder().build();
        UUID existingUserId = UUID.randomUUID();
        DailyQuote existing = createDummyQuote(quoteId, existingUserId);
        when(dailyQuoteService.getQuoteById(quoteId)).thenReturn(Optional.of(existing));

        DailyQuote updated = createDummyQuote(quoteId, existingUserId);
        when(dailyQuoteService.updateDailyQuote(ArgumentMatchers.any(DailyQuote.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/v1/daily-quotes/{id}", quoteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(quoteId.toString())))
                .andExpect(jsonPath("$.userId", is(existingUserId.toString())));

        verify(dailyQuoteService, times(1)).getQuoteById(quoteId);
        verify(dailyQuoteService, times(1))
                .updateDailyQuote(ArgumentMatchers.any(DailyQuote.class));
    }

    // DELETE /api/v1/daily-quotes/{id}
    @Test
    public void testDeleteDailyQuote() throws Exception {
        UUID quoteId = UUID.randomUUID();
        doNothing().when(dailyQuoteService).deleteDailyQuote(quoteId);

        mockMvc.perform(delete("/api/v1/daily-quotes/{id}", quoteId))
                .andExpect(status().isNoContent());

        verify(dailyQuoteService, times(1)).deleteDailyQuote(quoteId);
    }
}