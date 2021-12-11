package datastructures;

public class TwoChoiceHashing<T> {
	public interface Hashable<T> {
		public int hash(T item);
	}

	public static final int DEFAULT_CAPACITY = 16;

	public TwoChoiceHashing() {
		this(DEFAULT_CAPACITY);
	}

	public TwoChoiceHashing(int initCapacity) {
		this(initCapacity, null, null);
	}

	public TwoChoiceHashing(int initCapacity, Hashable<T> hasher1, Hashable<T> hasher2) {

	}
}
