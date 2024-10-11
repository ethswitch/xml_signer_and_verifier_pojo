package org.ips.xml.signer.xmlsigner.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageDigestVerificationControllerTest {
    private MessageDigestVerificationController verificationController=new MessageDigestVerificationController();

    private String signedXml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><FPEnvelope xmlns=\"urn:iso:std:iso:20022:tech:xsd:verification_request\" xmlns:document=\"urn:iso:std:iso:20022:tech:xsd:acmt.023.001.03\" xmlns:header=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.03\">\n" +
            "\t<header:AppHdr>\n" +
            "\t\t<header:Fr>\n" +
            "\t\t\t<header:FIId>\n" +
            "\t\t\t\t<header:FinInstnId>\n" +
            "\t\t\t\t\t<header:Othr>\n" +
            "\t\t\t\t\t\t<header:Id>CBETETAAXXX</header:Id>\n" +
            "\t\t\t\t\t</header:Othr>\n" +
            "\t\t\t\t</header:FinInstnId>\n" +
            "\t\t\t</header:FIId>\n" +
            "\t\t</header:Fr>\n" +
            "\t\t<header:To>\n" +
            "\t\t\t<header:FIId>\n" +
            "\t\t\t\t<header:FinInstnId>\n" +
            "\t\t\t\t\t<header:Othr>\n" +
            "\t\t\t\t\t\t<header:Id>ABYSETAAXXX123</header:Id>\n" +
            "\t\t\t\t\t</header:Othr>\n" +
            "\t\t\t\t</header:FinInstnId>\n" +
            "\t\t\t</header:FIId>\n" +
            "\t\t</header:To>\n" +
            "\t\t<header:BizMsgIdr>CBETETAAXXX2024020902230282590430</header:BizMsgIdr>\n" +
            "\t\t<header:MsgDefIdr>acmt.023.001.03</header:MsgDefIdr>\n" +
            "\t\t<header:CreDt>2023-06-24T00:00:00.000Z</header:CreDt>\n" +
            "\t<document:Sgntr xmlns:document=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.03\"><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
            "<ds:SignedInfo>\n" +
            "<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
            "<ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/>\n" +
            "<ds:Reference URI=\"#_601ab009-71d6-44c7-9e24-595fb8349a09\">\n" +
            "<ds:Transforms>\n" +
            "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
            "</ds:Transforms>\n" +
            "<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" +
            "<ds:DigestValue>3LAXwui1l8YWBL5QwQg0zVgRcEpa1LDoB2KeFrbFA/c=</ds:DigestValue>\n" +
            "</ds:Reference>\n" +
            "<ds:Reference Type=\"http://uri.etsi.org/01903/v1.3.2#SignedProperties\" URI=\"#1ef2fad7-04b8-418e-976f-acb428d839b1\">\n" +
            "<ds:Transforms>\n" +
            "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
            "</ds:Transforms>\n" +
            "<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" +
            "<ds:DigestValue>QfkM94qJkixmBFzKbPZtnAke1M5jjoZ8/mrybZyOaR0=</ds:DigestValue>\n" +
            "</ds:Reference>\n" +
            "<ds:Reference>\n" +
            "<ds:Transforms>\n" +
            "<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
            "</ds:Transforms>\n" +
            "<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" +
            "<ds:DigestValue>MD1k/1jXYCSQONrdRTDC3BaJvw2icicgYto1nZmVAKM=</ds:DigestValue>\n" +
            "</ds:Reference>\n" +
            "</ds:SignedInfo>\n" +
            "<ds:SignatureValue>\n" +
            "FRecLNcncDv1ZnZXV9r2VKvrnL+iWAyKLuTv8yQHdoGKb3NOkrOgJs5uTxY1cXEn0DspTeIJEbF3\n" +
            "PRSLhAGKFOSBFLqSgZg1EnXh2luK/3F126gNkZO2G7qNW3L7KWHDd5YCrqF/5+T6QAQIsLt5alUe\n" +
            "L9zW2FrXNj+cGjpQplEdR7UfFL8D/9spNKhmKQFSE2cZupRjiAywF6Oz5246WFMVriA3MhwN87ZR\n" +
            "DSFpMHvsPvi3APiETu/LgvfcIIbhF7WObPx2PyLB35rsAsi5yKBvnRJMnGT/XK7/n7hGrsjtBhPY\n" +
            "vKQQBWDJDQWHeIYEdXPs2BCtWPhatJcEb1MkUQ==\n" +
            "</ds:SignatureValue>\n" +
            "<ds:KeyInfo Id=\"_601ab009-71d6-44c7-9e24-595fb8349a09\">\n" +
            "<ds:X509Data>\n" +
            "<ds:X509IssuerSerial>\n" +
            "<ds:X509IssuerName>CN=TEST ETS IPS Issuing CA,O=EthSwitch,C=ET</ds:X509IssuerName>\n" +
            "<ds:X509SerialNumber>423714158916038225117618563593098199125983259</ds:X509SerialNumber>\n" +
            "</ds:X509IssuerSerial>\n" +
            "</ds:X509Data>\n" +
            "</ds:KeyInfo>\n" +
            "<ds:Object><xades:QualifyingProperties xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\"><xades:SignedProperties Id=\"1ef2fad7-04b8-418e-976f-acb428d839b1\"><xades:SignedSignatureProperties><xades:SigningTime>2024-10-11T11:27:33.325314200Z</xades:SigningTime></xades:SignedSignatureProperties></xades:SignedProperties></xades:QualifyingProperties></ds:Object></ds:Signature></document:Sgntr></header:AppHdr>\n" +
            "\t<document:Document>\n" +
            "\t\t<document:IdVrfctnReq>\n" +
            "\t\t\t<document:Assgnmt>\n" +
            "\t\t\t\t<document:MsgId>CBETETAAXXX2024020902230282590430</document:MsgId>\n" +
            "\t\t\t\t<document:CreDtTm>2023-06-24T00:00:00.000+03:00</document:CreDtTm>\n" +
            "\t\t\t\t<document:Assgnr>\n" +
            "\t\t\t\t\t<document:Agt>\n" +
            "\t\t\t\t\t\t<document:FinInstnId>\n" +
            "\t\t\t\t\t\t\t<document:Othr>\n" +
            "\t\t\t\t\t\t\t\t<document:Id>CBETETAAXXX</document:Id>\n" +
            "\t\t\t\t\t\t\t</document:Othr>\n" +
            "\t\t\t\t\t\t</document:FinInstnId>\n" +
            "\t\t\t\t\t</document:Agt>\n" +
            "\t\t\t\t</document:Assgnr>\n" +
            "\t\t\t\t<document:Assgne>\n" +
            "\t\t\t\t\t<document:Agt>\n" +
            "\t\t\t\t\t\t<document:FinInstnId>\n" +
            "\t\t\t\t\t\t\t<document:Othr>\n" +
            "\t\t\t\t\t\t\t\t<document:Id>ABYSETAAXXX123</document:Id>\n" +
            "\t\t\t\t\t\t\t</document:Othr>\n" +
            "\t\t\t\t\t\t</document:FinInstnId>\n" +
            "\t\t\t\t\t</document:Agt>\n" +
            "\t\t\t\t</document:Assgne>\n" +
            "\t\t\t</document:Assgnmt>\n" +
            "\t\t\t<document:Vrfctn>\n" +
            "\t\t\t\t<document:Id>CBETETAAXXX3361051669760</document:Id>\n" +
            "\t\t\t\t<document:PtyAndAcctId>\n" +
            "\t\t\t\t\t<document:Acct>\n" +
            "\t\t\t\t\t\t<document:Id>\n" +
            "\t\t\t\t\t\t\t<document:Othr>\n" +
            "\t\t\t\t\t\t\t\t<document:Id>1234567890</document:Id>\n" +
            "\t\t\t\t\t\t\t\t<document:SchmeNm>\n" +
            "\t\t\t\t\t\t\t\t\t<document:Prtry>ACCT</document:Prtry>\n" +
            "\t\t\t\t\t\t\t\t</document:SchmeNm>\n" +
            "\t\t\t\t\t\t\t</document:Othr>\n" +
            "\t\t\t\t\t\t</document:Id>\n" +
            "\t\t\t\t\t</document:Acct>\n" +
            "\t\t\t\t</document:PtyAndAcctId>\n" +
            "\t\t\t</document:Vrfctn>\n" +
            "\t\t</document:IdVrfctnReq>\n" +
            "\t</document:Document>\n" +
            "</FPEnvelope>";

    @Test
    void verifyXml() {
        String verified = this.verificationController.verifyXml(signedXml);
    }
}