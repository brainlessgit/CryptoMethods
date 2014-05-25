package com.kpi.blindsign;

import sun.security.rsa.RSAPublicKeyImpl;

import java.math.BigInteger;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.kpi.blindsign.Utils.modInv;

/**
 * User: brainless
 * Date: 24.05.14
 */
public class Alice {

    private final Signature rsa;
    private final BigInteger modulus;
    private final BigInteger publicExponent;
    private BigInteger k;
    private BigInteger kInv;
    private int blockSize;

    public Alice(PublicKey bobPublicKey) throws NoSuchAlgorithmException, InvalidKeyException {
        rsa = Signature.getInstance("NONEwithRSA");
        rsa.initVerify(bobPublicKey);
        modulus = ((RSAPublicKeyImpl) bobPublicKey).getModulus();
        publicExponent = ((RSAPublicKeyImpl) bobPublicKey).getPublicExponent();
        blockSize = modulus.bitLength() / 8;
    }

    public byte[] mask(byte[] toMask) {
        genMaskMultiplier();
        BigInteger kPow = k.modPow(publicExponent, modulus);

        byte[] block = new byte[blockSize];
        byte[] result = new byte[countResultLength(toMask)];
        int blocksDone = 0;

        for (int i = 0; i < toMask.length; i++) {
            if ((i != 0 && i % blockSize == 0) || i == toMask.length - 1) {
                if (i == toMask.length - 1) block[i % blockSize] = toMask[i];

                BigInteger a = new BigInteger(1, block);
                BigInteger maskedA = a.multiply(kPow).mod(modulus);
                byte[] src = maskedA.toByteArray();
                if (src.length > blockSize) {
                    int diff = src.length - blockSize;
                    System.arraycopy(src, diff, result, blocksDone * blockSize, src.length - diff);

                } else {
                    System.arraycopy(src, 0, result, blocksDone * blockSize + (blockSize - src.length), src.length);
                }
                Arrays.fill(block, (byte) 0);

                blocksDone++;
            }
            block[i % blockSize] = toMask[i];
        }
        return result;
    }

    public byte[] unMask(byte[] toUnmask) throws NoSuchAlgorithmException {
        if (kInv == null) {
            throw new IllegalArgumentException("How you mask the message?");
        } else {
            byte[] block = new byte[blockSize];
            byte[] result = new byte[countResultLength(toUnmask)];
            int blocksDone = 0;

            for (int i = 0; i < toUnmask.length; i++) {
                if ((i != 0 && i % blockSize == 0) || i == toUnmask.length - 1) {
                    if (i == toUnmask.length - 1) block[i % blockSize] = toUnmask[i];

                    BigInteger a = new BigInteger(1, block);
                    BigInteger unmaskedA = a.multiply(kInv).mod(modulus);
                    byte[] src = unmaskedA.toByteArray();
                    if (src.length > blockSize) {
                        int diff = src.length - blockSize;
                        System.arraycopy(src, diff, result, blocksDone * blockSize, src.length - diff);

                    } else {
                        System.arraycopy(src, 0, result, blocksDone * blockSize + (blockSize - src.length), src.length);
                    }
                    Arrays.fill(block, (byte) 0);

                    blocksDone++;
                }
                block[i % blockSize] = toUnmask[i];
            }
            return result;
        }
    }

    public boolean verify(byte[] message, byte[] signature) throws SignatureException {
        rsa.update(message);
        return rsa.verify(signature);
    }

    public boolean straightForwardVerify(byte[] message, byte[] signature) {
        byte[] signatureBlock = new byte[blockSize];
        byte[] messageBlock = new byte[blockSize];

        for (int i = 0; i < signature.length; i++) {
            if ((i != 0 && i % blockSize == 0) || i == signature.length - 1) {
                signatureBlock[blockSize - 1] = signature[i % blockSize == 0 ? i - 1 : i];
                BigInteger a = new BigInteger(1, signatureBlock);
                BigInteger unsignedA = a.modPow(publicExponent, modulus);
                BigInteger messageA = new BigInteger(1, messageBlock);
                Arrays.fill(signatureBlock, (byte) 0);
                Arrays.fill(messageBlock, (byte) 0);
                if (!messageA.equals(unsignedA)) return false;
            }
            signatureBlock[i % blockSize] = signature[i];
            if (i < message.length)
                messageBlock[i % blockSize] = message[i];
        }
        return true;
    }

    private void genMaskMultiplier() {
        Random random = new SecureRandom();
        int bitLength = modulus.bitLength() / 2;
        k = new BigInteger(bitLength, 10, random);
        while (!k.gcd(modulus).equals(BigInteger.ONE)) {
            k = new BigInteger(bitLength, 10, random);
        }
        kInv = modInv(k, modulus);
    }

    private int countResultLength(byte[] toSign) {
        int length = toSign.length;
        return length / blockSize == 0 ? blockSize : length % blockSize == 0 ? length : (length / blockSize) * blockSize + blockSize;
    }
}
