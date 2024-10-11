package org.ips.xml.signer.xmlsigner.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;


@Data
public class CertificateResponse {
    @JsonProperty("certificate")
    private String certificate;

    public static CertificateResponse convert(String json){

        CertificateResponse response=null;
        ObjectMapper objectMapper= new ObjectMapper();
        try {
            response=objectMapper.readValue(json,CertificateResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return  response;
    }
}
