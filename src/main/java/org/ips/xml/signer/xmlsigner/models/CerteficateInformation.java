package org.ips.xml.signer.xmlsigner.models;

import lombok.Data;

@Data
public class CerteficateInformation {
    private String certificateIssuer;
    private String certificateSerialNumber;
    private String certificate;
    private String certeficateDownloadUrl;
    private String validToken;
}
