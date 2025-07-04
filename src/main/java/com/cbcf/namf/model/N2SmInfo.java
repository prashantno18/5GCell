package com.cbcf.namf.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class N2SmInfo {
    
    @JsonProperty("pduSessionId")
    private Integer pduSessionId;
    
    @JsonProperty("n2InfoContent")
    private N2InfoContent n2InfoContent;
    
    @JsonProperty("sNssai")
    private SNssai sNssai;

    public Integer getPduSessionId() {
        return pduSessionId;
    }

    public void setPduSessionId(Integer pduSessionId) {
        this.pduSessionId = pduSessionId;
    }

    public N2InfoContent getN2InfoContent() {
        return n2InfoContent;
    }

    public void setN2InfoContent(N2InfoContent n2InfoContent) {
        this.n2InfoContent = n2InfoContent;
    }

    public SNssai getsNssai() {
        return sNssai;
    }

    public void setsNssai(SNssai sNssai) {
        this.sNssai = sNssai;
    }
}
