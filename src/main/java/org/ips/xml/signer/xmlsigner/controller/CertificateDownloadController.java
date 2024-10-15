package org.ips.xml.signer.xmlsigner.controller;

import org.ips.xml.signer.xmlsigner.models.CerteficateInformation;
import org.ips.xml.signer.xmlsigner.service.CertificateManagerInt;

import java.security.interfaces.RSAPublicKey;

public class CertificateDownloadController {
    private CertificateManagerInt certificateManager;

    public RSAPublicKey getPublicKeyForMessageOriginator(CerteficateInformation certeficateInformation) {
        RSAPublicKey publicKey = certificateManager.getPublicKeyForMessageOrginator(certeficateInformation);
        return publicKey;

    }
}
