# Chain of Responsibility Design Pattern

Functional Appraoch

```java
public interface Expr {
    public static Expr parse(Iter)
}

public static class BinaryOp implements Expr {
    public enum Operator {
        //omit
    }

    public static Optional<Operator> parse(String token) {
        return Optional.ofNullable(Operator.valueOf(token));
    }
}

public static class Variable implements Expr {

}

public static class Value implements Expr {

}

public static Optional<Expr> parseBinaryOperation(String token, Supplier<Expr> supplier) {
    return Opterator.parse(token).map(op -> new BinaryOp(op, supplier.get(), supplier.get()))
}

public static Optional<Expr> parsevalue(String token) {
    //try parsing value
}

public static Optional<Expr> parseVariable(String token) {
    return Optional.of(new Variable(token));
}

public static Expr parse(Iteartor<String> iter, Function<String, Optiona<Expr>> factory>) {
    String token = it.next();
    return factory.apply(token).orElseThrow(() -> new IllegalStateException());
}

private static Expr create(Iterator<String> it) {
    return parse(it, token -> parseBinaryOp(token, () -> create(it))
    .or(() -> paresValue(token))
    .or(() -> parseVariable(token)));
}
```
