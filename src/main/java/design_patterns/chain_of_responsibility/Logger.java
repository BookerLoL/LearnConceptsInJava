package design_patterns.chain_of_responsibility;

public abstract class Logger {
	public static final int INFO = 1;
	public static final int DEBUG = 2;
	public static final int ERROR = 3;

	protected Logger nextLogger;
	protected int level;

	public Logger(int level) {
		this.level = level;
	}

	public void setNextLogger(Logger nextLogger) {
		this.nextLogger = nextLogger;
	}

	public void log(int level, String message) {
		if (this.level <= level) {
			write(message);
		}

		if (nextLogger != null) {
			nextLogger.log(level, message);
		}
	}

	abstract protected void write(String message);
}
