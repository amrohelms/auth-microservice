package io.simplersoftware.learning.auth.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;

@Component
public class PKCS12KeyStore implements ServerKeyStore {


//    keytool -genkeypair -alias serverKeyPair -keyalg RSA -keysize 2048 -validity 365 -storetype PKCS12 -keystore keystore.p12 -storepass mypass
//    keytool -list -rfc --keystore keystore.jks | openssl x509 -inform pem -pubkey

    @Value("${APP.SECURITY.KEYPAIR.SECRET}")
    private String secret;
    @Value("${APP.SECURITY.KEYPAIR.ALIAS}")
    private String alias;
    @Value("${APP.SECURITY.KEYSTORE.FILE}")
    private String keyStoreFile;


    @Override
    public KeyPair getKeyPairFromKeyStore() {

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(keyStoreFile), secret.toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry =
                    (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, new KeyStore.PasswordProtection(secret.toCharArray()));

            Certificate cert = keyStore.getCertificate(alias);

            PublicKey publicKey = cert.getPublicKey();
            PrivateKey privateKey = privateKeyEntry.getPrivateKey();
            return new KeyPair(publicKey, privateKey);
        }
        catch (Exception exception){
            return null;
        }

    }
}