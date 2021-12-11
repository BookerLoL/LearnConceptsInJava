package datastructures;

import java.util.Objects;

public class CountMinSketch<T> {
	public interface Hashable<T> {
		public int hash(T item);
	}

	public static final int DEFAULT_CAPACITY = 16;
	private Hashable<T>[] hashFunctions;
	private int[][] counts;

	public CountMinSketch() {
		this(DEFAULT_CAPACITY, t -> Objects.hash(t));
	}

	@SafeVarargs
	public CountMinSketch(int capacity, Hashable<T>... hashFunctions) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("Capacity should be greater than 0");
		}

		Objects.requireNonNull(hashFunctions);
		if (hashFunctions.length == 0) {
			throw new IllegalArgumentException("Must provide hash functions");
		}
		counts = new int[hashFunctions.length][capacity];
	}

	public void insert(T item) {
		for (int hashIndex = 0; hashIndex < hashFunctions.length; hashIndex++) {
			Hashable<T> hasher = hashFunctions[hashIndex];
			int index = hasher.hash(item);
			counts[hashIndex][index]++;
		}
	}

	public void remove(T item) {
		for (int hashIndex = 0; hashIndex < hashFunctions.length; hashIndex++) {
			Hashable<T> hasher = hashFunctions[hashIndex];
			int index = hasher.hash(item);

			if (counts[hashIndex][index] != 0) {
				counts[hashIndex][index]--;
			}
		}
	}

	public int count(T item) {
		int min = Integer.MAX_VALUE;
		for (int hashIndex = 0; hashIndex < hashFunctions.length; hashIndex++) {
			Hashable<T> hasher = hashFunctions[hashIndex];
			int index = hasher.hash(item);
			min = Math.min(min, counts[hashIndex][index]);
		}

		return min;
	}
}
