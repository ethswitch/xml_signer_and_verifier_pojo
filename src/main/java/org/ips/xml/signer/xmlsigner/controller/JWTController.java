package org.ips.xml.signer.xmlsigner.controller;

import org.ips.xml.signer.xmlsigner.models.JWTInfo;
import org.ips.xml.signer.xmlsigner.service.JWTManager;
import org.ips.xml.signer.xmlsigner.utils.JwtSigningUtils;


import java.security.NoSuchAlgorithmException;


public class JWTController {


    private JWTManager jwtManager;


    public JWTController() {

        this.jwtManager = new JWTManager();
    }


    /**
     * @param userName
     * @return
     */
    public JWTInfo getJwt( String userName) {


        JWTInfo jwtInfo = null;
        try {
            jwtInfo = jwtManager.getJWT();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jwtInfo;
    }

}
