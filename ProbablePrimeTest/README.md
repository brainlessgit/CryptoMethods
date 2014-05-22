This a scrap heap of utils to measure BigInteger probable prime creation and Java primality tests.
The main harness to measure performance is JMH.

It is maven based project and to play with it you should install both Java and Maven to your machine.
So, to build it please go to project folder and run:
```
maven clean package
```

To run performance tests go to target folder and run:
```
java -jar microbenchmarks.jar -i 2 -f 1 -wi 2
```
you can play with arguments. Be careful to run deterministic AKS primality tests, they may take more time.


Good luck!
