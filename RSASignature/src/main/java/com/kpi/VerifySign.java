package com.kpi;

import sun.security.rsa.RSAPublicKeyImpl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * User: brainless
 * Date: 23.05.14
 */
public class VerifySign {
    public static void main(String[] args) {

        try {
            FileInputStream keyfis = new FileInputStream("brainless_pub_key");
            byte[] encKey = new byte[keyfis.available()];
            keyfis.read(encKey);
            keyfis.close();
            PublicKey pubKey = new RSAPublicKeyImpl(encKey);

            FileInputStream sigfis = new FileInputStream("sign.txt");
            byte[] sigToVerify = new byte[sigfis.available()];
            sigfis.read(sigToVerify );
            sigfis.close();

            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(pubKey);

            FileInputStream datafis = new FileInputStream("toSign.txt");
            BufferedInputStream bufin = new BufferedInputStream(datafis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
            };
            bufin.close();

            boolean verifies = sig.verify(sigToVerify);
            System.out.println("signature verifies: " + verifies);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
