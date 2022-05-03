package functional.pys_fp_book;

//Left is for errors, right is for values
public abstract class Either<T, U> {
    public abstract <V> Either<T, V> map(Function<U, V> f);
    public abstract <V> Either<T, V> flatMap(Function<U, Either<T, V>> f);
    public abstract U getOrElse(Supplier<U> defaultValue);

    private static class Left<T, U> extends Either<T, U> {
        private final T value;

        private Left(T value) {
            this.value = value;
        }

        @Override
        public <V> Either<T, V> map(Function<U, V> f) {
            return new Left<>(value);
        }

        @Override
        public <V> Either<T, V> flatMap(Function<U, Either<T, V>> f) {
            return new Left<>(value);
        }

        @Override
        public U getOrElse(Supplier<U> defaultValue) {
            return defaultValue.get();
        }
    }

    private static class Right<T, U> extends Either<T, U> {
        private final U value;

        private Right(U value) {
            this.value = value;
        }

        @Override
        public <V> Either<T, V> map(Function<U, V> f) {
            return new Right<>(f.apply(value));
        }

        @Override
        public <V> Either<T, V> flatMap(Function<U, Either<T, V>> f) {
            return f.apply(value);
        }

        @Override
        public U getOrElse(Supplier<U> defaultValue) {
            return value;
        }
    }

    public Either<T, U> orElse(Supplier<Either<T, U>> defaultValue) {
        return map(x -> this).getOrElse(defaultValue);
    }

    public static <T, U> Either<T, U> left(T value) {
        return new Left<>(value);
    }

    public static <T, U> Either<T, U> right(U value) {
        return new Right<>(value);
    }
}
