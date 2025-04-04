package org.ips.xml.signer.xmlsigner.service.digestService;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.ips.xml.signer.xmlsigner.info.SignatureInfo;
import org.ips.xml.signer.xmlsigner.info.SignatureKeyInfo;
import org.ips.xml.signer.xmlsigner.utils.XMLFileUtility;
import org.ips.xml.signer.xmlsigner.utils.XmlSignUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

@Log4j2
public class DigestServiceImpl implements DigestService {
    public static String xadesNS = "http://uri.etsi.org/01903/v1.3.2#";
    public static String signatureID = "Sig1";
    public static String signedPropID = "SignP";


    private XMLFileUtility xmlFileUtility;

    private XmlSignUtil signUtil;


    public DigestServiceImpl() {
        this.xmlFileUtility =  new XMLFileUtility();
        this.signUtil = new XmlSignUtil();
    }

    @Override
    public String signDocument(String xmlString) {

        String signedXml = null;
        Document document = xmlFileUtility.createDocumentFromString(xmlString);
        Document signedDocument = null;
        SignatureInfo signatureInfo = xmlFileUtility.buildKeySignatureInfo();
        SignatureKeyInfo signatureKeyInfo = xmlFileUtility.buildSignaturePrivateKeyInfo();

        try {
            signedDocument = signUtil.sign(document, signatureInfo, signatureKeyInfo);
            signedXml = convertDocumentToString(signedDocument);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        signedXml = signedXml.replace("&#xD;", "");
        return signedXml;
    }

    public static Element createElement(Document doc, String tag, String prefix, String nsURI) {
        String qName = prefix == null ? tag : prefix + ":" + tag;
        return doc.createElementNS(nsURI, qName);
    }

    private String convertDocumentToString(Document doc) {
        String convertedString=null;
        try {

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            convertedString= writer.getBuffer().toString();
            return convertedString;
        } catch (Exception e) {
            log.error(e.getMessage());
            return convertedString;
        }
    }

    private Document createDocumentFromString(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
