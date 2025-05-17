package test.first.subs.dto;

import java.time.LocalDate;

public record SubscriptionResponse(
        String name,
        LocalDate refreshDate,
        LocalDate endDate
) {
}
