package designpatterns.composite;

import java.util.ArrayList;
import java.util.Collection;

public class Tester {

	public static void main(String[] args) {
		Collection<Summable> numbers = new ArrayList<>();
		for (int i = 0; i <= 9; i++) {
			numbers.add(new Number(i));
		}

		Summable sum = new Numbers(numbers);
		System.out.println(sum.sum());

		Summable number = new Number(10);
		System.out.println(number.sum(sum).sum());
	}

}
