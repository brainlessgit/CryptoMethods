package com.kpi;

import com.kpi.aks.AKS;
import com.kpi.aks.Interface;
import com.kpi.anotheraks.Primes;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * User: brainless
 * Date: 20.05.14
 */
public class AKSvsBigInteger {
    public static void main(String[] args) {
        Random random = new Random();
        Map<BigInteger, Integer> fails = new HashMap<BigInteger, Integer>();
        for (int i = 0; i < 1; i++) {
            BigInteger pprime = BigInteger.probablePrime(128, random);
            System.out.println(pprime);
            if (new AKS(pprime).isPrime()) {
            } else {
               fails.put(pprime, 1);
            }
        }
        System.out.println("Probable prime fails " + fails.size() + "times");
        for (BigInteger bigInteger : fails.keySet()) {
            System.out.println(bigInteger);
        }
    }
}
