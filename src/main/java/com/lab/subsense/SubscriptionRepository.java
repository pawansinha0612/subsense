package com.lab.subsense;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByActiveTrue();
    List<Subscription> findByActiveTrueAndNextRenewalBetween(LocalDate start, LocalDate end);
}