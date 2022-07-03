package designpatterns.memento;

import java.util.ArrayList;
import java.util.List;

public class TextEditor {
	private TextWindow textWindow;
	private List<TextWindowState> savedState;

	public TextEditor(TextWindow textWindow) {
		this.textWindow = textWindow;
		this.savedState = new ArrayList<>();
	}

	public void write(String text) {
		if (text != null) {
			textWindow.addText(text);
		}
	}

	public boolean save() {
		savedState.add(textWindow.save());
		return true;
	}

	public boolean restore() {
		if (!savedState.isEmpty()) {
			textWindow.restore(savedState.remove(savedState.size() - 1));
			return true;
		}

		return false;
	}

	public void clear() {
		textWindow.clear();
	}

	public String print() {
		return textWindow.getText();
	}
}
