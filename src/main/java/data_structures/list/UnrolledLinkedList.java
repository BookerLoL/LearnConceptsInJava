package data_structures.list;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * A possible implementation of an Unrolled Linked List.
 * <p>
 * This implementation only merges / borrows from its next node reference.
 * <p>
 * A node will be initialized to prevent checking for null case at the start.
 *
 * @param <T>
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/Unrolled_linked_list">Wikipedia</a>
 */
public class UnrolledLinkedList<T> extends AbstractList<T> {
	private static class Node<T> {
		int numElements;
		Object[] data;
		Node<T> next;

		public Node(int blockSize) {
			data = new Object[blockSize];
		}

		public boolean isFull() {
			return numElements == data.length;
		}

		public boolean isEmpty() {
			return numElements == 0;
		}

		public int indexOf(Object item) {
			for (int i = 0; i < numElements; i++) {
				if (Objects.equals(data[i], item)) {
					System.out.println("Index of:" + i);
					return i;
				}
			}
			return NOT_FOUND;
		}

		public void remove(Object item) {
			remove(indexOf(item));
		}

		@SuppressWarnings("unchecked")
		public T remove(int index) {
			if (index < 0 || index > numElements) {
				return null;
			}
			T oldData = (T) data[index];
			System.arraycopy(data, index + 1, data, index, numElements - index - 1);
			numElements--;
			data[numElements] = null;
			return oldData;
		}

		public boolean add(T item) {
			return add(item, numElements);
		}

		public static <T> boolean moveDataFromEndToStart(Node<T> from, Node<T> to, final int amount) {
			if (amount < 1 || to == null || amount >= from.numElements || to.isOverCapacity(amount)) {
				return false;
			}

			if (to.numElements != 0) {
				System.arraycopy(to.data, 0, to.data, amount, to.numElements);
			}
			System.arraycopy(from.data, from.numElements - amount, to.data, 0, amount);
			Arrays.fill(from.data, from.numElements - amount, from.numElements, null);
			to.numElements += amount;
			from.numElements -= amount;
			return true;
		}

		protected void split(final int numElementsToNextNode) {
			Node<T> newNext = new Node<>(data.length);
			moveDataFromEndToStart(this, newNext, numElementsToNextNode);
			newNext.next = next;
			next = newNext;
		}

		public boolean isOverCapacity(int extraData) {
			return numElements + extraData > data.length;
		}

		public boolean add(T item, int index) {
			if (isFull()) {
				if (canSplit(MIN_AMOUNT_TO_MOVE)) {
					split(numElements / 2);
				} else {
					moveDataFromEndToStart(this, next, MIN_AMOUNT_TO_MOVE);
				}
			}

			if (index > numElements) {
				next.add(item, index - numElements);
			} else {
				System.arraycopy(data, index, data, index + 1, data.length - index - 1);
				data[index] = item;
				numElements++;
			}
			return true;
		}

		private boolean canSplit(int amountToSplit) {
			return next == null || next.isOverCapacity(amountToSplit);
		}

		public String toString() {
			return Arrays.toString(data) + " number of elements: " +
					numElements;
		}
	}

	public static final int DEFAULT_BLOCK_SIZE = 4;
	private static final int NOT_FOUND = -1;
	private static final int MIN_AMOUNT_TO_MOVE = 1;

	private final int blockSize;
	private final int minCapacity;
	private Node<T> firstNode;
	int size = 0;

	public UnrolledLinkedList() {
		this(DEFAULT_BLOCK_SIZE);
	}

	public UnrolledLinkedList(int blockSize) {
		this.blockSize = getValidBlockSize(blockSize);
		this.minCapacity = calculateMiniumCapacity(getBlockSize());
		this.firstNode = new Node<>(getBlockSize());
	}

	private int getValidBlockSize(int blockSize) {
		return Math.max(blockSize, DEFAULT_BLOCK_SIZE);
	}

	private int calculateMiniumCapacity(int blockSize) {
		return blockSize / 2;
	}

	@Override
	public boolean add(T item) {
		size++;
		return getLastNode().add(item);
	}

	@Override
	public void add(int index, T item) {
		rangeCheckForAdd(index);
		if (index == size) {
			size++;
			getLastNode().add(item);
			return;
		}

		int currIndex = 0;
		for (Node<T> temp = firstNode; temp != null; temp = temp.next) {
			if (currIndex + temp.numElements >= index) {
				int nodeIndex = index - currIndex;
				temp.add(item, nodeIndex);
				size++;
				break;
			} else {
				currIndex += temp.numElements;
			}
		}
	}

