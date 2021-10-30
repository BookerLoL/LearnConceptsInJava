package designpatterns.memento;

public class TextWindow {
	StringBuilder text;

	public TextWindow() {
		this.text = new StringBuilder();
	}

	public void addText(String text) {
		this.text.append(text);
	}

	public TextWindowState save() {
		return new TextWindowState(text.toString());
	}

	public void restore(TextWindowState saveState) {
		text = new StringBuilder(saveState.getText());
	}

	public void clear() {
		text.setLength(0);
	}

	public String getText() {
		return text.toString();
	}
}
