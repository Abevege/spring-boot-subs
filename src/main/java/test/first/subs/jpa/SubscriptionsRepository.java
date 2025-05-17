package test.first.subs.jpa;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.first.subs.dto.PopularResponse;

import java.awt.print.Pageable;
import java.util.Set;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscription, Long> {
    Set<Subscription> findAllByUser(User user);

    @Query("SELECT new test.first.subs.dto.PopularResponse(s.name, COUNT(s)) " +
            "FROM Subscription s " +
            "GROUP BY s.name " +
            "ORDER BY COUNT(s) desc")
    Set<PopularResponse> findPopular(PageRequest pageRequest);
}
