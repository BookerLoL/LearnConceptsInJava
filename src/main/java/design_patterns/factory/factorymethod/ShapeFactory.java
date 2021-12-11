package designpatterns.factorymethod;

public class ShapeFactory {
	public static Shape create(String name) {
		if (name == null) {
			return null;
		}

		if (name.equalsIgnoreCase(Circle.NAME)) {
			return new Circle();
		} else if (name.equalsIgnoreCase(Square.NAME)) {
			return new Square();
		}

		return null;
	}
}
