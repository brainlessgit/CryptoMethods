package com.kpi.anotheraks;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;

public class Primes {

    public static boolean binarySearchPerfectPower(int b, BigInteger n, BigInteger minA, BigInteger maxA) {
        if (minA.compareTo(maxA) > 0) {
            return false;
        }
        BigInteger mid = maxA.add(minA).shiftRight(1);
        int comparison = mid.pow(b).compareTo(n);
        if (comparison > 0) {
            return binarySearchPerfectPower(b, n, minA, mid.subtract(BigInteger.ONE));
        }
        if (comparison < 0) {
            return binarySearchPerfectPower(b, n, mid.add(BigInteger.ONE), maxA);
        }
        return true;
    }

    public static boolean isPerfectPower(BigInteger n) {
        int biggestB = n.bitLength();
        for (int b = 2; b <= biggestB; b++) {
            if (binarySearchPerfectPower(b, n, BigInteger.valueOf(2), n)) {
                return true;
            }
        }
        return false;
    }

    public static int calculateR(BigInteger n) {
        int size = n.bitLength();
        int maxR = 2 + size * size * size * size * size;
        for (int r = 2; r <= maxR; r++) {
            boolean found = true;
            BigInteger product = BigInteger.ONE;
            for (int i = 1; i <= size * size; i++) {
                product = product.multiply(n.modPow(BigInteger.valueOf(i), BigInteger.valueOf(r)).subtract(BigInteger.ONE)).mod(BigInteger.valueOf(r));
                if (product.equals(BigInteger.ZERO)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return r;
            }
        }
        throw new ArithmeticException("Something went wrong with calculating R!");
    }

    public static boolean notOneGCDExists(BigInteger n, int r) {
        for (int a = 1; a <= r; a++) {
            BigInteger gcd = BigInteger.valueOf(a).gcd(n);
            if (gcd.compareTo(BigInteger.ONE) > 0 && gcd.compareTo(n) < 0) {
                return true;
            }
        }
        return false;
    }

    public static BigInteger[] squareModR(BigInteger[] poly) {
        int r = poly.length;
        BigInteger[] ret = new BigInteger[r];
        Arrays.fill(ret, BigInteger.ZERO);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                int d = (i + j) % r;
                ret[d] = ret[d].add(poly[i].multiply(poly[j]));
            }
        }
        return ret;
    }

    public static BigInteger[] multiplyByMonomialModR(BigInteger[] poly, int a) {
        int r = poly.length;
        BigInteger[] ret = new BigInteger[r];
        Arrays.fill(ret, BigInteger.ZERO);
        BigInteger bigA = BigInteger.valueOf(a);
        for (int i = 1; i < r; i++) {
            ret[i] = ret[i].add(poly[i - 1]).add(poly[i].multiply(bigA));
        }
        ret[0] = poly[0].multiply(bigA).add(poly[r - 1]);
        return ret;
    }

    public static BigInteger[] raiseToNModR(BigInteger n, int a, int r) {
        BigInteger[] poly = new BigInteger[r];
        Arrays.fill(poly, BigInteger.ZERO);
        poly[0] = BigInteger.valueOf(a);
        poly[1] = BigInteger.ONE;
        for (int bit = n.bitLength() - 2; bit >= 0; bit--) {
            poly = squareModR(poly);
            if (n.testBit(bit)) {
                poly = multiplyByMonomialModR(poly, a);
            }
        }
        return poly;
    }

    public static BigInteger[] modN(BigInteger[] poly, BigInteger n) {
        BigInteger[] ret = new BigInteger[poly.length];
        for (int i = 0; i < poly.length; i++) {
            ret[i] = poly[i].mod(n);
        }
        return ret;
    }

    public static boolean checkPolynomials(BigInteger n, int r) {
        int testSize = (int) (Math.sqrt(r) * n.bitLength());
        for (int a = 1; a <= testSize; a++) {
            BigInteger[] canonPoly = modN(raiseToNModR(n, a, r), n);
            for (int i = 1; i < r; i++) {
                if (n.mod(BigInteger.valueOf(r)).equals(BigInteger.valueOf(i))) {
                    continue;
                }
                if (!BigInteger.ZERO.equals(canonPoly[i])) {
                    System.out.println(a);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isPrime(BigInteger n) {
        if (isPerfectPower(n)) {
            return false;
        }
        int r = calculateR(n);
        if (notOneGCDExists(n, r)) {
            return false;
        }
        if (BigInteger.valueOf(r).compareTo(n) >= 0) {
            return true;
        }
        return checkPolynomials(n, r);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BigInteger n = null;
        if (args.length >= 1) {
            try {
                n = new BigInteger(args[0]);
            } catch (NumberFormatException e) {
                n = null;
            }
        }
        if (n == null) {
            do {
                System.out.print("Input integer greater than one: ");
                try {
                    n = new BigInteger(br.readLine());
                } catch (NumberFormatException e) {
                    n = BigInteger.ZERO;
                }
            } while (n.compareTo(BigInteger.ONE) <= 0);
        }
        System.out.println(isPrime(n) ? "Prime" : "Composite");
    }
}