package datastructures;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * An iterative implementation of a Skew Heap.
 * 
 * References: https://en.wikipedia.org/wiki/Skew_heap
 * 
 * @author Ethan
 *
 * @param <T>
 */
public class SkewHeap<T extends Comparable<? super T>> {
	private class Node {
		T item;
		Node left;
		Node right;

		public Node(T item) {
			this.item = item;
		}
	}

	Node root;
	Comparator<T> comparator;

	public SkewHeap() {
		this(Comparator.naturalOrder());
	}

	public SkewHeap(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public void clear() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public boolean insert(T item) {
		root = merge(root, new Node(item));
		return true;
	}

	public T removeMin() {
		if (isEmpty()) {
			return null;
		}

		T min = root.item;
		root = merge(root.left, root.right);
		return min;
	}

	private Node merge(Node heap1, Node heap2) {
		if (heap1 == null) {
			return heap2;
		}
		if (heap2 == null) {
			return heap1;
		}

		LinkedList<Node> subtrees = new LinkedList<>();
		while (heap1 != null && heap2 != null) {
			if (comparator.compare(heap1.item, heap2.item) < 0) {
				subtrees.push(heap1);
				heap1 = heap1.right;
			} else {
				subtrees.push(heap2);
				heap2 = heap2.right;
			}
		}

		while (heap1 != null) {
			subtrees.push(heap1);
			heap1 = heap1.right;
		}

		while (heap2 != null) {
			subtrees.push(heap2);
			heap2 = heap2.right;
		}

		while (subtrees.size() != 1) {
			heap1 = subtrees.pop();
			heap2 = subtrees.pop();

			if (comparator.compare(heap1.item, heap2.item) < 0) {
				heap1.right = heap1.left;
				heap1.left = heap2;
				subtrees.push(heap1);
			} else {
				heap2.right = heap2.left;
				heap2.left = heap1;
				subtrees.push(heap2);
			}
		}

		return subtrees.pop();
	}

	public static void main(String[] args) {
		SkewHeap<Integer> h = new SkewHeap<>();

		int item = 1000000;
		int min = -1000000;
		int max = 1000000;
		List<Integer> nums = new LinkedList<>();
		for (int i = 0; i < item; i++) {
			int num = min + (int) (Math.random() * ((max - min) + 1));
			nums.add(num);
		}

		nums.forEach(n -> h.insert(n));
		Collections.sort(nums);
		int half = nums.size() / 2;
		for (int i = 0; i < half; i++) {
			int val = h.removeMin();
			if (nums.remove(0) != val) {
				System.out.println("failed");
				break;
			}
		}
		System.out.println(nums.size());

		for (int i = 0; i < item; i++) {
			int num = min + (int) (Math.random() * ((max - min) + 1));
			nums.add(num);
			h.insert(num);
		}
		Collections.sort(nums);
		System.out.println(nums.size());
		int i = 0;
		for (int n : nums) {
			int val = h.removeMin();
			if (n != val) {
				System.out.println("failed");
				break;
			}
			i++;
		}
		System.out.println(nums.size() == i);
	}
}
