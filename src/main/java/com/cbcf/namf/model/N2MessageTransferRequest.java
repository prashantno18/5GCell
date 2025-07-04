package com.cbcf.namf.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class N2MessageTransferRequest {
    
    @NotNull
    @JsonProperty("n2InfoContainer")
    private N2InfoContainer n2InfoContainer;
    
    @JsonProperty("pduSessionId")
    private Integer pduSessionId;
    
    @JsonProperty("requestIndication")
    private String requestIndication;
    
    @JsonProperty("skipInd")
    private Boolean skipInd;
    
    @JsonProperty("n2InfoNotifyReason")
    private String n2InfoNotifyReason;

    @JsonProperty("message")
    private String message;

    public N2InfoContainer getN2InfoContainer() {
        return n2InfoContainer;
    }

    public void setN2InfoContainer(N2InfoContainer n2InfoContainer) {
        this.n2InfoContainer = n2InfoContainer;
    }

    public Integer getPduSessionId() {
        return pduSessionId;
    }

    public void setPduSessionId(Integer pduSessionId) {
        this.pduSessionId = pduSessionId;
    }

    public String getRequestIndication() {
        return requestIndication;
    }

    public void setRequestIndication(String requestIndication) {
        this.requestIndication = requestIndication;
    }

    public Boolean getSkipInd() {
        return skipInd;
    }

    public void setSkipInd(Boolean skipInd) {
        this.skipInd = skipInd;
    }

    public String getN2InfoNotifyReason() {
        return n2InfoNotifyReason;
    }

    public void setN2InfoNotifyReason(String n2InfoNotifyReason) {
        this.n2InfoNotifyReason = n2InfoNotifyReason;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
