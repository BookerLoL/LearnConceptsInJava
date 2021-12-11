package designpatterns.adapter.objectadapter;

public class Client {
	Robot robot;

	public Client(Human mimickHuman) {
		robot = new HumanAdapter(mimickHuman);
	}

	public void test() {
		robot.speak();
	}

	public void setHuman(Human mimickHuman) {
		robot = new HumanAdapter(mimickHuman);
	}
}
