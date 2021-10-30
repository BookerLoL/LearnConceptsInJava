package designpatterns.chainofresponsibility;

public class FileLogger extends Logger {

	public FileLogger(int level) {
		super(level);
	}

	@Override
	protected void write(String message) {
		System.out.println("File logger: " + message);
	}

}
