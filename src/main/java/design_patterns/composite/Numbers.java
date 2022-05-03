package designpatterns.composite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Numbers implements Summable {
	private Collection<Summable> numbers;

	public Numbers(Iterable<Summable> numbers) {
		this.numbers = new ArrayList<>();
		numbers.iterator().forEachRemaining(this.numbers::add);
	}

	@Override
	public int sum() {
		return numbers.stream().reduce(Summable::add).orElse(Number.ZERO).sum();
	}

	@Override
	public Summable sum(Summable otherSum) {
		return new Numbers(Arrays.asList(new Number(this.sum()), new Number(otherSum.sum())));
	}
}
