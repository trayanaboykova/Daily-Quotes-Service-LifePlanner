package app.lifeplanner.dailyquotes.web;

import app.lifeplanner.dailyquotes.model.DailyQuote;
import app.lifeplanner.dailyquotes.service.DailyQuoteService;
import app.lifeplanner.dailyquotes.web.dto.AddDailyQuoteRequest;
import app.lifeplanner.dailyquotes.web.dto.EditDailyQuotesRequest;
import app.lifeplanner.dailyquotes.web.mapper.DailyQuoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/daily-quotes")
public class DailyQuotesController {

    private final DailyQuoteService dailyQuoteService;

    @Autowired
    public DailyQuotesController(DailyQuoteService dailyQuoteService) {
        this.dailyQuoteService = dailyQuoteService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddDailyQuoteRequest>> getQuotesByUserId(@PathVariable UUID userId) {
        List<DailyQuote> quotes = dailyQuoteService.getQuotesByUserId(userId);
        List<AddDailyQuoteRequest> dtos = quotes.stream()
                .map(DailyQuoteMapper::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET endpoint to retrieve a specific quote by ID
    @GetMapping("/{id}")
    public ResponseEntity<AddDailyQuoteRequest> getQuoteById(@PathVariable UUID id) {
        Optional<DailyQuote> optQuote = dailyQuoteService.getQuoteById(id);
        return optQuote.map(q -> ResponseEntity.ok(DailyQuoteMapper.fromEntity(q)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST endpoint to add a new daily quote using DTO
    @PostMapping
    public ResponseEntity<AddDailyQuoteRequest> addDailyQuote(@RequestBody AddDailyQuoteRequest addDailyQuoteRequest) {
        DailyQuote entity = DailyQuoteMapper.toEntity(addDailyQuoteRequest);
        DailyQuote saved = dailyQuoteService.addDailyQuote(entity);
        AddDailyQuoteRequest savedAddRequest = DailyQuoteMapper.fromEntity(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddRequest);
    }

    // PUT endpoint to update an existing quote using the EditDailyQuotesRequest DTO
    @PutMapping("/{id}")
    public ResponseEntity<EditDailyQuotesRequest> updateDailyQuote(@PathVariable UUID id, @RequestBody EditDailyQuotesRequest editRequest) {
        Optional<DailyQuote> existingOpt = dailyQuoteService.getQuoteById(id);
        if(existingOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        DailyQuote existing = existingOpt.get();
        if(editRequest.getUserId() == null) {
            editRequest.setUserId(existing.getUserId());
        }
        DailyQuote updated = dailyQuoteService.updateDailyQuote(DailyQuoteMapper.toEntity(editRequest));
        return ResponseEntity.ok(DailyQuoteMapper.fromEntityToEdit(updated));
    }


    // DELETE endpoint to delete a quote by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDailyQuote(@PathVariable UUID id) {
        dailyQuoteService.deleteDailyQuote(id);
        return ResponseEntity.noContent().build();
    }
}