package org.ips.xml.signer.xmlsigner.service;

import org.ips.xml.signer.xmlsigner.models.JWTInfo;
import org.ips.xml.signer.xmlsigner.utils.Constants;
import org.ips.xml.signer.xmlsigner.utils.JwtSigningUtils;


public class JWTManager {

    private String participantBic = Constants.PARTICIPANT_BIC;

    private JwtSigningUtils jwtSigningUtils;


    public JWTManager() {
        this.jwtSigningUtils = new JwtSigningUtils();

    }

    public JWTInfo getJWT() {
        JWTInfo jwtInfo = new JWTInfo();
        try {
            jwtInfo.setParticipantBic(participantBic);
            jwtSigningUtils.generateJwt(jwtInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jwtInfo;
    }
}
