# Converter Design Pattern

Makes it easy to map one object to another object

```java
import java.util.Random;
import java.util.function.Function;

public class Converter<T, U> {
    private final Function<T, U> convertTo;
    private final Function<U, T> convertFrom;

    public Converter(Function<T, U> convertTo, Function<U, T> convertFrom) {
        this.convertTo = convertTo;
        this.convertFrom = convertFrom;
    }

    public final U convertTo(T t) {
        return convertTo.apply(t);
    }

    public final T convertFrom(U u) {
        return convertFrom(u);
    }


    public final List<U> convertTo(final Collection<T> t) {
        return t.stream().map(this::convertTo).toList();
    }

    public final List<T> convertFrom(final Collection<U> u) {
        return u.stream().map(this::convertFrom).toList();
    }
}

public class RandomNumberConverter<Integer, Double> {
    private Random random = new Random(0);

    public MagicNumberConverter() {
        super(this::convertTo, this::convertFrom);
    }

    private static Integer convertFrom(Double d) {
        return (int) (d * random.nextDouble());
    }

    private static Double convertTo(Integer i) {
        return (double) (i * random.nextDouble());
    }
}
```