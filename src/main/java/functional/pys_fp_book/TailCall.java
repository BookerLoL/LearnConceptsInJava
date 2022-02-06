package functional.pys_fp_book;

public abstract class TailCall<T> {
    public abstract TailCall<T> resume();
    public abstract T eval();
    public abstract boolean isSuspended();

    public static class Return<T> extends TailCall<T> {
        private final T t;

        public Return(T t) { this.t = t; }

        @Override
        public TailCall<T> resume() {
            throw new IllegalStateException("No more calls to do");
        }

        @Override
        public T eval() {
            return t;
        }

        @Override
        public boolean isSuspended() {
            return false;
        }
    }

    public static class Suspend<T> extends TailCall<T> {
        private final Supplier<TailCall<T>> resume;

        private Suspend(Supplier<TailCall<T>> resume) { this.resume = resume; }

        @Override
        public TailCall<T> resume() {
            return resume.get();
        }

        @Override
        public T eval() {
            TailCall<T> currentCall = this;
            while (currentCall.isSuspended()) {
                currentCall = currentCall.resume();
            }
            return currentCall.eval();
        }

        @Override
        public boolean isSuspended() {
            return true;
        }
    }

    public static <T> Return<T> ret(T t) {
        return new Return<>(t);
    }

    public static <T> Suspend<T> sus(Supplier<TailCall<T>> t) {
        return new Suspend<>(t);
    }
}
