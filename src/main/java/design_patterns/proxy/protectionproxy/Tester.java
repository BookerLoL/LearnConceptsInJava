package designpatterns.proxy.protectionproxy;

public class Tester {

	public static void main(String[] args) {
		Internet internet = new ProxyInternet();
		internet.access("test.com");
		internet.access("google.com");
	}

}
