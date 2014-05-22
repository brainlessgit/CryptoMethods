package com.kpi.perfoamnce;

import com.kpi.aks.AKS;
import org.openjdk.jmh.annotations.*;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * User: brainless
 * Date: 21.05.14
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PbrobablePrimeVsAKS {

    final int certenity = Integer.MAX_VALUE;
    BigInteger len8;
    BigInteger len16;
    BigInteger len24;
    BigInteger len32;
    BigInteger len64;
    AKS aks8;
    AKS aks16;
    AKS aks24;
    AKS aks32;
    AKS aks64;

    @Setup
    public void setup() {
        Random random = new Random();
        len8 = new BigInteger(8, certenity, random);
        len16 = new BigInteger(16, certenity, random);
        len24 = new BigInteger(24, certenity, random);
        len32 = new BigInteger(32, certenity, random);
        len64 = new BigInteger(64, certenity, random);
        aks8 = new AKS(len8);
        aks16 = new AKS(len16);
        aks24 = new AKS(len24);
        aks32 = new AKS(len32);
        aks64 = new AKS(len64);
    }


    @GenerateMicroBenchmark
    public void dummy() {
    }

    @GenerateMicroBenchmark
    public boolean isProbablePrime8() {
        return len8.isProbablePrime(certenity);
    }

    @GenerateMicroBenchmark
    public boolean isProbablePrime16() {
        return len16.isProbablePrime(certenity);
    }

    @GenerateMicroBenchmark
    public boolean isProbablePrime24() {
        return len24.isProbablePrime(certenity);
    }

    @GenerateMicroBenchmark
    public boolean isProbablePrime32() {
        return len32.isProbablePrime(certenity);
    }

    @GenerateMicroBenchmark
    public boolean isProbablePrime64() {
        return len64.isProbablePrime(certenity);
    }

    @GenerateMicroBenchmark
    public boolean isPrime8() {
        return aks8.isPrime();
    }

    @GenerateMicroBenchmark
    public boolean isPrime16() {
        return aks16.isPrime();
    }

    @GenerateMicroBenchmark
    public boolean isPrime24() {
        return aks24.isPrime();
    }

    @GenerateMicroBenchmark
    public boolean isPrime32() {
        return aks32.isPrime();
    }

    @GenerateMicroBenchmark
    public boolean isPrime64() {
        return aks64.isPrime();
    }
}
