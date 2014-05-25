package com.kpi.performance;

import com.kpi.blindsign.Alice;
import com.kpi.blindsign.Bob;
import org.openjdk.jmh.annotations.*;

import java.security.SignatureException;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class SignatureBenchmark1024 {

    private Bob bob;
    private Alice alice;
    private byte[] message;
    private byte[] signed;
    private byte[] masked;
    private byte[] maskedSigned;
    private byte[] unmaskedSigned;
    private byte[] fineSigned;

    @Setup
    public void setup() throws Exception {
        bob = new Bob(1024);
        alice = new Alice(bob.getPub());
        message = ("A number of years ago, when I was a freshly-appointed instructor, I met, for the first time," +
                " a certain eminent historian of science." +
                " At the time I could only regard him with tolerant condescension.\n" +
                "I was sorry of the man who, it seemed to me, was forced to hover about the edges of science. " +
                "He was compelled to shiver endlessly in the outskirts, getting only feeble warmth from the distant" +
                " sun of science- in-progress; while I, just beginning my research, was bathed in the heady liquid heat" +
                " up at the very center of the glow.\n" +
                "In a lifetime of being wrong at many a point, I was never more wrong. " +
                "It was I, not he, who was wandering in the periphery. It was he, not I, who lived in the blaze.\n" +
                "I had fallen victim to the fallacy of the 'growing edge;' " +
                "the belief that only the very frontier of scientific advance counted; that everything that had been" +
                "left behind by that advance was faded and dead.\n" +
                "But is that true? Because a tree in spring buds and comes greenly into leaf, are those leaves" +
                " therefore the tree?" +
                " If the newborn twigs and their leaves were all that existed, they would form a vague halo of green" +
                " suspended in mid-air, but surely that is not the tree. The leaves, by themselves, are no more than" +
                " trivial fluttering decoration. It is the trunk and limbs that give the tree its grandeur and the " +
                "leaves themselves their meaning.\n" +
                "There is not a discovery in science, however revolutionary, however sparkling with insight, that" +
                " does not arise out of what went before. 'If I have seen further than other men,'" +
                " said Isaac Newton, 'it is because I have stood on the shoulders of giants" +
                "" +
                "...We now know the basic rules governing the universe, together with the gravitational " +
                "interrelationships of its gross components, as shown in the theory of relativity worked" +
                " out between 1905 and 1916. We also know the basic rules governing the subatomic particles and " +
                "their interrelationships, since these are very neatly described by the quantum theory worked out" +
                " between 1900 and 1930. What's more, we have found that the galaxies and clusters of galaxies are" +
                " the basic units of the physical universe, as discovered between 1920 and 1930.\n" +
                "...The young specialist in English Lit, having quoted me, went on to lecture me severely on the " +
                "fact that in every century people have thought they understood the universe at last, and in every " +
                "century they were proved to be wrong. It follows that the one thing we can say about " +
                "our modern 'knowledge' is that it is wrong...\n" +
                "My answer to him was, when people thought the Earth was flat, they were wrong. When people thought " +
                "the Earth was spherical they were wrong. But if you think that thinking the Earth is spherical " +
                "is just as wrong as thinking the Earth is flat, then your view is wronger than both of them " +
                "put together.\n" +
                "The basic trouble, you see, is that people think that 'right' and 'wrong' are absolute; " +
                "that everything that isn't perfectly and completely right is totally and equally wrong.\n" +
                "However, I don't think that's so. It seems to me that right and wrong are fuzzy concepts, " +
                "and I will devote this essay to an explanation of why I think so.\n" +
                "When my friend the English literature expert tells me that in every century scientists " +
                "think they have worked out the universe and are always wrong, what I want to know is how wrong are they?" +
                " Are they always wrong to the same degree?").getBytes();

        signed = bob.straightForwardSign(message);

        masked = alice.mask(message);
        maskedSigned = bob.straightForwardSign(masked);
        unmaskedSigned = alice.unMask(maskedSigned);

        fineSigned = bob.sign(message);
    }

    @GenerateMicroBenchmark
    public void dummy() {
        // place your benchmarked code here
    }

    @GenerateMicroBenchmark
    public byte[] fineSigning()  throws SignatureException{
      return bob.sign(message);
    }

    @GenerateMicroBenchmark
    public boolean fineVerify() throws SignatureException{
        return alice.verify(message, fineSigned);
    }

    @GenerateMicroBenchmark
    public byte[] customSign() {
        return bob.straightForwardSign(message);
    }

    @GenerateMicroBenchmark
    public boolean customVerify() {
        return alice.straightForwardVerify(message, signed);
    }

    @GenerateMicroBenchmark
    public byte[] masking() {
        return alice.mask(message);
    }

    @GenerateMicroBenchmark
    public byte[] unMasking() {
        return alice.unMask(maskedSigned);
    }

}
