package designpatterns.composite;

public class Number implements Summable {
	public static Number ZERO = new Number(0);

	private int number;

	public Number(int number) {
		this.number = number;
	}

	@Override
	public int sum() {
		return number;
	}

	@Override
	public Summable sum(Summable otherSum) {
		return new Number(sum() + otherSum.sum());
	}

}
