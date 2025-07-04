package com.cbcf.namf.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NgapData {
    
    @JsonProperty("contentId")
    private String contentId;
    
    @JsonProperty("ngapMessageType")
    private String ngapMessageType;
    
    @JsonProperty("ngapMessagePayload")
    private String ngapMessagePayload;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getNgapMessageType() {
        return ngapMessageType;
    }

    public void setNgapMessageType(String ngapMessageType) {
        this.ngapMessageType = ngapMessageType;
    }

    public String getNgapMessagePayload() {
        return ngapMessagePayload;
    }

    public void setNgapMessagePayload(String ngapMessagePayload) {
        this.ngapMessagePayload = ngapMessagePayload;
    }
}
