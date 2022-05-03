package functional.pys_fp_book;

import java.util.Objects;

public class StateTuple<A, S>  {
    public final A value;
    public final S state;

    public StateTuple(A a, S s) {
        value = a;
        state = Objects.requireNonNull(s);
    }

    @Override
    public String toString() {
        return String.format("(value: %s, state: %s)", value, state);
    }
}
