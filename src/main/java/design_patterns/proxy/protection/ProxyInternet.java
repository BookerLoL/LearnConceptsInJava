package designpatterns.proxy.protectionproxy;

import java.util.ArrayList;
import java.util.List;

public class ProxyInternet implements Internet {
	private Internet internet = new RealInternet();
	private static List<String> bannedUrls = new ArrayList<>();

	static {
		bannedUrls.add("test.com");
	}

	@Override
	public void access(String url) {
		if (bannedUrls.contains(url.toLowerCase())) {
			System.out.println("Sorry that site is blocked!");
			return;
		}

		internet.access(url);
	}
}
