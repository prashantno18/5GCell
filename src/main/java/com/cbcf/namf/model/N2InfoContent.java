package com.cbcf.namf.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class N2InfoContent {
    
    @JsonProperty("ngapIeType")
    private String ngapIeType;
    
    @JsonProperty("ngapData")
    private NgapData ngapData;

    public String getNgapIeType() {
        return ngapIeType;
    }

    public void setNgapIeType(String ngapIeType) {
        this.ngapIeType = ngapIeType;
    }

    public NgapData getNgapData() {
        return ngapData;
    }

    public void setNgapData(NgapData ngapData) {
        this.ngapData = ngapData;
    }
}
