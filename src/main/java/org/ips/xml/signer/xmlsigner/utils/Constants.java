package org.ips.xml.signer.xmlsigner.utils;

public class Constants {

    public static final String PARTICIPANT_BIC="ETSETAA";


    public static final String CERTEFICATE_PATH="C:\\etswitch\\xmlSigner_2\\xmlSigner\\src\\main\\resources\\xml\\certnew.cer";
    public static final String PRIVATE_KEY_PATH="C:\\etswitch\\xmlSigner_2\\xmlSigner\\src\\main\\resources\\xml\\JWT_Private.key";


    public static final String USER_NAME="ets_bank";
    public static final String PASSWORD ="ets_bank1";
    public static final String GRANT_TYPE="password";
    public static final String ETS_IPS_URL="http://192.168.20.44:9001";
    public static final String ETS_CERT_IPS_URL="http://192.168.20.45:9001";
    public static final String ETS_IPS_TOKEN_URL=ETS_IPS_URL+"/v1/token";

    public static final String ETS_IPS_CERITIFICATE_URL=ETS_CERT_IPS_URL+"/v1/cert";
}
