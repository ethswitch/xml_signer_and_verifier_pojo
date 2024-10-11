package org.ips.xml.signer.xmlsigner.controller;

import org.ips.xml.signer.xmlsigner.models.TokenInfo;
import org.ips.xml.signer.xmlsigner.service.TokenGenerationManager;


public class TokenGenerationController {


    private TokenGenerationManager tokenGenerationManager;


    public TokenGenerationController() {

        this.tokenGenerationManager = new TokenGenerationManager();
    }


    public TokenInfo getJwt(String userName) {


        TokenInfo tokenInfo = null;
        try {
            tokenInfo = tokenGenerationManager.getToken();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tokenInfo;
    }

}
