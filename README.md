# Performance cost of reflection

This project evaluates the performance overhead of using Java's reflection API to access attributes.

To run, first build the package with Maven:

```bash
mvn package
```

Then run the tests:

```bash
java -jar target/benchmarks.jar
```

Be prepared to wait for some time.

Results should look like the following:

```
Benchmark                                                                Mode  Cnt           Score          Error  Units
BenchmarkRun.Mutable_Without_Reflection                                 thrpt   20  4371718549,905 ± 16661759,981  ops/s
BenchmarkRun.Immutable_Without_Reflection                               thrpt   20  4355936723,612 ± 38472158,668  ops/s
BenchmarkRun.Mutable_With_ReflectASM_FieldAccess                        thrpt   20   434858277,833 ±  4880516,197  ops/s
BenchmarkRun.Mutable_With_ReflectASM_MethodAccess                       thrpt   20   272707071,354 ±  3500477,663  ops/s
BenchmarkRun.Mutable_With_Reflection                                    thrpt   20   111304786,764 ±  1340032,669  ops/s
BenchmarkRun.Immutable_With_Reflection                                  thrpt   20   107369247,621 ±  2025100,731  ops/s
BenchmarkRun.Mutable_With_JDK_MethodHandles                             thrpt   20    77192337,545 ±   440805,042  ops/s
BenchmarkRun.Mutable_With_JDK_MethodHandles_UnreflectField              thrpt   20    73985116,254 ±   387612,101  ops/s
BenchmarkRun.Mutable_With_JDK_MethodHandles_invokeExact                 thrpt   20     6441266,338 ±    32570,237  ops/s
BenchmarkRun.Mutable_With_JDK_MethodHandles_UnreflectField_invokeExact  thrpt   20     6417387,952 ±    29474,687  ops/s

```

The associated blog article can be found [here](https://blog.frankel.ch/performance-cost-of-reflection/).