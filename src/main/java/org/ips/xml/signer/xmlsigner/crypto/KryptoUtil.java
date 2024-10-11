package org.ips.xml.signer.xmlsigner.crypto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * This class is used as a cryptographic utility.
 *
 * @author <a href="mailto:debadatta.mishra@gmail.com">Debadatta Mishra</a>
 * @since 2013
 */
public class KryptoUtil {
    private static final Logger logger = LoggerFactory.getLogger(KryptoUtil.class);

    /**
     * Name of the algorithm
     */
    private static final String ALGORITHM = "RSA";

    /**
     * This method is used to generate key pair based upon the provided
     * algorithm
     *
     * @return KeyPair
     */
    private KeyPair generateKeyPairs() {
        KeyPair keyPair = null;
        KeyPairGenerator keyGen;
        try {
            keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(2048);
            keyPair = keyGen.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }
        return keyPair;
    }

    /**
     * Method used to store Private and Public Keys inside a directory
     *
     * @param dirPath to store the keys
     */
    public void storeKeyPairs(String dirPath) {
        KeyPair keyPair = null;
        keyPair = generateKeyPairs();
        if (keyPair != null) {
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            storeKeys(dirPath + File.separator + "publickey.key", publicKey);
            storeKeys(dirPath + File.separator + "privatekey.key", privateKey);
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Method used to store the key(Public/Private)
     *
     * @param filePath , name of the file
     * @param key
     */
    private void storeKeys(String filePath, Key key) {
        byte[] keyBytes = key.getEncoded();
        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(filePath);
            outStream.write(keyBytes);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    /**
     * Method used to retrieve the keys in the form byte array
     *
     * @param filePath of the key
     * @return byte array
     */
    private byte[] getKeyData(String filePath) {
        File file = new File(filePath);
        byte[] buffer = new byte[(int) file.length()];
        FileInputStream fis = null;
        int size = 0;
        try {
            fis = new FileInputStream(file);
            size = fis.read(buffer);
            if (size <= 0) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return buffer;
    }

    /**
     * Method used to get the generated Private Key
     *
     * @param filePath of the PrivateKey file
     * @return PrivateKey
     */
    public PrivateKey getStoredPrivateKey(String filePath) {
        PrivateKey privateKey = null;
        byte[] keydata = getKeyData(filePath);
        PKCS8EncodedKeySpec encodedPrivateKey = new PKCS8EncodedKeySpec(keydata);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            if (keyFactory != null) {
                privateKey = keyFactory.generatePrivate(encodedPrivateKey);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return privateKey;
    }

    /**
     * Method used to get the generated Public Key
     *
     * @param filePath of the PublicKey file
     * @return PublicKey
     */
    public PublicKey getStoredPublicKey(String filePath) {
        PublicKey publicKey = null;
        byte[] keydata = getKeyData(filePath);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            if (keyFactory != null) {
                X509EncodedKeySpec encodedPublicKey = new X509EncodedKeySpec(keydata);
                publicKey = keyFactory.generatePublic(encodedPublicKey);
            }
        } catch (NullPointerException npe) {
            logger.error(npe.getMessage());
        } catch (InvalidKeySpecException e) {
            logger.error(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return publicKey;
    }


}

