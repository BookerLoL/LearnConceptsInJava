package designpatterns.observer;

public class Tester {

	public static void main(String[] args) {
		Alert newsAlerts = new Alert();
		Phone p1 = new Phone();
		Phone p2 = new Phone();

		newsAlerts.addObserver(p1);
		newsAlerts.addObserver(p2);

		newsAlerts.notify(
				"This just in, a police chase down highway 99 is underway and police are warning everyone to stay away.");
	}

}
