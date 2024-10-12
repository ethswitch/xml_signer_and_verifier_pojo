package org.ips.xml.signer.xmlsigner.utils;

import org.ips.xml.signer.xmlsigner.configs.AppConfigCache;

public class Constants {

    public static  String PARTICIPANT_BIC="ETSETAA";


    public static  String CERTEFICATE_PATH="C:\\etswitch\\xmlSigner_2\\xmlSigner\\src\\main\\resources\\xml\\certnew.cer";
    public static  String PRIVATE_KEY_PATH="C:\\etswitch\\xmlSigner_2\\xmlSigner\\src\\main\\resources\\xml\\JWT_Private.key";


    public static  String USER_NAME="ets_bank";
    public static  String PASSWORD ="ets_bank1";
    public static  String GRANT_TYPE="password";
    public static  String ETS_IPS_URL="http://192.168.20.44:9001";
    public static  String ETS_CERT_IPS_URL="http://192.168.20.45:9001";
    public static  String ETS_IPS_TOKEN_URL=ETS_IPS_URL+"/v1/token";

    public static  String ETS_IPS_CERITIFICATE_URL=ETS_CERT_IPS_URL+"/v1/cert";
    
    static {
        intialize();
    }

    private static void intialize() {
        PARTICIPANT_BIC= AppConfigCache.getProperty("ips.participant.bic");


        CERTEFICATE_PATH= AppConfigCache.getProperty("security.pki.certificate.file.location");
        PRIVATE_KEY_PATH= AppConfigCache.getProperty("security.pki.privatekey.file.location");


        USER_NAME= AppConfigCache.getProperty("ets.ips.userName");
        PASSWORD = AppConfigCache.getProperty("ets.ips.password");
        GRANT_TYPE= AppConfigCache.getProperty("ets.ips.grantType");    

        ETS_IPS_TOKEN_URL= AppConfigCache.getProperty("ets.ips.token.url");
        ETS_IPS_CERITIFICATE_URL= AppConfigCache.getProperty("ets.ips.certificate.download.url");
    }
}
