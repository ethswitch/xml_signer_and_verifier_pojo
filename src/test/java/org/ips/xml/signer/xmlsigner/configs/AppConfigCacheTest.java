package org.ips.xml.signer.xmlsigner.configs;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigCacheTest {

    @Test
    void loadProperties() {

        try {
            // Load properties into cache from the file


            // Access properties from cache
            System.out.println("App Name: " + AppConfigCache.getProperty("spring.profiles.active"));
            System.out.println("App Version: " + AppConfigCache.getProperty("security.pki.privatekey.file.location"));
            System.out.println("Database URL: " + AppConfigCache.getProperty("spring.profiles.active"));

            // Check if a key exists in the cache
            if (AppConfigCache.containsKey("db.password")) {
                System.out.println("Password found in cache.");
            }

            // Update a property in cache
            AppConfigCache.setProperty("app.version", "2.0");
            System.out.println("Updated App Version: " + AppConfigCache.getProperty("app.version"));

        } catch (Exception e) {
            System.err.println("Error loading properties: " + e.getMessage());
        }

    }

    @Test
    void getProperty() {
    }

    @Test
    void containsKey() {
    }

    @Test
    void setProperty() {
    }

    @Test
    void removeProperty() {
    }
}