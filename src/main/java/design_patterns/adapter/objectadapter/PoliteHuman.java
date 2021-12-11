package designpatterns.adapter.objectadapter;

public class PoliteHuman implements Human {

	@Override
	public void talk() {
		System.out.println("It's a pleasure to meet you.");
	}

}
