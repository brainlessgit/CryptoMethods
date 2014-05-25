package com.kpi;

import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

class GenSign {

    public static void main(String[] args) {

        /* Generate a DSA signature */

        try {

            // the rest of the code goes here
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(1024, random);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();
//            System.out.println(((RSAPublicKeyImpl) pub).getModulus());
//            System.out.println(((RSAPublicKeyImpl) pub).getPublicExponent());
//            System.out.println(((RSAPrivateCrtKeyImpl) priv).getModulus());
//            System.out.println(((RSAPrivateCrtKeyImpl) priv).getPrivateExponent());
//            BigInteger a = BigInteger.ONE.add(BigInteger.ONE);
//            BigInteger n = BigInteger.TEN.add(BigInteger.ONE);
//            BigInteger aInv =  modInv(a, n);

//            System.out.println(a);
//            System.out.println(n);
//            System.out.println(aInv);
//            System.out.println(a.multiply(aInv).mod(n));

            byte[] key = pub.getEncoded();
            FileOutputStream keyfos = new FileOutputStream("brainless_pub_key");
            keyfos.write(key);
            keyfos.close();

            Signature rsa = Signature.getInstance("SHA1withRSA");
            rsa.initSign(priv);

            FileInputStream fis = new FileInputStream("toSign.txt");
            BufferedInputStream bufin = new BufferedInputStream(fis);

            FileOutputStream sigfos = new FileOutputStream("sign.txt");
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufin.read(buffer)) >= 0) {
                rsa.update(buffer, 0, len);
                byte[] realSig = rsa.sign();
                sigfos.write(realSig);
            }
            bufin.close();
            sigfos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}