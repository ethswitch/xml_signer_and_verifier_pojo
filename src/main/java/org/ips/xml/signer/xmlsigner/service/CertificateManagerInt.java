package org.ips.xml.signer.xmlsigner.service;

import org.ips.xml.signer.xmlsigner.models.CerteficateInformation;

import java.security.interfaces.RSAPublicKey;

public interface CertificateManagerInt {
    CerteficateInformation getCertificate(CerteficateInformation certeficateInformation);

    RSAPublicKey getPublicKeyForMessageOrginator(CerteficateInformation certeficateInformation);

    public void clearAllCache();
}
