package designpatterns.decorator;

public abstract class Food {
	private String description;
	private double price;
	
	public Food(String description, double price) {
		this.description = description;
		this.price = price;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getPrice() {
		return price;
	}
}
