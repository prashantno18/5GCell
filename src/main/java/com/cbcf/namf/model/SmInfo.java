package com.cbcf.namf.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmInfo {
    
    @JsonProperty("dnn")
    private String dnn;
    
    @JsonProperty("sNssai")
    private SNssai sNssai;

    public String getDnn() {
        return dnn;
    }

    public void setDnn(String dnn) {
        this.dnn = dnn;
    }

    public SNssai getsNssai() {
        return sNssai;
    }

    public void setsNssai(SNssai sNssai) {
        this.sNssai = sNssai;
    }
}
