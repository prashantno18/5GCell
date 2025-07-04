package com.cbcf.namf.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class N2InfoSubscribeRequest {
    private String n2NotifyCallbackUri;
    private String n2InfoClass;
    private List<String> n2InfoType;
    private String amfInstanceId;
    private LocalDateTime subscriptionValidityTime;

    //generate getter and setter
    public String getN2NotifyCallbackUri() {
        return n2NotifyCallbackUri;
    }
    public void setN2NotifyCallbackUri(String n2NotifyCallbackUri) {
        this.n2NotifyCallbackUri = n2NotifyCallbackUri;
    }
    public String getN2InfoClass() {
        return n2InfoClass;
    }
    public void setN2InfoClass(String n2InfoClass) {
        this.n2InfoClass = n2InfoClass;
    }
    public List<String> getN2InfoType() {
        return n2InfoType;
    }
    public void setN2InfoType(List<String> n2InfoType) {
        this.n2InfoType = n2InfoType;
    }
    public String getAmfInstanceId() {
        return amfInstanceId;
    }
    public void setAmfInstanceId(String amfInstanceId) {
        this.amfInstanceId = amfInstanceId;
    }
    public LocalDateTime getSubscriptionValidityTime() {
        return subscriptionValidityTime;
    }
    public void setSubscriptionValidityTime(LocalDateTime subscriptionValidityTime) {
        this.subscriptionValidityTime = subscriptionValidityTime;
    }

}
