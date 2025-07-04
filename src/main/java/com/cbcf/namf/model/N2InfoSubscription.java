package com.cbcf.namf.model;

import lombok.Data;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "n2_info_subscriptions")
public class N2InfoSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String subscriptionId;
    private String callbackUri;
    private String n2InfoClass;
    private String n2InfoType;
    private String amfInstanceId;
    private LocalDateTime validityTime;
    private boolean isActive;

    public N2InfoSubscription() {
        this.isActive = true; // Default to active
        this.validityTime = LocalDateTime.now().plusSeconds(3600); // Default validity of 1 hour
    }
    public N2InfoSubscription(String subscriptionId, String callbackUri, String n2InfoClass, 
                              String n2InfoType, String amfInstanceId) {
        this.subscriptionId = subscriptionId;
        this.callbackUri = callbackUri;         
        this.n2InfoClass = n2InfoClass;
        this.n2InfoType = n2InfoType;
        this.amfInstanceId = amfInstanceId;
        this.isActive = true; // Default to active
        this.validityTime = LocalDateTime.now().plusSeconds(3600); // Default
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getCallbackUri() {
        return callbackUri;
    }

    public void setCallbackUri(String callbackUri) {
        this.callbackUri = callbackUri;
    }

    public String getN2InfoClass() {
        return n2InfoClass;
    }

    public void setN2InfoClass(String n2InfoClass) {
        this.n2InfoClass = n2InfoClass;
    }

    public String getN2InfoType() {
        return n2InfoType;
    }

    public void setN2InfoType(String n2InfoType) {
        this.n2InfoType = n2InfoType;
    }

    public String getAmfInstanceId() {
        return amfInstanceId;
    }

    public void setAmfInstanceId(String amfInstanceId) {
        this.amfInstanceId = amfInstanceId;
    }

    public LocalDateTime getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(LocalDateTime validityTime) {
        this.validityTime = validityTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
