package org.ips.xml.signer.xmlsigner;

import lombok.extern.log4j.Log4j2;
import org.ips.xml.signer.xmlsigner.crypto.KryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collections;

@Log4j2
public class XMLDigitalSignatureUtil {
    Logger logger = LoggerFactory.getLogger(XMLDigitalSignatureUtil.class);

    private Document getXmlDocument(String xmlFilePath) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try {
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            doc = dbf.newDocumentBuilder().parse(new FileInputStream(xmlFilePath));
        } catch (ParserConfigurationException ex) {
            logger.error(ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return doc;
    }

    private javax.xml.crypto.dsig.keyinfo.KeyInfo getKeyInfo(XMLSignatureFactory xmlSigFactory, String publicKeyPath) {
        javax.xml.crypto.dsig.keyinfo.KeyInfo keyInfo = null;
        KeyValue keyValue = null;
        PublicKey publicKey = new KryptoUtil().getStoredPublicKey(publicKeyPath);
        KeyInfoFactory keyInfoFact = xmlSigFactory.getKeyInfoFactory();

        try {
            keyValue = keyInfoFact.newKeyValue(publicKey);
            keyInfo = keyInfoFact.newKeyInfo(Collections.singletonList(keyValue));
        } catch (KeyException ex) {
            logger.error(ex.getMessage());
        }

        return keyInfo;
    }

    private void storeSignedDoc(Document doc, String destnSignedXmlFilePath) {
        TransformerFactory transFactory = TransformerFactory.newDefaultInstance();
        Transformer trans = null;
        try {
            transFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            transFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            trans = transFactory.newTransformer();
            StreamResult streamRes = new StreamResult(new File(destnSignedXmlFilePath));
            trans.transform(new DOMSource(doc), streamRes);
        } catch (TransformerException ex) {
            logger.error(ex.getMessage());
        }
        logger.info("XML file with attached digital signature generated successfully ...");
    }

    public void generateXMLDigitalSignature(String originalXmlFilePath,
                                            String destnSignedXmlFilePath, String privateKeyFilePath, String publicKeyFilePath) {
        try {
            //Get the XML Document object
            Document doc = getXmlDocument(originalXmlFilePath);
            if(doc!=null) {
                //Create XML Signature Factory
                XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance("DOM");
                PrivateKey privateKey = new KryptoUtil().getStoredPrivateKey(privateKeyFilePath);
                DOMSignContext domSignCtx = new DOMSignContext(privateKey, doc.getDocumentElement());
                Reference ref = null;
                SignedInfo signedInfo = null;
                ref = xmlSigFactory.newReference("", xmlSigFactory.newDigestMethod(DigestMethod.SHA1, null),
                        Collections.singletonList(xmlSigFactory.newTransform(Transform.ENVELOPED,
                                (TransformParameterSpec) null)), null, null);
                signedInfo = xmlSigFactory.newSignedInfo(
                        xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                                (C14NMethodParameterSpec) null),
                        xmlSigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                        Collections.singletonList(ref));
                //Pass the Public Key File Path
                KeyInfo keyInfo = getKeyInfo(xmlSigFactory, publicKeyFilePath);
                //Create a new XML Signature
                XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);
                //Sign the document
                xmlSignature.sign(domSignCtx);
                storeSignedDoc(doc, destnSignedXmlFilePath);
            }
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        //Store the digitally signed document inta a location

    }
}
