package org.ips.xml.signer.xmlsigner.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;


public class TokenInfo {

    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("expires_in")
    private Long expires_in;

    @JsonProperty("refresh_token")
    private String refresh_token;

    @JsonProperty("refresh_expires_in")
    private Long refresh_expires_in;

    @JsonProperty("token_type")
    private String token_type;

    @JsonProperty("not-before-policy")
    private String not_before_policy;

    @JsonProperty("session_state")
    private String session_state;

    @JsonProperty("scope")
    private String scope;


    // Constructor that takes a JSON response (String) and parses it
    // Default constructor
    public TokenInfo() {
    }

    // Constructor with parameters for all fields
    @JsonCreator
    public TokenInfo(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("expires_in") Long expiresIn,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("token_type") String tokenType,
            @JsonProperty("refresh_expires_in") Long refreshExpiresIn,
            @JsonProperty("not-before-policy") Long notBeforePolicy,
            @JsonProperty("session_state") String sessionState,
            @JsonProperty("scope") String scope) {
        this.access_token = accessToken;
        this.expires_in = expiresIn;
        this.refresh_token = refreshToken;
        this.token_type = tokenType;
        this.refresh_expires_in = refreshExpiresIn;
        this.not_before_policy = not_before_policy;
        this.session_state = sessionState;
        this.scope = scope;
    }

    @JsonIgnore
    public static TokenInfo convert(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        TokenInfo tokenInfo = null;
        try {
            tokenInfo = objectMapper.readValue(json, TokenInfo.class);

            System.out.println("TokenInfo: " + tokenInfo);
        } catch (Exception e) {
            e.printStackTrace();


        }

        return tokenInfo;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return this.refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Long getRefresh_expires_in() {
        return this.refresh_expires_in;
    }

    public void setRefresh_expires_in(Long refresh_expires_in) {
        this.refresh_expires_in = refresh_expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getNot_before_policy() {
        return not_before_policy;
    }

    public void setNot_before_policy(String not_before_policy) {
        this.not_before_policy = not_before_policy;
    }

    public String getSession_state() {
        return session_state;
    }

    public void setSession_state(String session_state) {
        this.session_state = session_state;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
