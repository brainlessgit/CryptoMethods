package com.kpi.blindsign;

import java.math.BigInteger;

/**
 * User: brainless
 * Date: 25.05.14
 */
public class test {
    public static void main(String[] args) {

        BigInteger modulus = new BigInteger("7794014358223829430132871249435782621775844868372864990769475184383956875812465144399422116727792407555496222149921548206013922571129122529788616252954039", 10);
        BigInteger d = new BigInteger("1146916619172537818700908041710158957602670978387596471779007563333672278412708830325551936620740974358481113235200577466125266119695444235362560411211361", 10);
        BigInteger e = new BigInteger("65537");


        BigInteger a = new BigInteger("3791695672034319422051665020696875282456345764329031871993967128213856929737069592184086756223647444813558679705924649986233613078028130186279920592748544", 10);
        System.out.println(a.bitLength());

        System.out.println(a.modPow(d, modulus));
        System.out.println(a.modPow(d, modulus).modPow(e, modulus));
        System.out.println(a.modPow(d, modulus).modPow(e, modulus).equals(a));


    }
}
