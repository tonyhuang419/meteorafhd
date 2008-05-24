package test;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.TextBox;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class HelloWorldMIDlet extends MIDlet {
	private TextBox textbox;
	public HelloWorldMIDlet() {
		super();
		textbox = new TextBox("≤‚ ‘≥Ã–Ú", "Hello World!", 20, 0);
	}
	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(textbox);
	}
	protected void pauseApp() {
	}
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}
}
