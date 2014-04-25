/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package performance;

import org.openjdk.jmh.annotations.*;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)

public class BigIntegerCreationTest {

    private Random random;

    @Setup
    public void prepare() {
        random = new Random();
    }

    @GenerateMicroBenchmark
    public void dummy() {
        //intentionaly left blank
    }

    @GenerateMicroBenchmark
    public BigInteger createFromString512() {
        return new BigInteger("11100001101010000101100011000010110101110100100101000011100110000001001001101010110100011001000110000010000001111110101101011101010111110011100011111100110111111110000110011101110000011010001100101110010111000110010011111100111111101001011010110100101000111101111010001111011100111011100110010111001110011011111010111101001001110100111001001011011110110001110010100110101001100010000101100010111001010010101110101011101101101000010111010111100011101001001110111010010110010111001000011101111011111111100101001001", 2);
    }

    @GenerateMicroBenchmark
    public BigInteger createFromString1024() {
        return new BigInteger("1011111000100011111101000101011110110000110101100010101110110001101100110000010101001100110111101100011111110001110111111100001101101001111000100100000010101000100011110110111000000100011011001011111101101100001111010100101111000001001110000010101100000011000010101010001011101011100100100111011110101001101000001000001010100010000100010110100111110010110101001010110000101100100101101001100001011011011111100011010010010001110100101001110000100101110010111100000101111100110010110110100011001000000011011001010000001110111111100010011110010101110110100001001010101100011000000100000111111001010100101111010011111101010111000011011010100111111001101010100011010000101001111100010001110111011001000101000101000110100001010111010000111011111100111110100111100001110000110111101010011110101101110111110001000111000111111111111000011001000100111011101110111000100110110000001001100001001100100001011011000110111010111111110010100100110100100011001100100000011011110011111101111101010100111110000011001100100000000111100101001101", 2);
    }

    @GenerateMicroBenchmark
    public BigInteger createProbablePrime256() {
        return BigInteger.probablePrime(256, random);
    }

    @GenerateMicroBenchmark
    public BigInteger createProbablePrime512() {
        return BigInteger.probablePrime(512, random);
    }

    @GenerateMicroBenchmark
    public BigInteger createProbablePrime1024() {
        return BigInteger.probablePrime(1024, random);

    }

    @GenerateMicroBenchmark
    public BigInteger createProbablePrime2048() {
        return BigInteger.probablePrime(2048, random);
    }
}
