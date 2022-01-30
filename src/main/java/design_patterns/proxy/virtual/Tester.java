package design_patterns.proxy.virtual_proxy;

public class Tester {

	public static void main(String[] args) {
		ImageViewer viewer = new ProxyImageViewer("C://home");
		viewer.display();
	}

}
