
package design_patterns.composite;

public interface Summable {
	int sum();

	Summable sum(Summable otherSum);

	static Summable add(Summable a, Summable b) {
		return a.sum(b);
	}
}
