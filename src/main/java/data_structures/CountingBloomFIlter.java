package data_structures;

import java.util.Objects;

/**
 * An example on how to implement a counting bloom filter.
 * 
 * @author Ethan
 *
 * @param <T>
 */
public class CountingBloomFIlter<T> {
	public interface Hashable<T> {
		public int hash(T item);
	}

	public static final int DEFAULT_CAPACITY = 16;
	private Hashable<T>[] hashFunctions;
	private int[] counts;

	public CountingBloomFIlter() {
		this(DEFAULT_CAPACITY, t -> Objects.hash(t));
	}

	@SafeVarargs
	public CountingBloomFIlter(int capacity, Hashable<T>... hashFunctions) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("Capacity should be greater than 0");
		}

		Objects.requireNonNull(hashFunctions);
		if (hashFunctions.length == 0) {
			throw new IllegalArgumentException("Must provide hash functions");
		}
		counts = new int[capacity];
	}

	public void insert(T item) {
		for (Hashable<T> hasher : hashFunctions) {
			int index = hasher.hash(item) % counts.length;
			counts[index]++;
		}
	}

	public void remove(T item) {
		for (Hashable<T> hasher : hashFunctions) {
			int index = hasher.hash(item) % counts.length;

			if (counts[index] != 0) {
				counts[index]--;
			}
		}
	}

	// may contain it
	public boolean contains(T item) {
		for (Hashable<T> hasher : hashFunctions) {
			int index = hasher.hash(item) % counts.length;
			if (counts[index] == 0) {
				return false;
			}
		}

		return true;
	}
}
