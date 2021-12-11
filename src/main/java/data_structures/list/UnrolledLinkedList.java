package data_structures.list;

import java.util.Objects;

/**
 * A possible implementation of an Unrolled Linked List.
 * <p>
 * This implementation does not handled fragmented data where buckets get low
 * and do not end up combining.
 * <p>
 * A node will be initialized to prevent checking for null case at the start.
 *
 * @param <T>
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/Unrolled_linked_list">Wikipedia</a>
 */
public class UnrolledLinkedList<T> {
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

		public int contains(T item) {
			for (int i = 0; i < numElements; i++) {
				if (Objects.equals(data[i], item)) {
					return i;
				}
			}
			return NOT_FOUND;
		}

		public boolean remove(T item) {
			int index = contains(item);
			if (index == NOT_FOUND) {
				return false;
			}

			if (index < numElements - 1) {
				System.arraycopy(data, index + 1, data, index, numElements - index);
			}
			data[index] = null;
			numElements--;
			return true;
		}
	}

	public static final int DEFAULT_BLOCK_SIZE = 4;
	private static final int NOT_FOUND = -1;

	private final int blockSize;
	private Node<T> firstNode;

	public UnrolledLinkedList() {
		this(DEFAULT_BLOCK_SIZE);
	}

	public UnrolledLinkedList(int blockSize) {
		this.blockSize = blockSize > DEFAULT_BLOCK_SIZE ? blockSize : DEFAULT_BLOCK_SIZE;
		firstNode = new Node<>(blockSize);
	}

	public boolean add(T item) {
		Node<T> lastNode = getLastNode();
		if (lastNode.isFull()) {
			lastNode.next = new Node<>(blockSize);
			lastNode = lastNode.next;
		}
		lastNode.data[lastNode.numElements] = item;
		lastNode.numElements++;
		return true;
	}

	public boolean remove(T item) {
		for (Node<T> temp = firstNode; temp != null; temp = temp.next) {
			if (temp.remove(item)) {
				if (temp.isEmpty()) {
					removeNode(temp);
				}
				return true;
			}
		}
		return false;
	}

	public int indexOf(T item) {
		int index = 0;
		for (Node<T> temp = firstNode; temp != null; temp = temp.next) {
			int nodeIndex = temp.contains(item);
			if (nodeIndex != NOT_FOUND) {
				return index + nodeIndex;
			}
			index += temp.numElements;
		}
		return NOT_FOUND;
	}

	public T get(int index) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException("Index: " + index + " is not greater than 0 and less than " + size());
		}

		int i = 0;
		for (Node<T> temp = firstNode; temp != null; temp = temp.next) {
			for (int j = 0; j < temp.numElements; j++, i++) {
				if (i == index) {
					return (T) temp.data[j];
				}
			}
		}
		return null;
	}

	public boolean contains(T item) {
		return indexOf(item) != NOT_FOUND;
	}

	private void removeNode(Node<T> node) {
		if (node == null) {
			return;
		}

		if (node == firstNode && node.isEmpty() && node.next != null) {
			firstNode = node.next;
			return;
		}

		Node<T> beforeNode = getNodeBefore(node);
		beforeNode.next = beforeNode.next.next;
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

	public int size() {
		int size = 0;
		for (Node<T> temp = firstNode; temp != null; temp = temp.next) {
			size += temp.numElements;
		}
		return size;
	}

	public boolean isEmpty() {
		return firstNode.numElements == 0;
	}
}