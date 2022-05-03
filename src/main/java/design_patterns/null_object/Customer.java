package designpatterns.nullobject;

public abstract class Customer {
	protected String name;
	
	public Customer(String name) {
		this.name = name;
	}
	
	public abstract String getName();
}
