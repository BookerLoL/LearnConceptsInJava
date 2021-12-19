package data_structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A hash table that uses extendible hashing.
 * 
 * Null values are not allowed.
 * 
 * Resources: https://www2.cs.sfu.ca/CourseCentral/354/lxwu/notes/chapter11.pdf
 * https://www.geeksforgeeks.org/extendible-hashing-dynamic-approach-to-dbms/
 * 
 * This is primarily useful for using as an index in a database.
 * 
 * @author Ethan
 * @version 1.0
 * @since 2021-03-28
 *
 * @param <K> key type
 * @param <V> value type
 */
public class ExtendibleHashTable<K, V> implements Map<K, V> {
	// global depth can't exceed more than 32 bits due to an int hash
	public static final int DEFAULT_BUCKET_SIZE = 4;

	// Need at least 1 bit to compare
	private static final int MIN_DEPTH = 1;
	private static final int NOT_FOUND = -1;
	private static final int MAX_INT_BITS = 32;

	private byte globalDepth;
	private int bucketSize;
	private Object[] directory;
	private int size;
	private Function<Object, Integer> hasher;

	private class Bucket {
		byte localDepth;
		int numKeys; // Number of current keys
		K[] keys;
		V[] values;

		@SuppressWarnings("unchecked")
		public Bucket(byte depth) {
			localDepth = depth;
			keys = (K[]) new Object[bucketSize];
			values = (V[]) new Object[bucketSize];
		}

		boolean insert(K key, V value) {
			if (isFull()) {
				return false;
			}

			keys[numKeys] = key;
			values[numKeys] = value;
			numKeys++;
			return true;
		}

		public V insert(K key, V value, int index) {
			V oldValue = values[index];
			values[index] = value;
			keys[index] = key;
			return oldValue;
		}

		public V remove(Object key) {
			int index = indexOf(key, keys);

			if (index == NOT_FOUND) {
				return null;
			}

			V oldValue = values[index];

			// Shift items left, and clean up memory
			System.arraycopy(values, index + 1, values, index, numKeys - index - 1);
			System.arraycopy(keys, index + 1, keys, index, numKeys - index - 1);
			numKeys--;
			keys[numKeys] = null;
			values[numKeys] = null;
			size--;
			return oldValue;
		}

		/**
		 * Splits the entries in this bucket to another bucket. Ensures both buckets
		 * have their correct entries based on the leading bit. The local depth will
		 * increase by one and both buckets will have the same local depth
		 * 
		 * @param hash The hash of the key
		 * @return The new split bucket.
		 */
		public Bucket split(int hash) {
			localDepth++;
			numKeys = 0;
			Bucket otherBucket = new Bucket(localDepth);

			String input = getLeastSigBitsBinaryForm(hash, localDepth);
			char leadingBit = input.charAt(0);
			splitEntries(otherBucket, leadingBit);

			Arrays.fill(keys, numKeys, keys.length, null); // Cleaning up memory
			Arrays.fill(values, numKeys, values.length, null);
			return otherBucket;
		}

		/**
		 * Splits content of current bucket with other bucket. There are instances where
		 * no entries will be distributed to the other bucket due to the entries in the
		 * current bucket.
		 * 
		 * @param otherBucket Other bucket to split items with.
		 * @param leadingBit  If binary bit is equal to leading bit then the current
		 *                    bucket will get the item otherwise other bucket gets the
		 *                    item.
		 */
		private void splitEntries(Bucket otherBucket, char leadingBit) {
			final int leadingBitPos = 0;
			for (int i = 0; i < keys.length; i++) {
				String binaryForm = getLeastSigBitsBinaryForm(hasher.apply(keys[i]), localDepth);
				if (binaryForm.charAt(leadingBitPos) == leadingBit) {
					this.insert(keys[i], values[i]);
				} else {
					otherBucket.insert(keys[i], values[i]);
				}
			}
		}

		// Bucket will copy everything the other bucket's contents.
		public void borrowAll(Bucket other) {
			numKeys = other.numKeys;
			System.arraycopy(other.keys, 0, keys, 0, numKeys);
			System.arraycopy(other.values, 0, values, 0, numKeys);
		}

		int indexOf(Object o, Object[] objects) {
			for (int key = 0; key < numKeys; key++) {
				if (Objects.equals(objects[key], o)) {
					return key;
				}
			}

			return NOT_FOUND;
		}

		boolean contains(Object o, Object[] objects) {
			return indexOf(o, objects) != NOT_FOUND;
		}

		public boolean isFull() {
			return numKeys == bucketSize;
		}

		public boolean isEmpty() {
			return numKeys == 0;
		}

		void decrementDepth() {
			if (localDepth > MIN_DEPTH) {
				localDepth--;
			}
		}

		public String toString() {
			return String.format("Bucket, depth=%d, total keys=%d, keys=%s, values=%s", localDepth, numKeys,
					Arrays.toString(keys), Arrays.toString(values));
		}
	}

