package com.kpi.blindsign;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: brainless
 * Date: 24.05.14
 */
public class Bob {

    private final PrivateKey priv;
    private final PublicKey pub;
    private final Signature rsa;
    private final BigInteger modulus;
    private final BigInteger privateExponent;
    private int blockSize;

    public Bob() throws NoSuchAlgorithmException, InvalidKeyException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        keyGen.initialize(512, random);
        KeyPair pair = keyGen.generateKeyPair();
        priv = pair.getPrivate();
        pub = pair.getPublic();
        modulus = ((RSAPrivateKey) priv).getModulus();
        privateExponent = ((RSAPrivateKey) priv).getPrivateExponent();
        blockSize = modulus.bitLength() / 8;

        rsa = Signature.getInstance("NONEwithRSA");
        rsa.initSign(priv);
    }

    public byte[] sign(byte[] toSign) throws SignatureException {
        rsa.update(toSign);
        return rsa.sign();
    }

    public byte[] straightForwardSign(byte[] toSign) {
        byte[] block = new byte[blockSize];
        byte[] result = new byte[countResultLength(toSign)];
        int blocksDone = 0;

        for (int i = 0; i < toSign.length; i++) {
            if ((i != 0 && i % blockSize == 0) || i == toSign.length - 1) {
                block[i % blockSize] = toSign[i];

                BigInteger a = new BigInteger(block);
                BigInteger signedA = a.modPow(privateExponent, modulus);
                byte[] src = signedA.toByteArray();
                if (src.length > blockSize) {
                    int diff = src.length - blockSize;
                    System.arraycopy(src, diff, result, blocksDone * blockSize, src.length - diff);

                } else {
                    System.arraycopy(src, 0, result, blocksDone * blockSize + (blockSize - src.length), src.length);
                }
                Arrays.fill(block, (byte) 0);
                blocksDone++;
            }
            block[i % blockSize] = toSign[i];
        }
        return result;

    }

    private int countResultLength(byte[] toSign) {
        int length = toSign.length;
        return length / blockSize == 0 ? blockSize : length % blockSize == 0 ? length : (length / blockSize) * blockSize + blockSize;
    }

    public boolean signAndVerify(byte[] m) throws Exception {
        rsa.update(m);
        byte[] sign = rsa.sign();
        rsa.initVerify(pub);
        rsa.update(m);
        return rsa.verify(sign);
    }

    public PublicKey getPub() {
        return pub;
    }
}
