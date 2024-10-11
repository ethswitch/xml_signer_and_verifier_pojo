package org.ips.xml.signer.xmlsigner.utils;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.ips.xml.signer.xmlsigner.crypto.CerteficateAndKeysUtility;
import org.ips.xml.signer.xmlsigner.info.ReferenceSignInfo;
import org.ips.xml.signer.xmlsigner.info.SignatureInfo;
import org.ips.xml.signer.xmlsigner.info.SignatureKeyInfo;
import org.ips.xml.signer.xmlsigner.models.CerteficateInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.PrivateKey;
import java.util.UUID;



@Log4j2
@NoArgsConstructor
public class XMLFileUtility {
    private static final Logger logger = LoggerFactory.getLogger(XMLFileUtility.class);

    private CerteficateAndKeysUtility usageUtil;


    public XMLFileUtility(CerteficateAndKeysUtility usageUtil) {
        this.usageUtil = usageUtil;
    }

    public Document loadDocument(InputStream inputStream) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = null;
        try {
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            doc =
                    dbf.newDocumentBuilder().
                            parse(inputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        return doc;
    }

    public void writeToFile(InputStream inputStream, String filePath) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
            os.write(inputStream.readAllBytes());
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

        }

    }

    public InputStream documentToInputStream(Document xmlDocument) throws TransformerException {

        Source xmlSource = new DOMSource(xmlDocument);
        TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        Transformer transformer = transformerFactory.newTransformer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Result outputTarget = new StreamResult(outputStream);
        transformer.transform(xmlSource, outputTarget);
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return inputStream;
    }

    public static String generateID() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return uuidAsString;
    }


    public ReferenceSignInfo buildDocumentReferenceSignInfo() {
        return ReferenceSignInfo.builder().
                transformAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#").
                digestMethodAlgorithm("http://www.w3.org/2001/04/xmlenc#sha256").
                build();
    }

    public ReferenceSignInfo buildAppHdrReferenceSignInfo() {
        return ReferenceSignInfo.builder().
                transformAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#").
                digestMethodAlgorithm("http://www.w3.org/2001/04/xmlenc#sha256").
                build();
    }

    public ReferenceSignInfo buildKeyReferenceSignInfo() {
        return ReferenceSignInfo.builder().
                transformAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#").
                digestMethodAlgorithm("http://www.w3.org/2001/04/xmlenc#sha256").
                build();
    }

    public SignatureInfo buildKeySignatureInfo() {
        SignatureInfo signatureInfo;
        signatureInfo = SignatureInfo.builder().
                signatureMethodAlgorithm("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256").
                signatureCanonicalizationMethodAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#").
                signatureExclusionTransformer("http://www.w3.org/2001/10/xml-exc-c14n#").
                appHdrReferenceSignInfo(buildAppHdrReferenceSignInfo()).
                documentReferenceSignInfo(buildDocumentReferenceSignInfo()).
                keyReferenceSignInfo(buildKeyReferenceSignInfo()).
                build();
        return signatureInfo;

    }

    public SignatureKeyInfo buildSignaturePrivateKeyInfo() {
        PrivateKey privateKey = this.usageUtil.loadPrivateKey();
        SignatureKeyInfo signatureKeyInfo = SignatureKeyInfo.builder().privateKey(privateKey).build();
        return signatureKeyInfo;
    }

    public String convertDocumentToString(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Document createDocumentFromString(String xmlString) {
        Document xmlDocument = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;

        try {
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            builder = factory.newDocumentBuilder();
            xmlDocument = builder.parse(new InputSource(new StringReader(xmlString)));
            removeEmptyTags(xmlDocument);
            return xmlDocument;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private static void removeEmptyTags(Node node) {
        NodeList childNodes = node.getChildNodes();
        for (int i = childNodes.getLength() - 1; i >= 0; i--) {
            Node child = childNodes.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                removeEmptyTags(child);
                if (!child.hasChildNodes() && child.getTextContent().trim().isEmpty() && !child.hasAttributes()) {
                    node.removeChild(child);
                }
            }
        }
    }


public CerteficateInformation parseCerteficateFromDocument(Document doc) {
    Node serianNameNode = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "X509IssuerName").item(0);
    Node serianNumberNode = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "X509SerialNumber").item(0);
    String serialName = serianNameNode.getTextContent();
    String serialNumber = serianNumberNode.getTextContent();
    CerteficateInformation certeficateInformation = new CerteficateInformation();
    certeficateInformation.setCertificateIssuer(serialName);
    certeficateInformation.setCertificateSerialNumber(serialNumber);
    return certeficateInformation;
}

public String getMessageType(Document doc) {
    NamedNodeMap namedNodeMap = doc.getFirstChild().getAttributes();
    Node messageTypeNode = namedNodeMap.getNamedItem("xmlns:document");
    String messageType = messageTypeNode.getTextContent();
    return messageType;
}

}
