package designpatterns.decorator;

public abstract class FoodDecorator extends Food {
	protected Food baseFood;
	
	public FoodDecorator(String description, double price, Food baseFood) {
		super(description, price);
		this.baseFood = baseFood;
	}
}
