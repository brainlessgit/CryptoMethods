package com.kpi.blindsign;


/**
 * User: brainless
 * Date: 24.05.14
 */
public class MainFlow {
    public static void main(String[] args) throws Exception {
        Bob bob = new Bob(2048);
        Alice alice = new Alice(bob.getPub());

        byte[] message = ("A number of years ago, when I was a freshly-appointed instructor, I met, for the first time," +
                " a certain eminent historian of science. At the time I could only regard him with tolerant condescension.\n" +
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
                " said Isaac Newton, 'it is because I have stood on the shoulders of giants").getBytes();
        byte[] signed = bob.straightForwardSign(message);
        boolean result = alice.straightForwardVerify(message, signed);

        System.out.println(result);

        byte[] masked = alice.mask(message);
        byte[] maskedSigned = bob.straightForwardSign(masked);
        byte[] unmaskedSigned = alice.unMask(maskedSigned);

        result = alice.straightForwardVerify(message, unmaskedSigned);
        System.out.println(result);

//        BigInteger a = new BigInteger("10000000001");
//        System.out.println(a);
//        System.out.println(a.bitLength());
//        System.out.println(a.toByteArray().length);
//        byte[] masked = alice.mask(message);
//        byte[] signed = bob.sign(masked);
//        byte[] signed = bob.sign(message);
//        byte[] unMasked = alice.unMask(signed);


//        boolean result = alice.verify(message, unMasked);
//        System.out.println(Math.(4 * 1.0 / 3));

    }
}
