package gui;
import java.awt.Dimension;
import javax.swing.*;
public class MainGui extends JPanel {
	public MainGui() {
		this(null);
	}
	public MainGui(MyJApplet applet) {
		this.applet=applet;
		if(!isApplet()) {
			frame=new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else frame=null;
		// setLayout(new BorderLayout());
		setPreferredSize(new Dimension(640,480));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainGui.this.run();
			}
		});
	}
	String title() {
		return "Title";
	}
	public void addContent() {
		add(new JLabel("add content! top"));
	}
	void run() {
		if(isApplet())
			addContent();
		else {
			frame.setTitle(title());
			// frame.getContentPane().add(this, BorderLayout.CENTER);
			frame.getContentPane().add(this);
			addContent();
			frame.pack();
			System.out.println(getSize());
			frame.setVisible(true);
		}
	}
	boolean isApplet() {
		return applet!=null;
	}
	public static void main(String[] args) {
		new MainGui(null);
	}
	protected final JFrame frame;
	protected final MyJApplet applet;
	private static final long serialVersionUID=1;
}
