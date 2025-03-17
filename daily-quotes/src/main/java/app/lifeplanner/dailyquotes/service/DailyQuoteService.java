package app.lifeplanner.dailyquotes.service;

import app.lifeplanner.dailyquotes.repository.DailyQuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyQuoteService {
    private final DailyQuoteRepository dailyQuoteRepository;

    @Autowired
    public DailyQuoteService(DailyQuoteRepository dailyQuoteRepository) {
        this.dailyQuoteRepository = dailyQuoteRepository;
    }
}
