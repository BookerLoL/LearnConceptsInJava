# Stategy Design Pattern

**Behavioral** Design Pattern

- Define family of algorithms and use one of them as an appraoch to take

```java
public interface ArithemticStrategy {
    public int doOperation(int num1, int num2);
}

public class AddStrategy implements ArithmeticStrategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}

//Have other classes and implement their own stategy

public class CalculatorOperation {
    private ArithemticStrategy strategy;

    public CalculatorOperation(ArithemticStrategy strategy) {
        this.strategy = strategy;
    }

    public int calculate(int num1, int num2) {
        return strategy.doOperation(num1, num2);
    }
}
```

Other examples

```java
public class StrategyOperation {
    private final Strategy strategy;

    public StategyOpeartion(Strategy strategy) {
        this.strategy = strategy;
    }

    public void doSomething(String input) {
        //...
        strategy.do(input);
    }
}

StategyOperation operation = new StrategyOperation(SomeClassImplementsStrategy::new);
```

Functional Appraoch

```java
public class CalculatorOperation {
    public static final BiFunction<Integer, Integer, Integer> ADDER_STRATEGY = (num1, num2) -> num1 + num2;

    private BiFunction<Integer, Integer, Integer> strategy;
    public class CalculatorOperation(BiFunction<Integer, Integer, Integer> strategy) {
        this.strategy = strategy;
    }

    public int calculate(int num1, int num2) {
        return strategy.apply(num1, num2);
    }
}
```

Another Functional Approach

- Just pass predicates / functions around instead

```java
public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
    List<T> filteredData = new ArrayList<>();
    for (T item : collection) {
        if (predicate.test(item)) {
            filteredData.add(item);
        }
    }
    return filteredData;
}
```
