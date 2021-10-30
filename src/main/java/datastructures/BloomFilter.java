package datastructures;

import java.util.BitSet;
import java.util.Objects;

/**
 * An example of how to implement a bloom filter.
 * 
 * @author Ethan
 *
 * @param <T>
 */
public class BloomFilter<T> {
	public interface Hashable<T> {
		public int hash(T item);
	}

	public static final int DEFAULT_CAPACITY = 16;
	private Hashable<T>[] hashFunctions;
	private BitSet bits;

	public BloomFilter() {
		this(DEFAULT_CAPACITY, t -> Objects.hash(t));
	}

	@SafeVarargs
	public BloomFilter(int capacity, Hashable<T>... hashFunctions) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("Capacity should be greater than 0");
		}

		Objects.requireNonNull(hashFunctions);
		if (hashFunctions.length == 0) {
			throw new IllegalArgumentException("Must provide hash functions");
		}

		bits = new BitSet(capacity);
	}

	public void insert(T item) {
		for (Hashable<T> hasher : hashFunctions) {
			int index = hasher.hash(item) % bits.size();
			bits.set(index);
		}
	}

	// may contain it
	public boolean contains(T item) {
		for (Hashable<T> hasher : hashFunctions) {
			int index = hasher.hash(item) % bits.size();
			if (!bits.get(index)) {
				return false;
			}
		}

		return true;
	}
}
