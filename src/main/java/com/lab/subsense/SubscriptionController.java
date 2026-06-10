package com.lab.subsense;

import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin
public class SubscriptionController {
  private final SubscriptionRepository repo;
  public SubscriptionController(SubscriptionRepository repo) { this.repo = repo; }

  @GetMapping public List<Subscription> all() { return repo.findAll(); }

  @PostMapping public Subscription create(@RequestBody Subscription s) { return repo.save(s); }

  @PutMapping("/{id}") public Subscription update(@PathVariable Long id, @RequestBody Subscription s) {
    s.setId(id); return repo.save(s);
  }

  @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { repo.deleteById(id); }

  @GetMapping("/due-soon") public List<Subscription> dueSoon() {
    LocalDate today = LocalDate.now();
    return repo.findByActiveTrueAndNextRenewalBetween(today, today.plusDays(7));
  }

  @GetMapping("/summary") public Map<String,Object> summary() {
    BigDecimal monthly = repo.findByActiveTrue().stream()
            .map(s -> "YEARLY".equalsIgnoreCase(s.getCycle())
                    ? s.getAmount().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP)
                    : s.getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    return Map.of(
            "activeCount", repo.findByActiveTrue().size(),
            "monthlySpend", monthly,
            "annualSpend", monthly.multiply(BigDecimal.valueOf(12)));
  }
}