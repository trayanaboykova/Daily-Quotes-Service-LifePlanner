package app.lifeplanner.dailyquotes.repository;

import app.lifeplanner.dailyquotes.model.DailyQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DailyQuoteRepository extends JpaRepository<DailyQuote, UUID> {
    List<DailyQuote> findByUserId(UUID userId);
}
