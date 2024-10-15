package org.ips.xml.signer.xmlsigner.service.digestService;

import org.ips.xml.signer.xmlsigner.models.CerteficateInformation;

import org.w3c.dom.Document;

public interface XMLDigestVerifier {

    public String verify();

    public CerteficateInformation parseCerteficateFromDocument();

    void clearCache();
}
