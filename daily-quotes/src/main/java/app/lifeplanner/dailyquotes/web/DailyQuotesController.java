package app.lifeplanner.dailyquotes.web;

import app.lifeplanner.dailyquotes.service.DailyQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/daily-quotes")
public class DailyQuotesController {

    private final DailyQuoteService dailyQuoteService;

    @Autowired
    public DailyQuotesController(DailyQuoteService dailyQuoteService) {
        this.dailyQuoteService = dailyQuoteService;
    }
}
