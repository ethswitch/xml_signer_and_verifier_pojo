package org.ips.xml.signer.xmlsigner.controller;

import org.ips.xml.signer.xmlsigner.service.digestService.DigestService;
import org.ips.xml.signer.xmlsigner.service.digestService.DigestServiceImpl;
import org.ips.xml.signer.xmlsigner.service.digestService.XMLDigestVerifier;
import org.ips.xml.signer.xmlsigner.utils.JwtSigningUtils;


import java.security.NoSuchAlgorithmException;


public class DigestController {
    public DigestController() {

        this.digestService= new DigestServiceImpl();

    }

    private DigestService digestService;

    DigestController(
            DigestService digestService,
            XMLDigestVerifier digestVerifier,
            JwtSigningUtils jwtSigningUtils
         ) {
        this.digestService = digestService;

    }


    public String digestXml(String request) {

        String xmlResponse = digestService.signDocument(request);
        xmlResponse = xmlResponse.replace("&#xD;", "");
        return xmlResponse;
    }


}



