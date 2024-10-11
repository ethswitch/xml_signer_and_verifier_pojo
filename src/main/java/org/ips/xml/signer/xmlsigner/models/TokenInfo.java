package org.ips.xml.signer.xmlsigner.models;

import lombok.Data;

@Data
public class TokenInfo {

    private String access_token;
    private Long expires_in;
    private String refresh_token;
    private Long refresh_expires_in;

    // Constructor that takes a JSON response (String) and parses it
    public TokenInfo(String responseBody) {
        // Naive JSON parsing, assuming the format of the response is known and valid
        // In a real-world scenario, use a JSON parsing library like Jackson or Gson

        // This is just a demonstration. You should replace it with proper JSON parsing.
        this.access_token = extractValue(responseBody, "access_token");
        this.expires_in = Long.parseLong(extractValue(responseBody, "expires_in"));
        this.refresh_token = extractValue(responseBody, "refresh_token");
        this.refresh_expires_in = Long.parseLong(extractValue(responseBody, "refresh_expires_in"));
    }

    // Helper method to extract values from JSON-like strings (simplified for demonstration)
    private String extractValue(String json, String key) {
        String pattern = "\"" + key + "\":\"";
        int startIndex = json.indexOf(pattern) + pattern.length();
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }

    // Getters and setters (optional, but typical in POJO classes)
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Long getRefresh_expires_in() {
        return refresh_expires_in;
    }

    public void setRefresh_expires_in(Long refresh_expires_in) {
        this.refresh_expires_in = refresh_expires_in;
    }
}
