This a scrap heap of utils to measure BigInteger performance and memory usage.
There are several non-standart liblaries used. The main harness is JMH. Another one is MemoryMeasurer.jar. And LongArithmetic is apropriate software which was also tested.

To build this project with maven you should install LongArithmetic.jar and MemoryMeasurer.jar to you local mvn repo.
Please run

```
mvn install:install-file -Dfile=MemoryMeasurer.jar -DgroupId=thrd -DartifactId=MemoryMeasurer -Dversion=0.1 -Dpackaging=jar
```
and
```
mvn install:install-file -Dfile=LongArithmetic.jar -DgroupId=thrd -DartifactId=LonhInt -Dversion=0.0.1 -Dpackaging=jar
```

To run performance tests goto target folder and run:
```
java -jar microbenchmarks.jar -i 2 -f 1 -wi 2
```
you can play with arguments

To run memory measurments run:
```
java -jar -javaagent:MemoryMeasurer.jar BigIntegerTest-1.0.jar
```

Good luck!
