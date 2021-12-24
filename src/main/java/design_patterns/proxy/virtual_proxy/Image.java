package design_patterns.proxy.virtual_proxy;

public class Image {
	public Image(String path) {
		System.out.println("Loading image at: " + path + ", this is a really expensive thing to do");
	}

	public String toString() {
		return "Unknown image";
	}
}
