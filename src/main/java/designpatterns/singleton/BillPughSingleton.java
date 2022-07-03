package designpatterns.singleton;

public class BillPughSingleton {
	private static class Singleton {
		private static final BillPughSingleton INSTANCE = new BillPughSingleton();
	}

	public static BillPughSingleton getInstance() {
		return Singleton.INSTANCE;
	}

	public void test() {
		System.out.println("Singleton!");
	}
}
