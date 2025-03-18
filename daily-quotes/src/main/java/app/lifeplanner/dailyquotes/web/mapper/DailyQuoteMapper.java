package app.lifeplanner.dailyquotes.web.mapper;

import app.lifeplanner.dailyquotes.model.DailyQuote;
import app.lifeplanner.dailyquotes.web.dto.AddDailyQuoteRequest;
import app.lifeplanner.dailyquotes.web.dto.EditDailyQuotesRequest;

public class DailyQuoteMapper {
    // For "add" operations, using AddDailyQuoteRequest
    public static AddDailyQuoteRequest fromEntity(DailyQuote entity) {
        return AddDailyQuoteRequest.builder()
                .id(entity.getId())
                .quoteImage(entity.getQuoteImage())
                .userId(entity.getUserId())  // include userId from the entity
                .build();
    }

    public static DailyQuote toEntity(AddDailyQuoteRequest dto) {
        if (dto == null) {
            return null;
        }
        return DailyQuote.builder()
                .id(dto.getId())
                .quoteImage(dto.getQuoteImage())
                .userId(dto.getUserId())  // map userId as well
                .build();
    }

    // For "edit" operations, using EditDailyQuotesRequest
    public static EditDailyQuotesRequest fromEntityToEdit(DailyQuote entity) {
        return EditDailyQuotesRequest.builder()
                .id(entity.getId())
                .quoteImage(entity.getQuoteImage())
                .userId(entity.getUserId())
                .build();
    }

    public static DailyQuote toEntity(EditDailyQuotesRequest dto) {
        if(dto == null) {
            return null;
        }
        return DailyQuote.builder()
                .id(dto.getId())
                .quoteImage(dto.getQuoteImage())
                .userId(dto.getUserId())
                .build();
    }
}