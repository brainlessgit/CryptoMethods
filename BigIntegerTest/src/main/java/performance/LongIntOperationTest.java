package performance;

import entity.LongInt;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * User: brainless
 * Date: 13.05.2014
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class LongIntOperationTest {

    private LongInt a512;
    private LongInt b512;
    private LongInt a1024;
    private LongInt b1024;
    private LongInt a2048;
    private LongInt b2048;

    @Setup
    public void prepare() {
        a512 = new LongInt("3684936888602765618746238908038301769824529458924372050094101857170591990237326687548281648781587779740661085779457555399962622905170253776744759016416550");
        b512 = new LongInt("9903512025147031911731252789361757998656920966209917366769673423919018866622693971253224106165939879656747386729728717913683899883722820548020835521046425");
        a1024 = new LongInt("136249771281568261666886810567019066027206956809001244419612644150603567160976777265738785290935435422893644299842422417543652833322057836327330960388955669298687094334954683728782337063688529806063339716584177071528524212584677946607777259631015586655468190075589798674890415565776382469234863971767790595986");
        b1024 = new LongInt("77761798353519080594342103160824355824575955280685492737098535366897513649331013218023474220854329908214127097873228548917345370383730720064044752229193711973213512960499712435780824613989040596053095119774478935757476623685351472352474781885561309661992639121529533554923828007177228075670655851423362551435");
        a2048 = new LongInt("28953436064542794935218264542466596986958417831802638023149835819250520039387176518354825354940754513696765827535944906775204999481463132521607342098116614369649232892195443340127184325761435987659074064131291018014437573876850262262996455765471858708792871940330319232869696880171818133347898941836083414763932171205255400878025031088340137373662007107220993230337174295478031592863383092419809098284874014718679221699536898769737354555901892003222192270613172422073261061390632144685344554707536871301622148805026990697399777894911298484955623588072735426313436591395162611192041282824000338558404385172632870174494");
        b2048 = new LongInt("7129994168232145709199755661955372542941411809596542433021006713373041061821207693275578537453273633630129030543475562528934467265806359177790507363092892612031047560646726750685676314099183506445168207502727676516669608642422666676070241223241070397632500302974643008243897438112138925513995825851281656398628886808627376756141897334723412221662219714118036224295047035644157256261522663661786076476868856731960640286139236363193246161920771887195506236300976752315920860570446464134140742728660022443515469280072777633634525287684965754582174616966769802285144563877327892341491166005826577176723504808185807699664");
    }

    @GenerateMicroBenchmark
    public LongInt add512to512() {
        return a512.add(b512);
    }

    @GenerateMicroBenchmark
    public LongInt add1024to1024() {
        return a1024.add(b1024);
    }

    @GenerateMicroBenchmark
    public LongInt add2048to2048() {
        return a2048.add(b2048);
    }

    @GenerateMicroBenchmark
    public LongInt multiply512to512() {
        return a512.mult(b512);
    }

    @GenerateMicroBenchmark
    public LongInt multiply1024to1024() {
        return a1024.mult(b1024);
    }

    @GenerateMicroBenchmark
    public LongInt multiply1024to2048() {
        return a1024.mult(b2048);
    }

    @GenerateMicroBenchmark
    public LongInt multiply2048to1024() {
        return b2048.mult(a1024);
    }

    @GenerateMicroBenchmark
    public LongInt multiply2048to2048() {
        return a2048.mult(b2048);
    }

   // @GenerateMicroBenchmark
    public LongInt divide2048to512() {
        return b2048.div(a512);
    }

    //@GenerateMicroBenchmark
    public LongInt divide2048to1024() {
        return b2048.div(a1024);
    }


}
