package org.ips.xml.signer.xmlsigner.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigestControllerTest {
private DigestController digestController= new DigestController();
private String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
        "<FPEnvelope xmlns:header=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.03\" xmlns:document=\"urn:iso:std:iso:20022:tech:xsd:acmt.023.001.03\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:verification_request\">\n" +
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
        "\t</header:AppHdr>\n" +
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
    void digestXml() {
        String signedXml= this.digestController.digestXml(this.xml);
    }
}