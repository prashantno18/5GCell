package com.cbcf.namf.repository;

import com.cbcf.namf.model.N2InfoSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface N2InfoSubscriptionRepository extends JpaRepository<N2InfoSubscription, Long> {
    Optional<N2InfoSubscription> findBySubscriptionId(String subscriptionId);
    List<N2InfoSubscription> findByIsActiveTrue();
    
    @Query("SELECT s FROM N2InfoSubscription s WHERE s.validityTime < ?1 AND s.isActive = true")
    List<N2InfoSubscription> findExpiredSubscriptions(LocalDateTime currentTime);
}