package org.ips.xml.signer.xmlsigner.service.digestService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.ips.xml.signer.xmlsigner.crypto.CerteficateAndKeysUtility;
import org.ips.xml.signer.xmlsigner.models.CerteficateInformation;
import org.ips.xml.signer.xmlsigner.service.CertificateManager;
import org.ips.xml.signer.xmlsigner.service.CertificateManagerInt;
import org.ips.xml.signer.xmlsigner.utils.XMLFileUtility;
import org.ips.xml.signer.xmlsigner.utils.XmlSignUtil;

import org.w3c.dom.Document;

import java.security.interfaces.RSAPublicKey;

@Setter
@Log4j2
public class XMLDigestVerifierImpl implements XMLDigestVerifier {

    private XMLFileUtility xmlFileUtility;

    private CertificateManagerInt certificateManager;

    private XmlSignUtil signUtil;

    private Document document;

    private CerteficateInformation certeficateInformation;


    public XMLDigestVerifierImpl(String signedXml) {
        this.xmlFileUtility = new XMLFileUtility();
        this.certificateManager = new CertificateManager();
        this.signUtil = new XmlSignUtil();
        this.document = xmlFileUtility.createDocumentFromString(signedXml);
    }

    @Override
    public String verify() {
        boolean validDocuemnt = false;

        try {
            this.parseCerteficateFromDocument();
            RSAPublicKey publicKey = certificateManager.getPublicKeyForMessageOrginator(certeficateInformation);
            validDocuemnt = signUtil.verify(document, publicKey);


        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return String.valueOf(validDocuemnt);
    }

    @Override
    public CerteficateInformation parseCerteficateFromDocument() {
        this.certeficateInformation = xmlFileUtility.parseCerteficateFromDocument(this.document);
        return certeficateInformation;
    }

    @Override
    public void clearCache() {
        certificateManager.clearAllCache();
    }
}
