package design_patterns.mediator;

public class BooleanValue implements Value<Boolean> {
	Boolean value;

	public BooleanValue(Boolean value) {
		this.value = value;
	}

	@Override
	public Boolean get() {
		return value;
	}

	@Override
	public void set(Boolean value) {
		this.value = value;
	}

}
