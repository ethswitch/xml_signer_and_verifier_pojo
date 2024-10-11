package org.ips.xml.signer.xmlsigner.controller;

import org.ips.xml.signer.xmlsigner.models.JWTInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JWTControllerTest {

    private JWTController jwtController = new JWTController();;

    @BeforeEach
    void setUp() {
        this.jwtController= new JWTController();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getJwt() {
        JWTInfo jwtInfo= this.jwtController.getJwt("teklu");
    }

}