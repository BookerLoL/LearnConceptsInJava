package functional.pys_fp_book;

import java.util.Objects;

import static functional.pys_fp_book.List.list;

public abstract class Option<T> {
    public abstract T getOrThrow();

    public abstract T getOrElse(T defaultValue);

    public abstract T getOrElse(Supplier<T> defaultValue);

    public abstract <U> Option<U> map(Function<T, U> f);

    public abstract boolean isSome();

    private static final None<?> NONE = new None<>();

    private static class None<T> extends Option<T> {
        @Override
        public T getOrThrow() {
            throw new IllegalStateException("Get on None!");
        }

        @Override
        public T getOrElse(T defaultValue) {
            return defaultValue;
        }

        @Override
        public T getOrElse(Supplier<T> defaultValue) {
            return defaultValue.get();
        }

        @Override
        public <U> Option<U> map(Function<T, U> f) {
            return none();
        }

        @Override
        public boolean isSome() {
            return false;
        }

        @Override
        public boolean equals(Object o) {
            return this == o || o instanceof None;
        }
        @Override
        public int hashCode() {
            return 0;
        }
    }

    private static class Some<T> extends Option<T> {
        private final T value;

        private Some(T t) {
            this.value = t;
        }

        @Override
        public T getOrThrow() {
            return this.value;
        }

        @Override
        public T getOrElse(T defaultValue) {
            return this.value;
        }

        @Override
        public T getOrElse(Supplier<T> defaultValue) {
            return this.value;
        }

        @Override
        public <U> Option<U> map(Function<T, U> f) {
            return new Some<>(f.apply(value));
        }

        @Override
        public boolean isSome() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            return (this == o || o instanceof Some)
                    && this.value.equals(((Some<?>) o).value);
        }
        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }
    }

    public static <T> Option<T> some(T t) {
        return new Some<>(t);
    }

    public static <T> Option<T> none() {
        return (Option<T>) NONE;
    }

    public <U> Option<U> flatMap(Function<T, Option<U>> f) {
        return map(f).getOrElse(Option::none);
    }

    public Option<T> orElse(Supplier<Option<T>> defaultValue) {
        return map(x -> this).getOrElse(defaultValue);
    }

    public Option<T> filter(Function<T, Boolean> f) {
        return flatMap(value -> f.apply(value) ? this : none());
    }

    //Math.abs = double abs(double x)
    public static <T, U> Function<Option<T>, Option<U>> lift(Function<T, U> f) {
        return x -> {
            try {
                return x.map(f);
            } catch (Exception e) {
                return Option.none();
            }
        };
    }

    public static <T, U> Function<T, Option<U>> fLift(Function<T, U> f) {
        return x -> {
            try {
                return some(x).map(f);
            } catch (Exception e) {
                return Option.none();
            }
        };
    }

    public static <T, U, V> Option<V> map2(Option<T> t, Option<U> u, Function<T, Function<U, V>> f) {
        return t.flatMap(tVal -> u.map(uVal -> f.apply(tVal).apply(uVal)));
    }

    public static <T, U, V, W> Option<W> map3(Option<T> t, Option<U> u, Option<V> v, Function<T, Function<U, Function<V, W>>> f) {
        return t.flatMap(tVal -> u.flatMap(uVal -> v.map(vVal -> f.apply(tVal).apply(uVal).apply(vVal))));
    }

    public static <T, U> Option<List<U>> traverse(List<T> list, Function<T, Option<U>> f) {
        return list.foldRight(some(list()), x -> y -> map2(f.apply(x), y, a -> b -> b.cons(a)));
    }

    public static <T> Option<List<T>> sequence(List<Option<T>> list) {
        return traverse(list, x -> x);
    }
}
