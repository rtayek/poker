package gui;
import java.awt.BorderLayout;
public class Applet extends MyJApplet {
	@Override public void init() {
		super.init();
		Main.plaf(this);
	}
	@Override public void start() {
		super.start();
	}

	public void addContent() {
		getContentPane().add(new Main(this), BorderLayout.CENTER);
	}
	private static final long serialVersionUID = 1;
}