package test.first.subs;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import test.first.subs.dto.PopularResponse;
import test.first.subs.dto.SubscriptionRequest;
import test.first.subs.dto.SubscriptionResponse;
import test.first.subs.jpa.Subscription;
import test.first.subs.jpa.SubscriptionsRepository;
import test.first.subs.jpa.User;
import test.first.subs.jpa.UserRepository;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class SubscriptionManagerController {
    private final SubscriptionsRepository subscriptionsRepository;
    private final UserRepository userRepository;

    public SubscriptionManagerController(SubscriptionsRepository subscriptionsRepository, UserRepository userRepository) {
        this.subscriptionsRepository = subscriptionsRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/users/{id}/subscriptions")
    public @ResponseBody ResponseEntity<Void> addSubscription(@PathVariable Long id,
                                                @RequestBody SubscriptionRequest subscriptionRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setName(subscriptionRequest.name());
        subscription.setRefreshDate(subscriptionRequest.refreshDate());
        subscription.setEndDate(subscriptionRequest.endDate());
        subscriptionsRepository.save(subscription);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/users/{id}/subscriptions")
    public @ResponseBody Set<SubscriptionResponse> getSubscriptions(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return subscriptionsRepository.findAllByUser(user).stream()
                .map(s -> new SubscriptionResponse(s.getName(), s.getRefreshDate(), s.getEndDate()))
                .collect(Collectors.toSet());
    }

    @DeleteMapping(path = "users/{id}/subscriptions/{sub_id}")
    public @ResponseBody ResponseEntity<Void> deleteSubscription(@PathVariable Long id, @PathVariable Long sub_id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Subscription subscription = subscriptionsRepository.findById(sub_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found"));

        subscription.setEndDate(subscription.getRefreshDate());
        subscriptionsRepository.save(subscription);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/subscriptions/top")
    public @ResponseBody Set<PopularResponse> getTopSubscriptions() {
        return subscriptionsRepository.findPopular(PageRequest.of(0, 3));
    }
}
