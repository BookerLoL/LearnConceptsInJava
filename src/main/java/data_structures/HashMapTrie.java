package datastructures;

import java.util.HashMap;
import java.util.Map;

/**
 * An example of an uncompressed trie implemented using a hash map to support
 * other unicode letters.
 * 
 * Notes: There are plenty of other better approaches to implementing a trie.
 * 
 * References: https://en.wikipedia.org/wiki/Trie
 * 
 * @author Ethan
 *
 */
public class HashMapTrie {
	private class Node {
		private Map<Character, Node> edges;

		public Node() {
			edges = new HashMap<>();
		}
	}

	private static final char DEFAULT_TERMINATING_SYMBOL = '$';
	private Node root;

	public HashMapTrie() {
		root = new Node();
	}

	/**
	 * Inserts the input into the trie.
	 * 
	 * @param input any non empty lengthed string
	 * @return true if the input was not already present.
	 */
	public boolean insert(String input) {
		if (isInvalidInput(input)) {
			return false;
		}
		Node current = root;
		for (int i = 0; i < input.length(); i++) {
			current = current.edges.compute(input.charAt(i), (k, v) -> v == null ? new Node() : v);
		}
		return current.edges.compute(DEFAULT_TERMINATING_SYMBOL, (k, v) -> v == null ? v : v) == null;
	}

	/**
	 * Looks if the input is contained in the trie due to an insertion. If it's a
	 * prefix and was not inserted, find will return false.
	 * 
	 * 
	 * @param input the value to look for in the trie.
	 * @return true if the input exists in the trie
	 */
	public boolean find(String input) {
		Node end = get(input, root);
		return end != null && end.edges.containsKey(DEFAULT_TERMINATING_SYMBOL);
	}

	private Node get(String input, Node current) {
		for (int i = 0; i < input.length(); i++) {
			current = current.edges.getOrDefault(input.charAt(i), null);
			if (current == null) {
				break;
			}
		}
		return current;
	}

	/**
	 * Deletes any input that has been inserted.
	 * 
	 * @param input
	 * @return true if the input was inserted and present during deletion.
	 */
	public boolean delete(String input) {
		if (isInvalidInput(input)) {
			return false;
		}
		return deleteHelper(input, 0, root);
	}

	private boolean deleteHelper(String input, int index, Node current) {
		if (current == null) {
			return false;
		} else if (index == input.length()) {
			return current.edges.remove(DEFAULT_TERMINATING_SYMBOL) != null;
		}

		Node next = current.edges.getOrDefault(input.charAt(index), null);

		if (!deleteHelper(input, index + 1, next)) {
			return false;
		}

		// clean up memory while going back to the root.
		if (next.edges.isEmpty()) {
			current.edges.remove(input.charAt(index));
		}

		return true;
	}

	/**
	 * Finds whether or not an input is a prefix to any other inserted values.
	 * 
	 * A prefix should always have at least one character after it to be a
	 * considered a prefix for this method.
	 * 
	 * Example: prep, pre is would be a valid prefix in this case.
	 * 
	 * @param input the prefix
	 * @return true if the prefix is a prefix to another word.
	 */
	public boolean isPrefix(String input) {
		if (isInvalidInput(input)) {
			return false;
		}

		Node current = get(input, root);

		// never found a prefix or the input itself was a whole word.
		if (current == null || (current.edges.size() == 1 && current.edges.containsKey(DEFAULT_TERMINATING_SYMBOL))) {
			return false;
		}

		return true;
	}

	private boolean isInvalidInput(String input) {
		return input == null || input.isEmpty();
	}

	/**
	 * Removes all items currently stored in the trie
	 */
	public void clear() {
		root = new Node();
	}
}
