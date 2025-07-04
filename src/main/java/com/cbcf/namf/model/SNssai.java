package com.cbcf.namf.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SNssai {
    
    @JsonProperty("sst")
    private Integer sst;
    
    @JsonProperty("sd")
    private String sd;

    public Integer getSst() {
        return sst;
    }

    public void setSst(Integer sst) {
        this.sst = sst;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }
}
