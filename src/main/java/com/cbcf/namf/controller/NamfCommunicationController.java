package com.cbcf.namf.controller;

import com.cbcf.namf.model.*;
import com.cbcf.namf.service.NamfCommunicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/namf-comm/v1")
//@RequiredArgsConstructor
@Tag(name = "Namf Communication", description = "5G CBCF Namf_Communication Service Operations")
public class NamfCommunicationController {

    Logger log = Logger.getLogger(NamfCommunicationController.class.getName());


    private final NamfCommunicationService namfCommunicationService;

    @Autowired
    public NamfCommunicationController(NamfCommunicationService namfCommunicationService) {
        this.namfCommunicationService = namfCommunicationService;
    }

    @PostMapping("/non-ue-n2-messages")
    @Operation(summary = "NonUeN2MessageTransfer", 
               description = "Transfer N2 messages for non-UE specific procedures")
    public ResponseEntity<ApiResponse<String>> transferNonUeN2Message(@RequestBody N2MessageTransferRequest request) {
        if (request.getMessage() == null || request.getMessage().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid message"));
        }
        // Logic to process the message
        return ResponseEntity.ok(new ApiResponse(true, "Forwarded"));

    }
    
    @PostMapping("/subscriptions")
    @Operation(summary = "NonUeN2InfoSubscribe", 
               description = "Subscribe to N2 information notifications")
    public ResponseEntity<ApiResponse<String>> subscribeToN2Info(
            @Valid @RequestBody N2InfoSubscribeRequest request) {
        
        log.info("Received NonUeN2InfoSubscribe request");
        
        ApiResponse<String> response = namfCommunicationService.subscribeToN2Info(request);
        
        return response.isSuccess() 
            ? ResponseEntity.status(HttpStatus.CREATED).body(response)
            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @DeleteMapping("/subscriptions/{subscriptionId}")
    @Operation(summary = "NonUeN2InfoUnSubscribe", 
               description = "Unsubscribe from N2 information notifications")
    public ResponseEntity<ApiResponse<String>> unsubscribeFromN2Info(
            @Parameter(description = "Subscription ID to unsubscribe")
            @PathVariable String subscriptionId) {
        
        log.info("Received NonUeN2InfoUnSubscribe request for: {}");
        
        ApiResponse<String> response = namfCommunicationService.unsubscribeFromN2Info(subscriptionId);
        
        return response.isSuccess() 
            ? ResponseEntity.ok(response)
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    @GetMapping("/subscriptions")
    @Operation(summary = "Get Active Subscriptions", 
               description = "Retrieve all active N2 information subscriptions")
    public ResponseEntity<ApiResponse<List<N2InfoSubscription>>> getActiveSubscriptions() {
        
        log.info("Received request to get active subscriptions");
        
        List<N2InfoSubscription> subscriptions = namfCommunicationService.getActiveSubscriptions();
        
        return ResponseEntity.ok(
            ApiResponse.success(subscriptions, "Active subscriptions retrieved successfully"));
    }
    
    @PostMapping("/subscriptions/cleanup")
    @Operation(summary = "Cleanup Expired Subscriptions", 
               description = "Clean up expired N2 information subscriptions")
    public ResponseEntity<ApiResponse<String>> cleanupExpiredSubscriptions() {
        
        log.info("Received request to cleanup expired subscriptions");
        
        namfCommunicationService.cleanupExpiredSubscriptions();
        
        return ResponseEntity.ok(
            ApiResponse.success("cleanup-completed", "Expired subscriptions cleaned up successfully"));
    }
}
