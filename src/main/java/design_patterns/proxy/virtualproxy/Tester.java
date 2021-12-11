package designpatterns.proxy.virtualproxy;

public class Tester {

	public static void main(String[] args) {
		ImageViewer viewer = new ProxyImageViewer("C://home");
		viewer.display();
	}

}
