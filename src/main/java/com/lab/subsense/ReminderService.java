package com.lab.subsense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderService {
    private static final Logger log = LoggerFactory.getLogger(ReminderService.class);
    private final SubscriptionRepository repo;

    public ReminderService(SubscriptionRepository repo) { this.repo = repo; }

    // Demo cadence: every 60s so you can watch it fire. In production this would be a
    // daily cron, e.g. @Scheduled(cron = "0 0 8 * * *")  -> 8am every day.
    @Scheduled(fixedRate = 60000)
    public void checkRenewals() {
        LocalDate today = LocalDate.now();
        List<Subscription> dueSoon = repo.findByActiveTrueAndNextRenewalBetween(today, today.plusDays(7));
        if (dueSoon.isEmpty()) {
            log.info("Renewal check: nothing due in the next 7 days.");
        } else {
            dueSoon.forEach(s -> log.warn("RENEWAL DUE SOON: {} — {} {} renews {}",
                    s.getName(), s.getCurrency(), s.getAmount(), s.getNextRenewal()));
        }
    }
}