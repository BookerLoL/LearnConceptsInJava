package designpatterns.adapter.classadapter;

public class Tester {
	public static void main(String[] args) {
		Client client = new Client(new Adapter());
		System.out.println(client.getObjectId());
	}

}
