package datastructures;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * An iterative implementation of a leftist heap.
 * 
 * References:
 * https://userpages.umbc.edu/~chang/cs341.s17/park/L15-Leftist-Heaps-JP.pdf
 * https://en.wikipedia.org/wiki/Leftist_tree
 * 
 * Notes: The recursive implementation is a lot more shorter and more concise. I
 * decided not to implement the recursive version since Wikipedia pretty much
 * gives you an implementation.
 * 
 * @author Ethan
 *
 * @param <T>
 */

public class LeftistHeap<T extends Comparable<? super T>> {
	private class Node {
		T item;
		int rank;
		Node left;
		Node right;

		public Node(T item) {
			this.item = item;
		}

		public boolean isLeftEmpty() {
			return left == null;
		}

		boolean isRightHeavy() {
			if (right == null) {
				return false;
			}
			return right.rank > left.rank;
		}

		void swapChildren() {
			Node temp = left;
			left = right;
			right = temp;
		}
	}

	private Node root;
	private Comparator<T> comparator;

	public LeftistHeap() {
		this(Comparator.naturalOrder());
	}

	public LeftistHeap(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public boolean insert(T item) {
		root = merge(root, new Node(item));
		return true;
	}

	private Node merge(Node heap1, Node heap2) {
		if (heap1 == null) {
			return heap2;
		}
		if (heap2 == null) {
			return heap1;
		}

		Node tempRoot;
		if (comparator.compare(heap1.item, heap2.item) < 0) {
			tempRoot = heap1;
			heap1 = heap1.right;
		} else {
			tempRoot = heap2;
			heap2 = heap2.right;
		}

		// compare subtree roots and find path
		Stack<Node> path = new Stack<>();
		while (heap1 != null && heap2 != null) {
			path.push(tempRoot);
			if (comparator.compare(heap1.item, heap2.item) < 0) {
				tempRoot.right = heap1;
				tempRoot = heap1;
				heap1 = heap1.right;
			} else {
				tempRoot.right = heap2;
				tempRoot = heap2;
				heap2 = heap2.right;
			}
		}

		tempRoot.right = heap1 == null ? heap2 : heap1;
		path.push(tempRoot);
		// fix the children and rank going back up the path.
		while (!path.isEmpty()) {
			tempRoot = path.pop();

			if (tempRoot.isLeftEmpty() || tempRoot.isRightHeavy()) {
				tempRoot.swapChildren();
			}

			tempRoot.rank = tempRoot.right == null ? 1 : tempRoot.right.rank + 1;
		}

		return tempRoot;
	}

	public T removeMin() {
		if (this.isEmpty()) {
			return null;
		}

		T item = root.item;
		root = merge(root.left, root.right);
		return item;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void clear() {
		root = null;
	}

	public static void main(String[] args) {
		LeftistHeap<Integer> h = new LeftistHeap<>();

		int item = 1000000;
		int min = -100000000;
		int max = 100000000;
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
