# Currying Functions

- Takes multiple arguments of a function and makes it possible to apply 1 argument at a time to get the same result
- Functional

```java
private UnaryOperator<Integer> multiply(final int multiplier){
    return value -> value * multiplier;
}

UnaryOperator<Integer> doubler = multiply(2);
System.out.println(doubler.apply(10));
```
