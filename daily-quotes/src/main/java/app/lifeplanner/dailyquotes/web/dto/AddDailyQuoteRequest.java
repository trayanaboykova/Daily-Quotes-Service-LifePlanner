package app.lifeplanner.dailyquotes.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AddDailyQuoteRequest {
    private UUID id;
    @NotBlank(message = "Image URL cannot be blank")
    private String quoteImage;
    private UUID userId;
}