	private boolean isBelowMinCapacity(int numElements) {
		return numElements < minCapacity;
	}

	public int getBlockSize() {
		return blockSize;
	}

	@Override
	public boolean remove(Object item) {
		for (Node<T> temp = firstNode; temp != null; temp = temp.next) {
			if (temp.indexOf(item) != NOT_FOUND) {
				temp.remove(item);
				attemptReallocateNodeItems(temp);
				size--;
				return true;
			}
		}
		return false;
	}

	@Override
	public T remove(int index) {
		Objects.checkIndex(index, size);
		int currIndex = 0;
		for (Node<T> temp = firstNode; temp != null; temp = temp.next) {
			if (currIndex + temp.numElements > index) {
				T removedData = temp.remove(index - currIndex);
				attemptReallocateNodeItems(temp);
				size--;
				return removedData;
			} else {
				currIndex += temp.numElements;
			}
		}
		return null;
	}

	private void attemptReallocateNodeItems(Node<T> node) {
		if (node == null || !isBelowMinCapacity(node.numElements) || borrowFromNext(node)) {
			return;
		}

		if (Node.moveDataFromEndToStart(node, node.next, node.numElements) || node.isEmpty()) {
			removeNode(node);
		}
	}

	private void removeNode(Node<T> node) {
		if (node == firstNode) {
			if (node.next != null) {
				firstNode = node.next;
			}
			return;
		}
		Node<T> beforeNode = getNodeBefore(node);
		beforeNode.next = node.next;
	}

	private Node<T> getLastNode() {
		return getNodeBefore(null);
	}

	private Node<T> getNodeBefore(Node<T> target) {
		Node<T> temp = firstNode;
		while (temp.next != target) {
			temp = temp.next;
		}
		return temp;
	}

	private boolean borrowFromNext(Node<T> node) {
		if (node.next == null) {
			return false;
		}

		Node<T> nextNode = node.next;
		int numItemsNeed = minCapacity - node.numElements;

		if (isBelowMinCapacity(nextNode.numElements - numItemsNeed)) {
			return false;
		}

		System.arraycopy(nextNode.data, 0, node.data, node.numElements, numItemsNeed);
		System.arraycopy(nextNode.data, numItemsNeed, nextNode.data, 0, nextNode.numElements - numItemsNeed);
		Arrays.fill(nextNode.data, nextNode.numElements - numItemsNeed, nextNode.numElements, null);

		node.numElements += numItemsNeed;
		nextNode.numElements -= numItemsNeed;
		return true;
	}

	@Override
	public int indexOf(Object item) {
		int index = -1;
		for (Node<T> temp = firstNode; temp != null; temp = temp.next) {
			int nodeIndex = temp.indexOf(item);
			if (nodeIndex != NOT_FOUND) {
				return index + nodeIndex;
			}
			index += temp.numElements;
		}
		return NOT_FOUND;
	}

	@Override
	public int lastIndexOf(Object o) {
		int index = 0;
		int latestIndex = NOT_FOUND;
		for (Node<T> temp = firstNode; temp != null; temp = temp.next) {
			int nodeIndex = temp.indexOf(o);
			if (nodeIndex != NOT_FOUND) {
				latestIndex = index + nodeIndex;
			}
			index += temp.numElements;
		}
		return latestIndex;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(int index) {
		Objects.checkIndex(index, size);

		int currIndex = 0;
		for (Node<T> temp = firstNode; temp != null; temp = temp.next) {
			if (currIndex + temp.numElements > index) {
				return (T) temp.data[index - currIndex];
			}
			currIndex += temp.numElements;
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T set(int index, T element) {
		Objects.checkIndex(index, size);

		int currIndex = 0;
		for (Node<T> temp = firstNode; temp != null; temp = temp.next) {
			if (currIndex + temp.numElements > index) {
				T oldData = (T) temp.data[index - currIndex];
				temp.data[index - currIndex] = element;
				return oldData;
			}
			currIndex += temp.numElements;
		}
		return null;
	}

	private void rangeCheckForAdd(int index) {
		if (index > size() || index < 0) {
			throw new IndexOutOfBoundsException(
					"Index: " + index + " is not greater than 0 and less than or equal to " + size());
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		Arrays.fill(firstNode.data, null);
		firstNode.numElements = 0;
		firstNode.next = null;
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) != NOT_FOUND;
	}

	@Override
	public Iterator<T> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {
		throw new UnsupportedOperationException();
	}

	public void print() {
		for (Node<T> node = firstNode; node != null; node = node.next) {
			System.out.println(node);
		}
	}
}