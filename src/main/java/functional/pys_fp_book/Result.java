package functional.pys_fp_book;

public abstract class Result<T> {
    public abstract T getOrElse(final T defaultValue);

    public abstract T getOrElse(final Supplier<T> defaultValue);

    public abstract <U> Result<U> map(Function<T, U> f);

    public abstract <U> Result<U> flatMap(Function<T, Result<U>> f);

    public abstract Result<T> mapFailure(String s);

    public abstract Result<T> mapFailure(String s, Exception e);

    public abstract Result<T> mapFailure(Exception e);

    public abstract void forEach(Effect<T> effect);

    public abstract void forEachOrThrow(Effect<T> effect);

    public abstract Result<RuntimeException> forEachOrException(Effect<T> effect);

    public abstract boolean isSuccess();

    public abstract boolean isFailure();

    public abstract boolean isEmpty();

    private static class Empty<T> extends Result<T> {
        private Empty() {
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
        public <U> Result<U> map(Function<T, U> f) {
            return empty();
        }

        @Override
        public <U> Result<U> flatMap(Function<T, Result<U>> f) {
            return empty();
        }

        @Override
        public Result<T> mapFailure(String s) {
            return this;
        }

        @Override
        public Result<T> mapFailure(String s, Exception e) {
            return this;
        }

        @Override
        public Result<T> mapFailure(Exception e) {
            return this;
        }

        @Override
        public void forEach(Effect<T> effect) {

        }

        @Override
        public void forEachOrThrow(Effect<T> effect) {

        }

        @Override
        public Result<RuntimeException> forEachOrException(Effect<T> effect) {
            return empty();
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }

    private static class Success<T> extends Result<T> {
        private final T value;

        private Success(T t) {
            value = t;
        }

        @Override
        public T getOrElse(T defaultValue) {
            return value;
        }

        @Override
        public T getOrElse(Supplier<T> defaultValue) {
            return value;
        }

        @Override
        public <U> Result<U> map(Function<T, U> f) {
            try {
                return success(f.apply(value));
            } catch (Exception e) {
                return failure(e);
            }
        }


        @Override
        public <U> Result<U> flatMap(Function<T, Result<U>> f) {
            try {
                return f.apply(value);
            } catch (Exception e) {
                return failure(e);
            }
        }

        @Override
        public Result<T> mapFailure(String s) {
            return this;
        }

        @Override
        public Result<T> mapFailure(String s, Exception e) {
            return this;
        }

        @Override
        public Result<T> mapFailure(Exception e) {
            return this;
        }

        @Override
        public void forEach(Effect<T> effect) {
            effect.apply(value);
        }

        @Override
        public void forEachOrThrow(Effect<T> effect) {
            effect.apply(value);
        }

        @Override
        public Result<RuntimeException> forEachOrException(Effect<T> effect) {
            effect.apply(value);
            return empty();
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    private static class Failure<T> extends Result<T> {
        private final RuntimeException exception;

        private Failure(String errorMessage) {
            exception = new IllegalStateException(errorMessage);
        }

        private Failure(Exception e) {
            this.exception = new IllegalStateException(e.getMessage(), e);
        }

        private Failure(String s, Exception e) {
            this.exception = new IllegalStateException(s, e);
        }

        private Failure(RuntimeException e) {
            this.exception = e;
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
        public <U> Result<U> map(Function<T, U> f) {
            return failure(exception);
        }

        @Override
        public <U> Result<U> flatMap(Function<T, Result<U>> f) {
            return failure(exception);
        }

        @Override
        public Result<T> mapFailure(String s) {
            return failure(s);
        }

        @Override
        public Result<T> mapFailure(String s, Exception e) {
            return failure(s, e);
        }

        @Override
        public Result<T> mapFailure(Exception e) {
            return failure(e);
        }

        @Override
        public void forEach(Effect<T> effect) {

        }

        @Override
        public void forEachOrThrow(Effect<T> effect) {
            throw exception;
        }

        @Override
        public Result<RuntimeException> forEachOrException(Effect<T> effect) {
            return success(exception);
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    public Result<T> orElse(Supplier<Result<T>> defaultValue) {
        return map(x -> this).getOrElse(defaultValue);
    }

    public Result<T> filter(Function<T, Boolean> p) {
        return flatMap(x -> p.apply(x) ? this : failure("Condition failed"));
    }

    public boolean exists(Function<T, Boolean> p) {
        return map(p).getOrElse(false);
    }

    public static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    public static <T> Result<T> failure(String message) {
        return new Failure<>(message);
    }

    public static <T> Result<T> failure(Exception e) {
        return new Failure<>(e);
    }

    public static <T> Result<T> failure(RuntimeException e) {
        return new Failure<>(e);
    }

    public static <T> Result<T> failure(String message, Exception e) {
        return new Failure<>(message, e);
    }

    public static <T> Result<T> empty() {
        return new Empty<>();
    }

    public static <T> Result<T> of(T value) {
        return value == null ? failure("Null value") : success(value);
    }

    public static <T> Result<T> of(T value, String message) {
        return value == null ? failure(message) : success(value);
    }

    public static <T> Result<T> of(Function<T, Boolean> predicate, T value) {
        try {
            return predicate.apply(value) ? success(value) : empty();
        } catch (Exception e) {
            return failure("Exception while evaluation predicate");
        }
    }

    public static <T> Result<T> of(Function<T, Boolean> predicate, T value, String message) {
        try {
            return predicate.apply(value) ? success(value) : empty();
        } catch (Exception e) {
            return failure(message);
        }
    }

    public static <T, U> Function<Result<T>, Result<U>> lift(Function<T, U> f) {
        return x -> {
            try {
                return x.map(f);
            } catch (Exception e) {
                return failure(e);
            }
        };
    }

    public static <T, U, V> Function<Result<T>, Function<Result<U>, Result<V>>> lift2(Function<T, Function<U, V>> f) {
        return a -> b -> a.map(f).flatMap(b::map);
    }

    public static <T, U, V, W> Function<Result<T>, Function<Result<U>, Function<Result<V>, Result<W>>>> lift3(Function<T, Function<U, Function<V, W>>> f) {
        return a -> b -> c -> a.map(f).flatMap(b::map).flatMap(c::map);
    }

    public static <T, U, V> Result<V> map2(Result<T> t, Result<U> u, Function<T, Function<U, V>> f) {
        return lift2(f).apply(t).apply(u);
    }
}
