package design_patterns.chain_of_responsibility;

public class ConsoleLogger extends Logger {

	public ConsoleLogger(int level) {
		super(level);
	}

	@Override
	protected void write(String message) {
		System.out.println("Console: " + message);
	}

}
