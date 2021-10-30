package designpatterns.proxy.virtualproxy;

public class BaseImageViewer implements ImageViewer {
	private Image image;

	public BaseImageViewer(String path) {
		image = new Image(path);
	}

	@Override
	public void display() {
		System.out.println(image);
	}
}
