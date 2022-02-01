# Memoization

- Designed to optimize a function by storing previous results
- [Source](http://rdafbn.blogspot.com/2015/06/memoize-functions-in-java-8.html)

```java
import java.util.*;

public class Fibonacci {
    private Map<Integer, Long> previousResults = new HashMap<>();

    public long fib(int n) {
        if (n <= 1) return n;
        return previousResults.compute(n, (k, v) -> v != null ? v : fib(n - 2) + fib(n - 1))
    }
}
```

Functional Appraoch

```java
public static final Function<Integer, Integer> ADDER = x -> x + x;

public static <X, Y> Function<X, Y> memoise(Function<X, Y> fn) {
    Map<X, Y> results = new ConcurrentHashMap<X, Y>();
    return value -> results.computeIfAbsent(value, x -> {
            System.out.println("Saving: " + x);
            return fn.apply(x);
            }
    );
}

public static final Function<Integer, Integer> doubler = memoise(ADDER);


//Multiple arguments
Function<Integer, Function<Integer, Function<Integer, Integer>>> f3 = memoise(x -> memoise(y -> memoise(z -> x + y - z)));
```

- 146
