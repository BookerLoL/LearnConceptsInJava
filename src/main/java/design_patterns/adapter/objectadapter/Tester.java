package designpatterns.adapter.objectadapter;

public class Tester {
	public static void main(String[] args) {
		Human politeHuman = new PoliteHuman();
		Human rudeHuman = new RudeHuman();

		Client client = new Client(politeHuman);
		client.test();
		client.setHuman(rudeHuman);
		client.test();
	}
}