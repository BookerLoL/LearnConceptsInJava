# Currying Functions

- Takes multiple arguments of a function and makes it possible to apply 1 argument at a time to get the same result

  - This avoids needing to supply all arguments when using the function

- Functional

```java
//Similar to: f(3)(5) = g(5) = 3 + 5
//Where x = 3 is provided, but y still needs a value

private UnaryOperator<Integer> multiply(final int multiplier){
    return value -> value * multiplier;
}

UnaryOperator<Integer> doubler = multiply(2);
System.out.println(doubler.apply(10));
```

More examples, using Function - The idea is, to keep using Function until all parameters have been resolved then return an output

```java
//More traditional functional approach would be this type o fcode
Function<Function<T, U>, Function<Function<V, T>, Function<V, U>>> curry = x -> y -> z -> x.apply(y.apply(z));

Function<Integer, Integer> returnSameValue = Function.identy();
Function<Integer, Integer> addOne = x -> x + 1;
Function<Integer, Integer> f = curry.apply(returnSameValue).apply(addOne);
f.apply(2);


Function<B, C> partialA(A a, Function<A, Function<B, C>> f) {
    return a -> f.apply(a);
}


Function<A, C> partialB(B b, Function<A, Function<B, C>> f) {
    return a -> f.apply(a).apply(b);
}
```

- Swapping arguments of curried functions

```java
public static <T, U, V> Function<U, Function<T, V>> reverse(Function<T, Function<U, V>> f) {
    return u -> t -> f.apply(t).apply(u);
}
```
