package app.lifeplanner.dailyquotes.service;

import app.lifeplanner.dailyquotes.exception.EntityNotFoundException;
import app.lifeplanner.dailyquotes.model.DailyQuote;
import app.lifeplanner.dailyquotes.repository.DailyQuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DailyQuoteService {
    private final DailyQuoteRepository dailyQuoteRepository;

    @Autowired
    public DailyQuoteService(DailyQuoteRepository dailyQuoteRepository) {
        this.dailyQuoteRepository = dailyQuoteRepository;
    }

    // Save a new quote (make sure to set userId when saving)
    public DailyQuote addDailyQuote(DailyQuote dailyQuote) {
        return dailyQuoteRepository.save(dailyQuote);
    }

    // Retrieve quotes for a specific user
    public List<DailyQuote> getQuotesByUserId(UUID userId) {
        return dailyQuoteRepository.findByUserId(userId);
    }

    // Retrieve a quote by ID
    public Optional<DailyQuote> getQuoteById(UUID id) {
        return dailyQuoteRepository.findById(id);
    }

    // Update an existing quote
    public DailyQuote updateDailyQuote(DailyQuote dailyQuote) {
        if (!dailyQuoteRepository.existsById(dailyQuote.getId())) {
            throw new EntityNotFoundException("Quote not found with ID: " + dailyQuote.getId());
        }
        return dailyQuoteRepository.save(dailyQuote);
    }

    // Delete a quote by ID
    public void deleteDailyQuote(UUID id) {
        if (!dailyQuoteRepository.existsById(id)) {
            throw new EntityNotFoundException("Quote not found with ID: " + id);
        }
        dailyQuoteRepository.deleteById(id);
    }
}