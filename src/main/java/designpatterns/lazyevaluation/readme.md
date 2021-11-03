# Lazy Evaluation Design Pattern

Evaluate results until needed.

There are many different ways of designing

- Supplier makes it easy to create lazy evaluation

```java
//DecoratingIterator omitted
public class LazyEvaluation<T> extends Iterable<T> {
    private final Iterable<T> iterable;

    protected LazyEvaluation() {
        iterable = this;
    }

    public LazyEvaluation<T> filter(Predicate<? super E> predicate) {
        return new LazyEvaluation<>() {
            @Override
            public Iterator<T> iterator() {
                return new DecoratingIterator<>(iterable.iterator()) {
                    @Override
                    public E next() {
                        while (decoratingIterator.hasNext()) {
                            E item = decoratingIterator.next();
                            if (predicate.test(item)) {
                                return candidate;
                            }
                        }
                        return null;
                    }
                }
            }
        }
    }

    public Iterator<T> iterator() {
        return new DecoratingIterator<>(iterable.iterator());
    }
}

class Lazy<T> implements Supplier<T> {
    private final Supplier<? extends T> supplier;
    private T value;

    Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        if (value == null) {
            value = supplier.get();
        }
        return value;
    }

    public static <T> Lazy<T> of(Supplier<? extends T> supplier) {
        return new Lazy<>(supplier);
    }

    public <S> Lazy<S> map(Function<? super T, ? extends S> function) {
        return Lazy.of(() -> function.apply(get()));
    }

    public <S> Lazy<S> flatMap(Function<? super T, Lazy<? extends S>> function) {
        return Lazy.of(() -> function.apply(get()).get());
    }
}
```