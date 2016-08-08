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

Benchmark                                                                Mode  Cnt          Score         Error  Units
BenchmarkRun.Mutable_With_ReflectASM_FieldAccess                        thrpt   10  76 077 308,108 ± 2177693,260  ops/s
BenchmarkRun.Mutable_With_JDK_MethodHandles_UnreflectField_invokeExact  thrpt   10  75 934 500,898 ± 1768786,400  ops/s
BenchmarkRun.Immutable_Without_Reflection                               thrpt   10  75 846 329,147 ± 1929441,918  ops/s
BenchmarkRun.Mutable_Without_Reflection                                 thrpt   10  75 740 658,284 ± 3264378,159  ops/s
BenchmarkRun.Mutable_With_ReflectASM_MethodAccess                       thrpt   10  75 173 324,993 ± 9064486,637  ops/s
BenchmarkRun.Mutable_With_JDK_MethodHandles_invokeExact                 thrpt   10  74 862 256,754 ± 5260449,996  ops/s
BenchmarkRun.Immutable_With_Reflection                                  thrpt   10  52 481 871,753 ±  503552,217  ops/s
BenchmarkRun.Mutable_With_Reflection                                    thrpt   10  51 607 512,394 ±  915338,934  ops/s
BenchmarkRun.Mutable_With_JDK_MethodHandles                             thrpt   10  45 013 172,599 ±  102249,692  ops/s
BenchmarkRun.Mutable_With_JDK_MethodHandles_UnreflectField              thrpt   10  44 236 762,131 ± 3077948,900  ops/s

```


The associated blog article can be found [here](https://blog.frankel.ch/performance-cost-of-reflection/).