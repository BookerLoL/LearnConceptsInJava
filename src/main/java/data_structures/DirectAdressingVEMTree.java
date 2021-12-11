package datastructures;

/**
 * A Java implementation of a Van Emde Boas datastructures.tree using only direct addressing.
 * Check out other versions if you want better performance.
 * 
 * No dupicates are allowed
 * 
 * Supports ints from 0 to total numbers - 1 allowed. 16 -> 0 - 15 supported
 * 
 * Notes: Could consider implementing with a bit set to reduce memory footprint.
 * To allow more than the integer amount, a possibly extension is simply
 * creating an array / list to support additional number ranges.
 * 
 * http://www-di.inf.puc-rio.br/~laber/vanEmdeBoas.pdf
 * 
 * @author Ethan
 * @apiNote STILL A WORK IN PROGRESS
 */
public class DirectAdressingVEMTree {
	private int maxIndex;
	private int kBit;

	// insert, delete, member, min, max, successor, predecessor
	public DirectAdressingVEMTree(int totalNumbers) {
		kBit = powerOfTwoSupporting(totalNumbers);
	}

	private static int powerOfTwoSupporting(int number) {
		int power = 1;
		while ((1 << power) < number) {
			power++;
		}
		return power;
	}

	public static void main(String[] args) {
		System.out.println(powerOfTwoSupporting(16));
	}
}
