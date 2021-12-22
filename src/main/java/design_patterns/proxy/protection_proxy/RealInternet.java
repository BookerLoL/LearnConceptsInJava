package designpatterns.proxy.protectionproxy;

public class RealInternet implements Internet {

	@Override
	public void access(String url) {
		System.out.println("Loading up " + url);
	}
}
