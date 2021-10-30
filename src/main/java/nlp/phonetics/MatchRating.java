package nlp.phonetics;

/**
 * Match Rating Approach (MRA) for standard English names.
 * 
 * Performas well with names containing y. Does poorly if encoded names differ
 * by more than length 2. 
 * 
 * Source: https://en.wikipedia.org/wiki/Match_rating_approach
 * 
 * Source Date: January 09, 2021
 * 
 * @author Ethan Booker
 * @version 1.0
 */
public class MatchRating extends Phonetizer {
	private static final char[] VOWELS = { 'a', 'e', 'i', 'o', 'u' };
	private static final int MAX_ENCODING_LENGTH = 6;

	@Override
	public String encode(String name) {
		name = name.trim().toLowerCase();
		name = removeVowelsAndDoubleConsonant(name);
		return redux(name);
	}

	/*
	 * Delete vowels unless at start of the word
	 * 
	 * Remove double consonants to only 1
	 * 
	 * ex: ccccbbbb -> cb
	 */
	private String removeVowelsAndDoubleConsonant(String name) {
		StringBuilder sb = new StringBuilder(name.length());
		char prevChar = name.charAt(0); // first letter
		sb.append(prevChar);

		for (int i = 1; i < name.length(); i++) {
			char curr = name.charAt(i);
			if (curr != prevChar && !isVowel(curr, VOWELS)) {
				sb.append(curr);
				prevChar = curr;
			}
		}
		return sb.toString();
	}

	private String redux(String name) {
		if (name.length() <= MAX_ENCODING_LENGTH) {
			return name;
		}

		return repeat(name.charAt(0), 3) + repeat(name.charAt(name.length() - 1), 3);
	}

	private String repeat(char letter, int times) {
		StringBuilder sb = new StringBuilder(times);
		while (sb.length() < times) {
			sb.append(letter);
		}
		return sb.toString();
	}

	private int minimumThreshold(String encode1, String encode2) {
		int sumLength = encode1.length() + encode2.length();
		if (sumLength <= 4) {
			return 5;
		} else if (sumLength <= 7) {
			return 4;
		} else if (sumLength <= 11) {
			return 3;
		} else if (sumLength == 12) {
			return 2;
		} else {
			throw new IllegalArgumentException("The two strings should be encoded prior to use");
		}
	}

	/**
	 * Checks to see if the two encodings are similar.
	 * 
	 * @param encode1
	 * @param encode2
	 * @return if encodings are similar
	 * @throws NullPointerException if either encoding is null
	 */
	public boolean compare(String encode1, String encode2) {
		int lengthDiff = Math.abs(encode1.length() - encode2.length());
		if (lengthDiff >= 3) {
			return false;
		}

		int minRating = minimumThreshold(encode1, encode2);
		StringBuilder encode1Sb = new StringBuilder(encode1.length());
		StringBuilder encode2Sb = new StringBuilder(encode1.length());
		int j;
		int i = 0;

		while (i < encode1.length() && i < encode2.length()) { // read left to right
			char encode1Ch = encode1.charAt(i);
			char encode2Ch = encode2.charAt(i);
			if (encode1Ch != encode2Ch) {
				encode1Sb.append(encode1Ch);
				encode2Sb.append(encode2Ch);
			}
			i++;
		}

		while (i < encode1.length()) { // may have unprocessed chars
			encode1Sb.append(encode1.charAt(i));
			i++;
		}

		while (i < encode2.length()) {
			encode2Sb.append(encode2.charAt(i));
			i++;
		}

		StringBuilder encode1SbAfter = new StringBuilder(encode1Sb.length());
		StringBuilder encode2SbAfter = new StringBuilder(encode2Sb.length());

		i = encode1Sb.length() - 1;
		j = encode2Sb.length() - 1;
		while (i >= 0 && j >= 0) { // reading right to left
			char encode1Ch = encode1Sb.charAt(i);
			char encode2Ch = encode2Sb.charAt(j);
			if (encode1Ch != encode2Ch) {
				encode1SbAfter.append(encode1Ch);
				encode2SbAfter.append(encode2Ch);
			}
			i--;
			j--;
		}

		while (i >= 0) {
			encode1SbAfter.append(encode1Sb.charAt(i));
			i--;
		}

		while (j >= 0) {
			encode1SbAfter.append(encode1Sb.charAt(j));
			j--;
		}

		StringBuilder longer = encode1SbAfter.length() > encode2SbAfter.length() ? encode1SbAfter : encode2SbAfter;
		int similarityRating = 6 - longer.length();
		return similarityRating >= minRating;
	}
}
