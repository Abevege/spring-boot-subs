package test.first.subs.dto;

import java.time.LocalDate;

public record SubscriptionRequest(
        String name,
        LocalDate refreshDate,
        LocalDate endDate
) {
}
