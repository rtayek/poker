package gui.old;

import javax.swing.*;

public class JFrame3 extends JFrame {
	private static void createAndShowGUI() throws Exception {
		JFrame3 frame = new JFrame3();
		frame.setTitle("Video Poker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(640, 480);
		JButton b = new JButton("Foo");
		ImageIcon icon=new ImageIcon("resources/cards/Kd.gif","Kd");
		int status=icon.getImageLoadStatus();
		System.out.println(status);
		b.setSize(100, 100);
		b.setBounds(100, 100, 100, 100);
		frame.getContentPane().add(b);
		JButton b2 = new JButton(icon);
		Thread.sleep(5000);
		b2.setSize(100,100);
		frame.getContentPane().add(b2);
		frame.setBounds(0, 0, 640, 480);
		// frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
				createAndShowGUI(); }
				catch(Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	static final long serialVersionUID = 0;
}
