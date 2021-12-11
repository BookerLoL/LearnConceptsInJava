package designpatterns.decorator;

public abstract class ExpensiveFoodDecorator extends FoodDecorator {
	public ExpensiveFoodDecorator(String description, double price, Food baseFood) {
		super(description, price, baseFood);
	}
	
	@Override
	public double getPrice() {
		return (this.baseFood.getPrice() + this.getPrice());
	}
}
