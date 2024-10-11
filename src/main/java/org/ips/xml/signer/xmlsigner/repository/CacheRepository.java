package org.ips.xml.signer.xmlsigner.repository;

import java.util.Optional;

/**
 * CacheRepository
 */
public interface CacheRepository {

    void put(String key, String value);


    Optional<String> get(String key);

    void remove(String key);

}