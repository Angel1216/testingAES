/*
 * Copyright (C) 2015 Dominik Schadow, dominikschadow@gmail.com 
 * 
 * This file is part of the Java Security project. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package mx.com.beo.util;

import org.apache.shiro.codec.Base64; 
import org.apache.shiro.codec.CodecSupport; 
import org.apache.shiro.crypto.AesCipherService; 
import org.apache.shiro.util.ByteSource; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
import java.io.IOException; 
import java.io.InputStream; 
import java.security.*; 
import java.security.cert.CertificateException; 
 
/**
 * Symmetric encryption sample with Apache Shiro. Loads the AES key from the sample keystore, encrypts and decrypts sample text with it. 
 * 
 * @author Dominik Schadow 
 */ 
public class AESEncryption { 
    private static final Logger LOGGER = LoggerFactory.getLogger(AESEncryption.class); 
    private static final String KEYSTORE_PATH = "/samples.ks"; 
 
//    public static void main(String[] args) { 
//        AESEncryption res = new AESEncryption(); 
//        final String initialText = "AES encryption sample text"; 
//        final char[] keystorePassword = "samples".toCharArray(); 
//        final String keyAlias = "symmetric-sample"; 
//        final char[] keyPassword = "symmetric-sample".toCharArray(); 
// 
//        try { 
//            KeyStore ks = res.loadKeystore(KEYSTORE_PATH, keystorePassword); 
//            Key key = res.loadKey(ks, keyAlias, keyPassword); 
//            byte[] ciphertext = res.encrypt(key, CodecSupport.toBytes(initialText)); 
//            byte[] plaintext = res.decrypt(key, ciphertext); 
// 
//            res.printReadableMessages(initialText, ciphertext, plaintext); 
//        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | UnrecoverableKeyException | IOException ex) { 
//            LOGGER.error(ex.getMessage(), ex); 
//        } 
//    } 
 
    private KeyStore loadKeystore(String keystorePath, char[] keystorePassword) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException { 
        InputStream keystoreStream = getClass().getResourceAsStream(keystorePath); 
 
        KeyStore ks = KeyStore.getInstance("JCEKS"); 
        ks.load(keystoreStream, keystorePassword); 
 
        return ks; 
    } 
 
    private Key loadKey(KeyStore ks, String keyAlias, char[] keyPassword) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException { 
        if (!ks.containsAlias(keyAlias)) { 
            throw new UnrecoverableKeyException("Secret key " + keyAlias + " not found in keystore"); 
        } 
 
        return ks.getKey(keyAlias, keyPassword); 
    } 
 
    /**
     * Encrypts the given text using all Shiro defaults: 128 bit size, CBC mode, PKCS5 padding scheme. 
     * 
     * @param key The key to use 
     * @param initialText The text to encrypt 
     * @return The encrypted text 
     */ 
    private byte[] encrypt(Key key, byte[] initialText) { 
        AesCipherService cipherService = new AesCipherService(); 
        ByteSource cipherText = cipherService.encrypt(initialText, key.getEncoded()); 
 
        return cipherText.getBytes(); 
    } 
 
    private byte[] decrypt(Key key, byte[] ciphertext) { 
        AesCipherService cipherService = new AesCipherService(); 
        ByteSource plainText = cipherService.decrypt(ciphertext, key.getEncoded()); 
 
        return plainText.getBytes(); 
    } 
 
    private void printReadableMessages(String initialText, byte[] ciphertext, byte[] plaintext) { 
        LOGGER.info("initialText: {}", initialText); 
        LOGGER.info("cipherText as byte[]: {}", new String(ciphertext)); 
        LOGGER.info("cipherText as Base64: {}", Base64.encodeToString(ciphertext)); 
        LOGGER.info("plaintext: {}", CodecSupport.toString(plaintext)); 
    } 
}
