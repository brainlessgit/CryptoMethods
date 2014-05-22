package com.kpi.perfoamnce;

import com.kpi.aks.Interface;
import org.openjdk.jmh.annotations.*;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * User: brainless
 * Date: 20.05.14
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
public class ProbablePrimeCreation {

    Random random;

    @Setup
    public void prepare() {
        random = new Random();
    }
    @GenerateMicroBenchmark
    public void dummy() {
        //intentionally left blank
    }

//    @GenerateMicroBenchmark
    public BigInteger createProbablePrime1024Certenity100() {
        return new BigInteger(1024, 100, random);
    }

    @GenerateMicroBenchmark
    public BigInteger createProbablePrime1024Certenity1000() {
        return new BigInteger(1024, 1000, random);
    }

    @GenerateMicroBenchmark
    public BigInteger createProbablePrime1024CertenityIntMaxValue() {
        return new BigInteger(1024, Integer.MAX_VALUE, random);
    }


    @GenerateMicroBenchmark
    public BigInteger createProbablePrime2048CertenityIntMaxValue() {
        return new BigInteger(2048, Integer.MAX_VALUE, random);
    }

//    @GenerateMicroBenchmark
    public BigInteger createProbablePrime4096CertenityIntMaxValue() {
        return new BigInteger(4096, Integer.MAX_VALUE, random);
    }
}
