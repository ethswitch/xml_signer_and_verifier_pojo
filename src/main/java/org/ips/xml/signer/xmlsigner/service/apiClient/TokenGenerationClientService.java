package org.ips.xml.signer.xmlsigner.service.apiClient;

import org.ips.xml.signer.xmlsigner.models.ParticipantCredentialInfo;
import org.ips.xml.signer.xmlsigner.models.TokenInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;
import java.net.URLEncoder;

public class TokenGenerationClientService {

    private static final Logger logger = LoggerFactory.getLogger(TokenGenerationClientService.class);

    private HttpClient httpClient;
    private URI authServerTokenUri;

    public TokenGenerationClientService() {
        this.httpClient = HttpClient.newHttpClient();  // Java 11 HttpClient
    }

    public void create(String tokenUrl) {
        this.authServerTokenUri = URI.create(tokenUrl);
    }

    public TokenInfo generateToken(ParticipantCredentialInfo credentialInfo) {
        create(credentialInfo.getTokenGenerationPath());

        // Prepare request body in application/x-www-form-urlencoded format
        Map<String, String> parameters = Map.of(
                "username", credentialInfo.getUserName(),
                "password", credentialInfo.getPassword(),
                "grant_type", credentialInfo.getGrantType()
        );
        String formParams = encodeParams(parameters);

        // Build the HTTP POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(authServerTokenUri)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("jwt-assertion", credentialInfo.getJwt())  // Adding JWT header
                .POST(HttpRequest.BodyPublishers.ofString(formParams))
                .build();

        // Make the HTTP call and process response
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                // Parse the response body (assuming TokenInfo is in JSON format)
                return parseTokenInfo(response.body());
            } else {
                logger.error("Failed to generate token, Status code: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Error occurred while generating token", e);
            return null;
        }
    }

    private String encodeParams(Map<String, String> params) {
        return params.entrySet()
                .stream()
                .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" +
                        URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }

    // Simulating JSON parsing for TokenInfo (use actual JSON parsing in real projects)
    private TokenInfo parseTokenInfo(String responseBody) {
        // Assuming TokenInfo has a simple constructor that takes the response
        // In practice, use a JSON library like Jackson to parse the response.
        return new TokenInfo(responseBody);
    }
}