	// Default hash function from
	// https://github.com/frohoff/jdk8u-jdk/blob/master/src/share/classes/java/util/HashMap.java
	final static Function<Object, Integer> DEFAULT_HASHER = key -> {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	};

	public ExtendibleHashTable() {
		this(DEFAULT_BUCKET_SIZE);
	}

	public ExtendibleHashTable(int bucketSize) {
		this(bucketSize, DEFAULT_HASHER);
	}

	public ExtendibleHashTable(int bucketSize, Function<Object, Integer> hashFunction) {
		Objects.requireNonNull(hashFunction);
		if (bucketSize < 0) {
			throw new IllegalArgumentException("Bucket size must be greater than 0");
		}

		globalDepth = MIN_DEPTH;
		hasher = hashFunction;
		this.bucketSize = bucketSize;
		resetDirectoryBuckets();
	}

	private int totalPotentialBuckets() {
		// Working with powers of 2
		return 1 << globalDepth;
	}

	@SuppressWarnings("unchecked")
	private Bucket getBucket(int hash) {
		int directoryIndex = getLeastSigBitsValue(hash, globalDepth);
		return (Bucket) directory[directoryIndex];
	}

	/**
	 * Returns <tt>true</tt> if this map contains a mapping for the specified key.
	 *
	 * @param key The key whose presence in this map is to be tested
	 * @return <tt>true</tt> if this map contains a mapping for the specified key.
	 */
	@Override
	public boolean containsKey(Object key) {
		Objects.requireNonNull(key);
		Bucket keyBucket = getBucket(hasher.apply(key));
		return keyBucket.contains(key, keyBucket.keys);
	}

	/**
	 * Returns <tt>true</tt> if the hashtable contains a mapping for the specified
	 * value. This is a very computationally expensive operation.
	 * 
	 * @param The value whose presence in this hashtable to be tested.
	 * @return <tt>true</tt> if the hashtable contains a mapping for the specified
	 *         value, otherwise false.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean containsValue(Object value) {
		Objects.requireNonNull(value);

		Set<Bucket> seenBuckets = new HashSet<>();
		for (Object bucket : directory) {
			Bucket b = (Bucket) bucket;
			if (seenBuckets.add(b)) {
				if (b.contains(value, b.values)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns the associated value that the key maps to.
	 * 
	 * @param The key to retrieve the associated value.
	 * @return The value associated with the key. Returns null if there is no
	 *         associated value.
	 */
	@Override
	public V get(Object key) {
		Objects.requireNonNull(key);
		Bucket keyBucket = getBucket(hasher.apply(key));
		int index = keyBucket.indexOf(key, keyBucket.keys);
		return index != NOT_FOUND ? keyBucket.values[index] : null;
	}

