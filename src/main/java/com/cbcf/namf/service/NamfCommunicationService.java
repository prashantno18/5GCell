package com.cbcf.namf.service;

import com.cbcf.namf.controller.NamfCommunicationController;
import com.cbcf.namf.model.*;
import com.cbcf.namf.repository.N2InfoSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
//@RequiredArgsConstructor
@Slf4j
public class NamfCommunicationService {



    private final WebClient webClient;
    private final N2InfoSubscriptionRepository subscriptionRepository;
    
    @Value("${cbcf.amf.base-url}")
    private String amfBaseUrl;
    
    @Value("${cbcf.subscription.default-validity:3600}")
    private int defaultValiditySeconds;
    
    // Add timeout configuration
    @Value("${cbcf.amf.timeout:5000}")
    private int amfTimeout;

    public NamfCommunicationService(WebClient webClient, N2InfoSubscriptionRepository subscriptionRepository) {
        this.webClient = webClient;
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Implements NonUeN2MessageTransfer operation
     * Transfers N2 messages to AMF for non-UE specific procedures
     */
    public Mono<ApiResponse<String>> transferNonUeN2Message(N2MessageTransferRequest request) {
//    log.info("Processing NonUeN2MessageTransfer request");

    try {
        // Validate request
        if (request.getN2InfoContainer() == null) {
//            log.warn("Validation failed: N2InfoContainer is required");
            return Mono.just(ApiResponse.error("N2InfoContainer is required"));
        }

        try {
            // Forward message to AMF
            return webClient.post()
                    .uri(amfBaseUrl + "/namf-comm/v1/non-ue-n2-messages")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(amfTimeout))
                    .map(response -> {
//                        log.info("Successfully transferred N2 message to AMF");
                        return ApiResponse.success(response, "N2 message transferred successfully");
                    })
                    .onErrorResume(error -> {
//                        log.error("Failed to transfer N2 message to AMF: {}", error.getMessage());
                        return Mono.just(ApiResponse.error("Failed to transfer N2 message: " + error.getMessage()));
                    });
        } catch (Exception e) {
//            log.error("Error during WebClient operation: {}", e.getMessage());
            return Mono.just(ApiResponse.error("Internal error during WebClient operation: " + e.getMessage()));
        }
    } catch (Exception e) {
//        log.error("Unexpected error processing N2 message transfer: {}", e.getMessage());
        return Mono.just(ApiResponse.error("Internal error: " + e.getMessage()));
    }
}
    
    /**
     * Implements NonUeN2InfoSubscribe operation
     * Subscribes to N2 information notifications from AMF
     */
    public ApiResponse<String> subscribeToN2Info(N2InfoSubscribeRequest request) {
//        log.info("Processing NonUeN2InfoSubscribe request for callback: {}", request.getN2NotifyCallbackUri());
        
        try {
            // Validate request
            if (request == null || request.getN2NotifyCallbackUri() == null) {
                return ApiResponse.error("Invalid request: N2NotifyCallbackUri is required");
            }

            if (request.getN2InfoClass() == null) {
                return ApiResponse.error("Invalid request: N2InfoClass is required");
            }
            
            // Generate unique subscription ID
            String subscriptionId = UUID.randomUUID().toString();
            
            // Calculate validity time
            LocalDateTime validityTime = request.getSubscriptionValidityTime() != null 
                ? request.getSubscriptionValidityTime()
                : LocalDateTime.now().plusSeconds(defaultValiditySeconds);
            
            // Create subscription entity
            N2InfoSubscription subscription = new N2InfoSubscription();
            subscription.setSubscriptionId(subscriptionId);
            subscription.setCallbackUri(request.getN2NotifyCallbackUri());
            subscription.setN2InfoClass(request.getN2InfoClass());
            subscription.setN2InfoType(request.getN2InfoType() != null ? 
                String.join(",", request.getN2InfoType()) : null);
            subscription.setAmfInstanceId(request.getAmfInstanceId());
            subscription.setValidityTime(validityTime);
            subscription.setActive(true);
            
            // Save subscription
            subscriptionRepository.save(subscription);
            
//            log.info("Created N2 info subscription with ID: {}", subscriptionId);
            return ApiResponse.success(subscriptionId, "Subscription created successfully");
            
        } catch (Exception e) {
//            log.error("Error creating N2 info subscription: {}", e.getMessage());
            return ApiResponse.error("Failed to create subscription: " + e.getMessage());
        }
    }
    
    /**
     * Implements NonUeN2InfoUnSubscribe operation
     * Unsubscribes from N2 information notifications
     */
    public ApiResponse<String> unsubscribeFromN2Info(String subscriptionId) {
//        log.info("Processing NonUeN2InfoUnSubscribe request for subscription: {}", subscriptionId);
        
        try {
            Optional<N2InfoSubscription> subscriptionOpt = 
                subscriptionRepository.findBySubscriptionId(subscriptionId);
            
            if (subscriptionOpt.isEmpty()) {
//                log.warn("Subscription not found: {}", subscriptionId);
                return ApiResponse.error("Subscription not found");
            }
            
            N2InfoSubscription subscription = subscriptionOpt.get();
            subscription.setActive(false);
            subscriptionRepository.save(subscription);
            
//            log.info("Successfully unsubscribed from N2 info: {}", subscriptionId);
            return ApiResponse.success(subscriptionId, "Unsubscribed successfully");
            
        } catch (Exception e) {
//            log.error("Error unsubscribing from N2 info: {}", e.getMessage());
            return ApiResponse.error("Failed to unsubscribe: " + e.getMessage());
        }
    }
    
    /**
     * Get all active subscriptions
     */
    public List<N2InfoSubscription> getActiveSubscriptions() {
        return subscriptionRepository.findByIsActiveTrue();
    }
    
    /**
     * Clean up expired subscriptions
     */
    public void cleanupExpiredSubscriptions() {
        List<N2InfoSubscription> expiredSubscriptions = 
            subscriptionRepository.findExpiredSubscriptions(LocalDateTime.now());
        
        for (N2InfoSubscription subscription : expiredSubscriptions) {
            subscription.setActive(false);
            subscriptionRepository.save(subscription);
//            log.info("Deactivated expired subscription: {}", subscription.getSubscriptionId());
        }
    }
}
