package functional.pys_fp_book;

import static functional.pys_fp_book.List.list;
import static functional.pys_fp_book.TailCall.ret;
import static functional.pys_fp_book.TailCall.sus;

public abstract class Stream<T> {
    private static Stream<?> EMPTY = new Empty();

    public abstract T head();
    public abstract Stream<T> tail();
    public abstract boolean isEmpty();
    public abstract Result<T> headOption();
    public abstract Stream<T> take(int n);
    public abstract Stream<T> drop(int n);
    public abstract Stream<T> dropWhile(Function<T, Boolean> p);
    public abstract Stream<T> takeWhile(Function<T, Boolean> p);
    public abstract <U> U foldRight(Supplier<U> z, Function<T, Function<Supplier<U>, U>> f);

    private static class Empty<T> extends Stream<T> {
        @Override
        public T head() {
            throw new IllegalStateException();
        }

        @Override
        public Stream<T> tail() {
            throw new IllegalStateException();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Result<T> headOption() {
            return Result.empty();
        }

        @Override
        public Stream<T> take(int n) {
            return this;
        }

        @Override
        public Stream<T> drop(int n) {
            return this;
        }

        @Override
        public Stream<T> dropWhile(Function<T, Boolean> p) {
            return this;
        }

        @Override
        public Stream<T> takeWhile(Function<T, Boolean> p) {
            return this;
        }

        @Override
        public <U> U foldRight(Supplier<U> z, Function<T, Function<Supplier<U>, U>> f) {
            return z.get();
        }
    }

    private static class Cons<T> extends Stream<T> {
        private final Supplier<T> head;
        private final Supplier<Stream<T>> tail;

        private Cons(Supplier<T> head, Supplier<Stream<T>> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public T head() {
            //if (h == nul) { h = head.get(); } return h;
            return head.get();
        }

        @Override
        public Stream<T> tail() {
            return tail.get();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Result<T> headOption() {
            return Result.success(head());
        }

        @Override
        public Stream<T> take(int n) {
            return n <= 0
                    ? empty()
                    : cons(head, () -> tail().take(n - 1));
        }

        @Override
        public Stream<T> drop(int n) {
            return drop_(this, n).eval();
        }

        @Override
        public Stream<T> dropWhile(Function<T, Boolean> p) {
            return p.apply(head()) ? this.tail().dropWhile(p) : this;
        }

        @Override
        public Stream<T> takeWhile(Function<T, Boolean> f) {
            return f.apply(head())
                    ? cons(head, () -> tail().takeWhile(f))
                    : empty();
        }

        @Override
        public <U> U foldRight(Supplier<U> z, Function<T, Function<Supplier<U>, U>> f) {
            return f.apply(head()).apply(() -> tail().foldRight(z, f));
        }

        public TailCall<Stream<T>> drop_(Stream<T> acc, int n) {
            return acc.isEmpty() || n <= 0
                    ? ret(acc)
                    : sus(() -> drop_(acc.tail(), n - 1));
        }
    }

    public static <T> Stream<T> cons(Supplier<T> head, Stream<T> tail) {
        return new Cons<>(head, () -> tail);
    }

    public static <T> Stream<T> cons(Supplier<T> head, Supplier<Stream<T>> tail) {
        return new Cons<>(head, tail);
    }

    public static <T> Stream<T> empty() {
        return (Stream<T>)EMPTY;
    }

    public static Stream<Integer> from(int i) {
        return iterate(i, x -> x + 1);
    }

    public static Stream<Integer> from2(int i) {
        return unfold(i, x -> Result.success(new Tuple<>(x, x+1)));
    }

    public List<T> toList() {
        return toList_(this, list()).eval().reverse();
    }

    private TailCall<List<T>> toList_(Stream<T> s, List<T> acc) {
        return s.isEmpty() ? ret(acc) : sus(() -> toList_(s.tail(), List.cons(s.head(), acc)));
    }

    public boolean exists(Function<T, Boolean> p) {
        return exists_(this, p).eval();
    }
    private TailCall<Boolean> exists_(Stream<T> s, Function<T, Boolean> p) {
        return s.isEmpty() ? ret(false) : p.apply(s.head()) ? ret(true) : sus(() -> exists_(s.tail(), p));
    }

    public Result<T> headOptionViaFoldRight() {
        return foldRight(Result::empty, a -> ignore -> Result.success(a));
    }

    public Stream<T> takeWhileViaFoldRight(Function<T, Boolean> p) {
        return foldRight(Stream::empty, a -> b -> p.apply(a) ? cons(() -> a, b) : empty());
    }

    public <U> Stream<U> map(Function<T, U> f) {
        return foldRight(Stream::empty, a -> b -> cons(() -> f.apply(a), b));
    }

    public Stream<T> filter(Function<T, Boolean> p) {
        Stream<T> stream = this.dropWhile(x -> !p.apply(x));

        return foldRight(Stream::empty, a -> b -> p.apply(a) ? cons(() -> a, b) : b.get());
    }

    public Stream<T> append(Supplier<Stream<T>> s) {
        return foldRight(s, a -> b -> cons(() -> a, b));
    }

    public <U> Stream<U> flatMap(Function<T, Stream<U>> f) {
        return foldRight(Stream::empty, a -> b -> f.apply(a).append(b));
    }

    public Result<T> find(Function<T, Boolean> p) {
        return filter(p).headOption();
    }

    public static <T> Stream<T> iterate(Supplier<T> seed, Function<T, T> f) {
        return cons(seed, () -> iterate(f.apply(seed.get()), f));
    }

    public static <T> Stream<T> iterate(T seed, Function<T, T> f) {
        return cons(() -> seed, () -> iterate(f.apply(seed), f));
    }

    public static <T> Stream<T> repeat(T t) {
        return iterate(t, x -> x);
    }

    public static <T, U> Stream<T> unfold(U z, Function<U, Result<Tuple<T, U>>> f) {
        return f.apply(z).map(x -> cons(() -> x._1, () -> unfold(x._2, f))).getOrElse(empty());
    }
}
