package design_patterns.proxy.virtual_proxy;

public class ProxyImageViewer implements ImageViewer {

	private String path;
	private ImageViewer viewer;

	public ProxyImageViewer(String path) {
		this.path = path;
	}

	@Override
	public void display() {
		if (viewer == null) {
			viewer = new BaseImageViewer(path);
		}

		viewer.display();
	}

}
