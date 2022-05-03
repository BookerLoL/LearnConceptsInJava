package designpatterns.facade;

public class Tester {

	public static void main(String[] args) {
		ByteLoaderFacade loader = new ByteLoaderFacade();
		loader.load(new Byte[] { 0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5 });
		loader.load(null);
	}

}
