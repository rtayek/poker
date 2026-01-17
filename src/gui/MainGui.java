package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
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
		setLayout(new BorderLayout());
		if(isApplet())
			setPreferredSize(new Dimension(640,480));
		SwingUtilities.invokeLater(this::run);
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
			frame.getContentPane().setLayout(new BorderLayout());
			frame.getContentPane().add(this,BorderLayout.CENTER);
			addContent();
			Rectangle bounds=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
			int width=Math.max(1,bounds.width/2);
			int height=Math.max(1,bounds.height/2);
			int x=bounds.x+(bounds.width-width)/2;
			int y=bounds.y+(bounds.height-height)/2;
			frame.setBounds(x,y,width,height);
			frame.setVisible(true);
			frame.validate();
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
