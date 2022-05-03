package design_patterns.factory.factorymethod;

public abstract class Shape {
	protected String name;

	public Shape(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
