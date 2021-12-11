package designpatterns.decorator;

public class ExpensiveEggs extends ExpensiveFoodDecorator {

	public ExpensiveEggs(String description, double price, Food baseFood) {
		super(description, price, baseFood);
	}

	@Override
	public String getDescription() {
		return "Expensive eggs";
	}
}
