package org.ips.xml.signer.xmlsigner.service;

import org.ips.xml.signer.xmlsigner.models.JWTInfo;
import org.ips.xml.signer.xmlsigner.models.ParticipantCredentialInfo;
import org.ips.xml.signer.xmlsigner.models.TokenInfo;
import org.ips.xml.signer.xmlsigner.service.apiClient.TokenGenerationClientService;



public class TokenGenerationManager {



    private String tokenUrl;

    private String grantType;

    private String userName;

    private String password;

    private String signeJWT;
    TokenGenerationClientService service;
    JWTManager jwtManager;


    public TokenGenerationManager() {
        this.service = new TokenGenerationClientService();
        this.jwtManager = new JWTManager();
    }

    public TokenInfo getToken() {
        ParticipantCredentialInfo credentialInfo = new ParticipantCredentialInfo();
        TokenInfo tokenInfo = null;
        JWTInfo jwtInfo = jwtManager.getJWT();
        if (jwtInfo != null) {
            credentialInfo.setUserName(userName);
            credentialInfo.setPassword(password);
            credentialInfo.setGrantType(grantType);
            credentialInfo.setTokenGenerationPath(tokenUrl);
            credentialInfo.setJwt(jwtInfo.getJwt());
            tokenInfo = service.generateToken(credentialInfo);
        }
        return tokenInfo;
    }
}
