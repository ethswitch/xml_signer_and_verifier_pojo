package org.ips.xml.signer.xmlsigner.service.apiClient;

import org.ips.xml.signer.xmlsigner.models.CerteficateInformation;
import org.ips.xml.signer.xmlsigner.models.CertificateResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class CerteficatClientService {

    private HttpClient httpClient;
    private URI requestSubmittingUri;
    private String requestSubmittingUrl;

    // Constructor
    public CerteficatClientService(String requestSubmittingUrl) {
        this.httpClient = HttpClient.newHttpClient(); // Using Java 11+ HttpClient
        this.requestSubmittingUrl = requestSubmittingUrl;
    }

    // Method to prepare URIs and headers, similar to 'create' in Spring
    private void create() {
        requestSubmittingUri = URI.create(requestSubmittingUrl);
    }

    // Download certificate method, replacing RestTemplate with HttpClient
    public CerteficateInformation downloadCerteficate(CerteficateInformation certeficateInformation) throws Exception {
        create();

        String url = certeficateInformation.getCerteficateDownloadUrl() +
                "?cert_iss=" + certeficateInformation.getCertificateIssuer() +
                "&cert_sn=" + certeficateInformation.getCertificateSerialNumber();
        url=url.replaceAll(" ","%20")
                .replaceAll(",","%2C")
//                .replaceAll("=","%3D")
        ;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Bearer " + certeficateInformation.getValidToken())
                .GET() // Using GET method
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Assuming the response body contains the certificate in a way you can parse and update the model
        if (response.statusCode() == 200) {
            CertificateResponse certificateResponse= CertificateResponse.convert(response.body());
            certeficateInformation.setCertificate(certificateResponse.getCertificate());
        } else {
            System.out.println("Failed to download certificate: " + response.statusCode());
        }

        return certeficateInformation;
    }

    // Send message method using HttpClient POST
    public String sendMessage(String message, String validToken) throws Exception {
        create();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestSubmittingUri)
                .header("Content-Type", "text/xml")
                .header("Accept", "text/xml")
                .header("Custom-Header", "Custom-Value")
                .header("Authorization", "Bearer " + validToken)
                .POST(HttpRequest.BodyPublishers.ofString(message)) // Using POST with message body
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            System.out.println("Failed to send message: " + response.statusCode());
            return null;
        }
    }
}