package app.lifeplanner.dailyquotes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyQuote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String quoteImage;
}
