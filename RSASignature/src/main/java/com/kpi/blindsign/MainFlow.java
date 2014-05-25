package com.kpi.blindsign;


import java.math.BigInteger;
import java.util.Arrays;

/**
 * User: brainless
 * Date: 24.05.14
 */
public class MainFlow {
    public static void main(String[] args) throws Exception{
        Bob bob = new Bob();
        Alice alice = new Alice(bob.getPub());

        byte[] m = "   lkajsldkkksd walksdjlaks                                                laksjdlakjsdl                                             alskdjl akjsd                  orld!!!!".getBytes();
        byte[] signed = bob.straightForwardSign(m);
        boolean result = alice.straightForwardVerify(m, signed);

        System.out.println(result);

        byte[] masked = alice.mask(m);
        byte[] maskedSigned = bob.straightForwardSign(masked);
        byte[] unmaskedSigned = alice.unMask(maskedSigned);

        result = alice.straightForwardVerify(m, unmaskedSigned);
        System.out.println(result);

//        BigInteger a = new BigInteger("10000000001");
//        System.out.println(a);
//        System.out.println(a.bitLength());
//        System.out.println(a.toByteArray().length);
//        byte[] masked = alice.mask(m);
//        byte[] signed = bob.sign(masked);
//        byte[] signed = bob.sign(m);
//        byte[] unMasked = alice.unMask(signed);


//        boolean result = alice.verify(m, unMasked);
//        System.out.println(Math.(4 * 1.0 / 3));

    }
}