	/**
	 * Returns the previous associated value that was overrided by the new value
	 * given the key.
	 *
	 * @param The key create or override an association.
	 * @param The value override and store.
	 * @return The previous value that was overrided.
	 */
	@Override
	public V put(K key, V value) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(value);
		return putHelper(key, value);
	}

	private V putHelper(K key, V value) {
		int hash = hasher.apply(key);
		Bucket keyBucket = getBucket(hash);

		int index = keyBucket.indexOf(key, keyBucket.keys);
		if (index != NOT_FOUND) {
			return keyBucket.insert(key, value, index);
		} else if (keyBucket.insert(key, value)) {
			size++;
			return null;
		}

		if (keyBucket.localDepth == globalDepth) {
			growDirectory();
		}

		// split buckets
		Bucket splitBucket = keyBucket.split(hash);
		Collection<Integer> samePointingDirectories = getAllDirectoryIndicesPointingTo(keyBucket);

		int bitsToMatch = getLeastSigBitsValue(hash, keyBucket.localDepth);
		Collection<Integer> splitDirectoriesIndices = samePointingDirectories.stream()
				.filter(directoryBin -> getLeastSigBitsValue(directoryBin, keyBucket.localDepth) != bitsToMatch)
				.collect(Collectors.toList());

		for (int directoryIndex : splitDirectoriesIndices) {
			directory[directoryIndex] = splitBucket;
		}

		// recursively try to reinsert in case of numerous splits
		return putHelper(key, value);
	}

	private Collection<Integer> getAllDirectoryIndicesPointingTo(Bucket bucket) {
		Collection<Integer> directoryIndices = new LinkedList<>();
		for (int i = 0; i < directory.length; i++) {
			if (directory[i] == bucket) {
				directoryIndices.add(i);
			}
		}
		return directoryIndices;
	}

	private void growDirectory() {
		globalDepth++;
		Object[] newDirectory = new Object[totalPotentialBuckets()];
		int halfDirectoryPosition = totalPotentialBuckets() / 2;

		// Copy contents of first half into second half
		for (int i = 0; i < directory.length; i++) {
			newDirectory[i] = directory[i];
			newDirectory[i + halfDirectoryPosition] = directory[i];
		}
		directory = newDirectory;
	}

	@SuppressWarnings("unchecked")
	public V remove(Object key) {
		Objects.requireNonNull(key);

		int hash = hasher.apply(key);
		Bucket keyBucket = getBucket(hash);

		V value = keyBucket.remove(key);

		if (keyBucket.isEmpty() && globalDepth > MIN_DEPTH) {
			int keyBucketDirectory = getLeastSigBitsValue(hash, globalDepth);
			int upperHalfBucketDirectory = totalPotentialBuckets() / 2;

			if (keyBucketDirectory < upperHalfBucketDirectory) {
				Bucket upperHalfSiblingBucket = (Bucket) this.directory[upperHalfBucketDirectory + keyBucketDirectory];

				keyBucket.borrowAll(upperHalfSiblingBucket);
				keyBucket.decrementDepth();
				updateDirectoryPointers(upperHalfSiblingBucket, keyBucket);
			}

			while (canCollapseDirectory()) {
				collapseDirectory();
			}

			if (globalDepth == MIN_DEPTH) {
				/*
				 * Special cases where after collapsing or borrowing from rippled buckets, the
				 * bucket's depth may not necessarily be correct.
				 */
				for (int i = 0; i < this.totalPotentialBuckets(); i++) {
					Bucket b = (Bucket) directory[i];
					b.localDepth = 1;
				}
			}
		}

		return value;
	}

	private void updateDirectoryPointers(Bucket oldBucket, Bucket newBucket) {
		for (int i : getAllDirectoryIndicesPointingTo(oldBucket)) {
			this.directory[i] = newBucket;
		}
	}

	@SuppressWarnings("unchecked")
	private boolean canCollapseDirectory() {
		if (globalDepth == MIN_DEPTH) {
			return false;
		}

		final int fullBuckets = totalPotentialBuckets();
		for (int i = fullBuckets / 2; i < fullBuckets; i++) {
			Bucket bucket = (Bucket) directory[i];

			if (!bucket.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	private void collapseDirectory() {
		globalDepth--;
		Object[] halfDirectory = new Object[totalPotentialBuckets()];
		System.arraycopy(directory, 0, halfDirectory, 0, halfDirectory.length);
		directory = halfDirectory;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		Objects.requireNonNull(map);
		map.entrySet().forEach(entry -> put(entry.getKey(), entry.getValue()));
	}

	@Override
	public void clear() {
		size = 0;
		globalDepth = MIN_DEPTH;
		resetDirectoryBuckets();
	}

	private void resetDirectoryBuckets() {
		directory = new Object[totalPotentialBuckets()];
		for (int i = 0; i < directory.length; i++) {
			directory[i] = new Bucket(globalDepth);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keySet() {
		Set<K> keys = new HashSet<>();

		for (Object bucket : directory) {
			Bucket b = (Bucket) bucket;
			for (int i = 0; i < b.numKeys; i++) {
				keys.add(b.keys[i]);
			}
		}

		return keys;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> values() {
		Collection<V> values = new ArrayList<>();

		for (Object bucket : directory) {
			Bucket b = (Bucket) bucket;
			for (int i = 0; i < b.numKeys; i++) {
				values.add(b.values[i]);
			}
		}

		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> entries = new HashSet<>();

		for (Object bucket : directory) {
			Bucket b = (Bucket) bucket;
			for (int i = 0; i < b.numKeys; i++) {
				entries.add(new Pair<>(b.keys[i], b.values[i]));
			}
		}

		return entries;
	}

	/**
	 * Retrieves the number of entries in the hash table.
	 * 
	 * @return The number of entries in the hash table
	 */
	public int size() {
		return size;
	}

	/**
	 * Determines if the hash table has any entries or not.
	 * 
	 * @return True if there are no entries, false otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Builds the binary string representation of the given value for n least
	 * signiticant bits building up the string from left to right by masking each
	 * position for the specific bit at that position.
	 * 
	 * <pre>
	 * String leastBits = getLeastSigBitsBinaryFormStr(122, 4);
	 * leastBits.equals("1010");
	 * </pre>
	 * 
	 * 
	 * @param value value The value you want to get your bits from
	 * @param nBits nBits the amount of bits you want.
	 * @return The least significant n bits as string
	 */
	private static String getLeastSigBitsBinaryForm(int value, int nBits) {
		StringBuilder binarySB = new StringBuilder(nBits);
		nBits = Math.min(nBits, MAX_INT_BITS);
		for (int i = nBits - 1; i >= 0; i--) {
			int mask = 1 << i;
			char bit = (value & mask) == 0 ? '0' : '1';
			binarySB.append(bit);
		}
		return binarySB.toString();
	}

	/**
	 * Convert the given value into X least significant bits as an int value.
	 * 
	 * @param value The value you want to get your bits from
	 * @param nBits the amount of bits you want. Max of 32 bits.
	 * @return the least significant n bits as an int
	 */
	private static int getLeastSigBitsValue(int value, int nBits) {
		return Integer.parseInt(getLeastSigBitsBinaryForm(value, nBits), 2);
	}
}