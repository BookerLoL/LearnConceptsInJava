package datastructures;

/**
 * An example of an uncompressed trie implemented using an array for 'a-z'
 * letters.
 * 
 * References: https://en.wikipedia.org/wiki/Trie
 * 
 * @author Ethan
 *
 */
public class ArrayTrie {
	private class Node {
		// 'a-z' is 0-25, can determine index by doing 'char letter' - 'a'

		Node[] edges; // Will only initialize when needed.
		boolean isTerminating;

		public Node getNext(char letter) {
			if (edges == null) {
				return null;
			}
			return edges[letter - 'a'];
		}

		public Node getNextOrInitNext(char letter) {
			if (edges == null) {
				edges = new Node[26]; // 'a-z'
			}

			int index = letter - 'a';
			if (edges[index] == null) {
				edges[index] = new Node();
			}

			return edges[index];
		}

		public boolean isEmptyEdges() {
			for (Node node : edges) {
				if (node != null) {
					return false;
				}
			}
			return true;
		}

		public boolean isRemovable() {
			return edges == null || isEmptyEdges();
		}

		public void remove(char letter) {
			edges[letter - 'a'] = null;
		}
	}

	private Node root;

	public ArrayTrie() {
		root = new Node();
	}

	/**
	 * Inserts the input into the trie.
	 * 
	 * Only allowing valid English letters.
	 * 
	 * @param input any non empty lengthed string
	 * @return true if the input was not already present.
	 */
	public boolean insert(String input) {
		if (isInvalidInput(input)) {
			return false;
		}

		input = input.toLowerCase();
		Node current = root;
		for (int i = 0; i < input.length(); i++) {
			current = current.getNextOrInitNext(input.charAt(i));
		}

		// already been inserted
		if (current.isTerminating) {
			return false;
		}

		current.isTerminating = true;
		return true;
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
		if (isInvalidInput(input)) {
			return false;
		}
		Node end = get(input, root);
		return end != null && end.isTerminating;
	}

	private Node get(String input, Node current) {
		for (int i = 0; i < input.length(); i++) {
			current = current.getNext(input.charAt(i));
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
			if (current.isTerminating) {
				current.isTerminating = false;
				return true;
			}
			return false;
		}

		Node next = current.getNext(input.charAt(index));

		if (!deleteHelper(input, index + 1, next)) {
			return false;
		}

		// clean up memory while going back to the root.
		if (next.isRemovable()) {
			current.remove(input.charAt(index));
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

		// never found a prefix or the prefix itself was a whole word.
		return current != null && !current.isTerminating;
	}

	private boolean isInvalidInput(String input) {
		return input == null || input.isEmpty() || !containsAllLetters(input);
	}

	private boolean containsAllLetters(String input) {
		for (int i = 0; i < input.length(); i++) {
			if (!Character.isLetter(input.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Removes all items currently stored in the trie
	 */
	public void clear() {
		root = new Node();
	}
}
