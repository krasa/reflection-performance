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
BenchmarkRun.Immutable_Without_Reflection                               thrpt   20  102 214 799,142 ± 1146143,316  ops/s
BenchmarkRun.Mutable_Without_Reflection                                 thrpt   20  101 962 026,052 ±  769921,913  ops/s
BenchmarkRun.Mutable_With_ReflectASM_FieldAccess                        thrpt   20   91 149 819,205 ±  167487,740  ops/s
BenchmarkRun.Mutable_With_ReflectASM_MethodAccess                       thrpt   20   89 414 320,276 ±   94895,259  ops/s
BenchmarkRun.Immutable_With_Reflection                                  thrpt   20   60 196 592,871 ±  479156,501  ops/s
BenchmarkRun.Mutable_With_Reflection                                    thrpt   20   57 873 746,873 ± 1229671,008  ops/s
BenchmarkRun.Mutable_With_JDK_MethodHandles_UnreflectField_invokeExact  thrpt   10   50 402 347,437 ±  351941,848  ops/s
BenchmarkRun.Mutable_With_JDK_MethodHandles_invokeExact                 thrpt   10   50 261 652,584 ±  146383,521  ops/s
BenchmarkRun.Mutable_With_JDK_MethodHandles_UnreflectField              thrpt   20   49 353 822,681 ±  200844,605  ops/s
BenchmarkRun.Mutable_With_JDK_MethodHandles                             thrpt   20   48 749 285,928 ±  584271,048  ops/s

```

The associated blog article can be found [here](https://blog.frankel.ch/performance-cost-of-reflection/).