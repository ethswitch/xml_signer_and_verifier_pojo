package org.ips.xml.signer.xmlsigner.controller;

import org.ips.xml.signer.xmlsigner.models.TokenInfo;
import org.ips.xml.signer.xmlsigner.service.TokenGenerationManager;
import org.ips.xml.signer.xmlsigner.service.TokenGenerationManagerInt;


public class TokenGenerationController {


    private TokenGenerationManagerInt tokenGenerationManagerInt;


    public TokenGenerationController() {

        this.tokenGenerationManagerInt = new TokenGenerationManager();
    }


    public TokenInfo getJwt(String userName) {


        TokenInfo tokenInfo = null;
        try {
            tokenInfo = tokenGenerationManagerInt.getToken();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tokenInfo;
    }

}
