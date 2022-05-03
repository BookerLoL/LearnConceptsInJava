package designpatterns.chainofresponsibility;

public class ErrorLogger extends Logger {

	public ErrorLogger(int level) {
		super(level);
	}

	@Override
	protected void write(String message) {
		System.out.println("Error: " + message);
	}

}
