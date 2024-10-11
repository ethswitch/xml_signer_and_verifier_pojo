package org.ips.xml.signer.xmlsigner.utils;

import java.util.UUID;
import com.mifmif.common.regex.Generex;

public class IdGenerationUtil {

    public static String UUID36(String bic) {
        String uuidStr = UUID.randomUUID().toString();
//        int padding = bic.length();
//        uuidStr = ("m_").concat(uuidStr).replace("-", "");
        return uuidStr;
    }

    public static String UUID35(String bic) {
        String uuidStr = UUID.randomUUID().toString().substring(0,35);

        return uuidStr;
    }

    public static String messageIds(String bic) {
        String uuidStr = UUID.randomUUID().toString().substring(0,20);
        int padding = bic.length();
        uuidStr = bic.concat(uuidStr).replace("-", "");
        return uuidStr;
    }

    public static String generateId(String bic) {
        String uuidStr = UUID.randomUUID().toString().substring(0,20);
        int padding = bic.length();
        uuidStr = bic.concat(uuidStr).replace("-", "");
        return uuidStr;
    }

    public static String generateStringFromRegex(String regex) {
        Generex generex = new Generex(regex);
        return generex.random();
    }
    public static void main(String[] args) {
        String regex = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}";
        String generatedString = generateStringFromRegex(regex);
        System.out.println("Generated String: " + generatedString);
    }
}

