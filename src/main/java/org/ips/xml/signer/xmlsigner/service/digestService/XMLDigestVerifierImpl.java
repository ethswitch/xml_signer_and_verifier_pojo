package org.ips.xml.signer.xmlsigner.service.digestService;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.ips.xml.signer.xmlsigner.crypto.CerteficateAndKeysUtility;
import org.ips.xml.signer.xmlsigner.models.CerteficateInformation;
import org.ips.xml.signer.xmlsigner.service.CertificateManager;
import org.ips.xml.signer.xmlsigner.utils.XMLFileUtility;
import org.ips.xml.signer.xmlsigner.utils.XmlSignUtil;

import org.w3c.dom.Document;

@Setter
@Log4j2


public class XMLDigestVerifierImpl implements XMLDigestVerifier {

    XMLFileUtility xmlFileUtility;

    CertificateManager certificateManager;

    private XmlSignUtil signUtil;


    public XMLDigestVerifierImpl() {
        this.xmlFileUtility = new XMLFileUtility();
        this.certificateManager = new CertificateManager();
        this.signUtil = new XmlSignUtil();
    }

    @Override
    public String verify(String signedXml) {

        Document document = xmlFileUtility.createDocumentFromString(signedXml);

        boolean validDocuemnt = false;

        try {
            CerteficateInformation certeficateInformation = xmlFileUtility.parseCerteficateFromDocument(document);
            validDocuemnt = signUtil.verify(document,certificateManager.getPublicKeyForMessageOrginator(certeficateInformation));


        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return String.valueOf(validDocuemnt);
    }

    @Override
    public void clearCache() {
        certificateManager.clearAllCache();
    }
}
