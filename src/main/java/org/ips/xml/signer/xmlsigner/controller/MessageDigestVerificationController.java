package org.ips.xml.signer.xmlsigner.controller;


import org.ips.xml.signer.xmlsigner.service.digestService.XMLDigestVerifier;
import org.ips.xml.signer.xmlsigner.service.digestService.XMLDigestVerifierImpl;


public class MessageDigestVerificationController {

    private XMLDigestVerifier digestVerifier;

    public MessageDigestVerificationController() {
        this.digestVerifier = new XMLDigestVerifierImpl();
    }


    public String verifyXml( String request) {

        String xmlResponse = digestVerifier.verify(request);
        xmlResponse = xmlResponse.replace("&#xD;", "");
        return xmlResponse;
    }


    public String evictCach() {

        digestVerifier.clearCache();
        return "evictede succ";

    }
}
