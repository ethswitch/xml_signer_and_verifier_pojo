package org.ips.xml.signer.xmlsigner.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.ips.xml.signer.xmlsigner.crypto.CerteficateAndKeysUtility;
import org.ips.xml.signer.xmlsigner.models.JWTInfo;


import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAKey;
import java.util.Date;


public class JwtSigningUtils {
    CerteficateAndKeysUtility certeficateAndKeysUtility;
    int accessExpirationMs = 9600000;

    public JwtSigningUtils() {
        this.certeficateAndKeysUtility = new CerteficateAndKeysUtility();
    }

    public JWTInfo generateJwt(JWTInfo jwtInfo) throws NoSuchAlgorithmException, Exception {
        PrivateKey privateKey = certeficateAndKeysUtility.loadPrivateKey();
        Algorithm algorithm = Algorithm.RSA256((RSAKey) privateKey);
        X509Certificate key = certeficateAndKeysUtility.getStoredCerteficate();
        BigInteger serialNumber = key.getSerialNumber();
        String issuer = key.getIssuerX500Principal().getName();
        jwtInfo.setIssuer(issuer);
        jwtInfo.setSerialNumber(serialNumber);
        String jwtToken = JWT.create()
                .withIssuer(jwtInfo.getParticipantBic())
                .withClaim("cert_iss", issuer)
                .withClaim("cert_sn", String.valueOf(serialNumber))
                .withExpiresAt(new Date(System.currentTimeMillis() + 5000L))
                .withJWTId("11223312412321")
                .sign(algorithm);
        jwtInfo.setJwt(jwtToken);
        return jwtInfo;
    }

}
