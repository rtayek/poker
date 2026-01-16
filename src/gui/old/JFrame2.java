package gui.old;
import java.awt.*;
import javax.swing.*;
public class JFrame2 extends JFrame {
	public JFrame2() throws HeadlessException {
		init();
	}
	public JFrame2(GraphicsConfiguration gc) {
		super(gc);
		init();
	}
	public JFrame2(String title) throws HeadlessException {
		super(title);
		init();
	}
	public JFrame2(String title,GraphicsConfiguration gc) {
		super(title,gc);
		init();
	}
	private void init() {}
	private static void createAndShowGUI() {
		JFrame2 frame=new JFrame2("Video Poker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.pack();
		frame.setVisible(true);
		hack=frame;
	}
	public static void main(String[] args) throws Exception {
		javax.swing.SwingUtilities.invokeLater(JFrame2::createAndShowGUI);
		Thread.sleep(2000);
		System.out.println("in main:");
		for(JButton h:hack.holds)
			System.out.println(h.getBounds());
	}
	private static JFrame2 hack;
	private final JButton bet,deal,draw;
	private final JLabel coins,coinsValue,credits,creditsValue;
	private final JTextField status;
	private final CardCanvas cardCanvas[]=new CardCanvas[5];
	private final JButton holds[]=new JButton[5];
	private RepaintThread repaintThread[]=new RepaintThread[5];
	{
		setLayout(null);
		Insets insets=getInsets();
		System.out.println(insets);
		// insets.left, insets.top), and has a size of width - (insets.left + insets.right) by height - (insets.top + insets.bottom).
		setSize(640+insets.left+insets.right,480+insets.top+insets.bottom);
		bet=new JButton("Bet");
		// bet.setActionCommand("button");
		bet.setBounds(48,420,96,43);
		bet.setBackground(new Color(0,255,0));
		getContentPane().add(bet);
		deal=new JButton("Deal");
		// deal.setActionCommand("button");
		deal.setBounds(264,420,96,42);
		deal.setBackground(new Color(0,255,0));
		getContentPane().add(deal);
		draw=new JButton("Draw");
		// draw.setActionCommand("button");
		draw.setBounds(480,420,99,44);
		draw.setBackground(new Color(0,255,0));
		getContentPane().add(draw);
		coins=new JLabel("Coins");
		coins.setBounds(180,420,48,20);
		coins.setFont(new Font("Dialog",Font.PLAIN,18));
		coins.setForeground(new Color(255,255,255));
		getContentPane().add(coins);
		coinsValue=new JLabel("0");
		coinsValue.setBounds(192,444,12,16);
		coinsValue.setFont(new Font("Dialog",Font.PLAIN,18));
		coinsValue.setForeground(new Color(255,255,255));
		getContentPane().add(coinsValue);
		credits=new JLabel("Credits");
		credits.setBounds(396,420,60,20);
		credits.setFont(new Font("Dialog",Font.PLAIN,18));
		credits.setForeground(new Color(255,255,255));
		getContentPane().add(credits);
		creditsValue=new JLabel("0");
		creditsValue.setBounds(396,444,60,16);
		creditsValue.setFont(new Font("Dialog",Font.PLAIN,18));
		creditsValue.setForeground(new Color(255,255,255));
		getContentPane().add(creditsValue);
		for(int i=0;i<5;i++) {
			cardCanvas[i]=new CardCanvas();
			cardCanvas[i].setBounds(48+108*i,180,96,162);
			cardCanvas[i].setBackground(new Color(255,255,255));
			// getContentPane().add(cardCanvas[i]);
			// repaintThread[i]=new RepaintThread(cardCanvas[i]);
			// repaintThread[i].start();
		}
		status=new JTextField();
		status.setBounds(12,12,588,96);
		status.setFont(new Font("Dialog",Font.PLAIN,12));
		getContentPane().add(status);
		System.out.println("in ctor:");
		for(int i=0;i<5;i++) {
			holds[i]=new JButton("Hold");
			holds[i].setActionCommand("button");
			holds[i].setBounds(48+108*i,360,96,48);
			holds[i].setBackground(new Color(128,0,0));
			getContentPane().add(holds[i]);
			System.out.println(holds[i].getBounds());
		}
	}
	static final long serialVersionUID=0;
}
