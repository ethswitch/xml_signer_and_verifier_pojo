package org.ips.xml.signer.xmlsigner.service;

import org.ips.xml.signer.xmlsigner.models.CerteficateInformation;
import org.ips.xml.signer.xmlsigner.models.TokenInfo;

import org.ips.xml.signer.xmlsigner.service.apiClient.CerteficatClientService;
import org.ips.xml.signer.xmlsigner.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;


public class CertificateManager implements CertificateManagerInt {
    private static final Logger logger = LoggerFactory.getLogger(CertificateManager.class);

    private TokenGenerationManagerInt tokenGenerationManagerInt;
    private CerteficatClientService certeficatClientService;
    private String certeficateDownloadUrl= Constants.ETS_IPS_CERITIFICATE_URL;

    public CertificateManager() {

        this.tokenGenerationManagerInt = new TokenGenerationManager();
        this.certeficatClientService = new CerteficatClientService(certeficateDownloadUrl);
    }



    public void clearAllCache() {
        System.out.println("clearing all catche");
    }


    @Override
    public CerteficateInformation getCertificate(CerteficateInformation certeficateInformation) {

        CerteficateInformation cachedCeretficate =null;
        TokenInfo tokenInfo = null;

            logger.info("calling the certeficate api");
            try {
                tokenInfo = tokenGenerationManagerInt.getToken();
                certeficateInformation.setValidToken(tokenInfo.getAccess_token());
                certeficateInformation.setCerteficateDownloadUrl(this.certeficateDownloadUrl);
                CerteficateInformation cert = this.certeficatClientService.downloadCerteficate(certeficateInformation);
                if (cert != null) {
                    certeficateInformation.setCertificate(cert.getCertificate());

                    logger.info(cert.toString());
                }
            }catch (Exception ex){
                logger.error("calling the certeficate api");
            }

        return certeficateInformation;

    }
    @Override
    public RSAPublicKey getPublicKeyForMessageOrginator(CerteficateInformation certeficateInformation) {
        RSAPublicKey publicKey = null;
        CerteficateInformation certeficate = null;
        X509Certificate x509Certificate = null;
        try {
            certeficate = this.getCertificate(certeficateInformation);
            if (certeficate != null) {
                x509Certificate = convertBase64StringToCerteficate(certeficate);
                publicKey = (RSAPublicKey) x509Certificate.getPublicKey();

            }
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }

        return publicKey;

    }


    public X509Certificate convertBase64StringToCerteficate(CerteficateInformation certeficate) throws CertificateException {
        String certificateString = certeficate.getCertificate();
        X509Certificate certificate = null;
        CertificateFactory cf = null;
        try {
            if (certificateString != null && !certificateString.trim().isEmpty()) {
                certificateString = certificateString.replace("-----BEGIN CERTIFICATE-----", "")
                        .replace("-----END CERTIFICATE-----", ""); // NEED FOR PEM FORMAT CERT STRING
                byte[] certificateData = Base64.getDecoder().decode(certificateString);
                cf = CertificateFactory.getInstance("X509");
                certificate = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certificateData));

            }
        } catch (CertificateException e) {
            throw new CertificateException(e);
        }
        return certificate;
    }

}
