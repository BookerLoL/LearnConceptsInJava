package designpatterns.service_locator;

public class InitialContext {
	public Object lookup(String name) {
		if (name.equalsIgnoreCase("SERVICE_1")) {
			return new Service1();
		} else if (name.equalsIgnoreCase("SERVICE_2")) {
			return new Service2();
		}

		return null;
	}
}
