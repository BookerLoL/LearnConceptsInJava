# Monad Design Pattern

Functional design pattern to allow chaining operations 

```java
import java.util.function.Function;
import java.util.function.Predicate;

public class Test<T> {
    T obj;

    private Test(T t) {
        this.obj = t;
    }

    public static <T> Test<T> of(T t) {
        return new Test<>(t);
    }

    public <U> Test<U> convert(Function<T, U> f) {
        return of(f.apply(obj));
    }

    public Test<T> test(Predicate<T> t) {
        if (t.test(obj)) {
            System.out.println("Passed the predicate!");
        }
        return this;
    }

    public <U> Test<T> test(Function<T, U> f, Predicate<U> v) {
        return test(f.andThen(v::test)::apply);
    }

    public T get() {
        return obj;
    }
}

public class Tester {
    public static void main(String[] args) {
        Function<Integer, Double> doubleF = x -> x * 2.0;
        Predicate<Double> pred = d -> d == 2;
        System.out.println(Test.of(1).test(doubleF, pred).get());
    }
}
```