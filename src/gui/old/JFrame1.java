package gui.old;
import java.awt.*;
import javax.swing.*;
import poker.machine.*;
import poker.machine.original.*;
public class JFrame1 extends JFrame implements View {
	public JFrame1() throws HeadlessException {
		init();
	}
	public JFrame1(GraphicsConfiguration gc) {
		super(gc);
		init();
	}
	public JFrame1(String title) throws HeadlessException {
		super(title);
		init();
	}
	public JFrame1(String title,GraphicsConfiguration gc) {
		super(title,gc);
		init();
	}
	@Override public void update(State state) {}
	private void init() {}
	private static void createAndShowGUI() {
		JFrame1 frame=new JFrame1("Video Poker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.pack();
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(JFrame1::createAndShowGUI);
	}
	private final JButton bet,deal,draw;
	private final JLabel coins,coinsValue,credits,creditsValue;
	private final JTextField status;
	private final CardCanvas cardCanvas[]=new CardCanvas[5];
	private final JButton holds[]=new JButton[5];
	{
		getContentPane().setLayout(null);
		// getContentPane().setSize(640, 480);
		setSize(getInsets().left+640+getInsets().right,getInsets().top+480+getInsets().bottom);
		getContentPane().setFont(new Font("TimesRoman",Font.PLAIN,36));
		getContentPane().setBackground(new Color(0,0,255));
		bet=new JButton("Bet");
		// bet.setActionCommand("button");
		bet.setBounds(getInsets().left+48,getInsets().top+420,96,43);
		bet.setBackground(new Color(0,255,0));
		bet.setFont(new Font("TimesRoman",Font.PLAIN,32)); // lools like it's
															// not inherited?
		getContentPane().add(bet);
		deal=new JButton("Deal");
		// deal.setActionCommand("button");
		deal.setBounds(getInsets().left+264,getInsets().top+420,96,42);
		deal.setBackground(new Color(0,255,0));
		deal.setFont(new Font("TimesRoman",Font.PLAIN,32)); // lools like it's
															// not inherited?
		getContentPane().add(deal);
		draw=new JButton("Draw");
		// draw.setActionCommand("button");
		draw.setBounds(getInsets().left+480,getInsets().top+420,99,44);
		draw.setBackground(new Color(0,255,0));
		draw.setFont(new Font("TimesRoman",Font.PLAIN,32)); // lools like it's
															// not inherited?
		getContentPane().add(draw);
		coins=new JLabel("Coins");
		coins.setBounds(getInsets().left+180,getInsets().top+420,48,20);
		coins.setFont(new Font("Dialog",Font.PLAIN,18));
		coins.setForeground(new Color(255,255,255));
		getContentPane().add(coins);
		coinsValue=new JLabel("0");
		coinsValue.setBounds(getInsets().left+192,getInsets().top+444,12,16);
		coinsValue.setFont(new Font("Dialog",Font.PLAIN,18));
		coinsValue.setForeground(new Color(255,255,255));
		getContentPane().add(coinsValue);
		credits=new JLabel("Credits");
		credits.setBounds(getInsets().left+396,getInsets().top+420,60,20);
		credits.setFont(new Font("Dialog",Font.PLAIN,18));
		credits.setForeground(new Color(255,255,255));
		getContentPane().add(credits);
		creditsValue=new JLabel("0");
		creditsValue.setBounds(getInsets().left+396,getInsets().top+444,60,16);
		creditsValue.setFont(new Font("Dialog",Font.PLAIN,18));
		creditsValue.setForeground(new Color(255,255,255));
		getContentPane().add(creditsValue);
		for(int i=0;i<5;i++) {
			cardCanvas[i]=new CardCanvas();
			cardCanvas[i].setBounds(getInsets().left+48+108*i,getInsets().top+180,96,162);
			cardCanvas[i].setBackground(new Color(255,255,255));
			getContentPane().add(cardCanvas[i]);
			// repaintThread[i]=new RepaintThread(cardCanvas[i]);
			// repaintThread[i].start();
		}
		status=new JTextField();
		status.setBounds(getInsets().left+12,getInsets().top+12,588,96);
		status.setFont(new Font("Dialog",Font.PLAIN,12));
		getContentPane().add(status);
		for(int i=0;i<5;i++) {
			holds[i]=new JButton("Hold");
			holds[i].setActionCommand("button");
			holds[i].setBounds(getInsets().left+48+108*i,getInsets().top+360,96,48);
			holds[i].setFont(new Font("TimesRoman",Font.PLAIN,30)); // lools
																	// like
																	// it's
																	// not
																	// inherited?
			System.out.println(""+(getInsets().left+48+108*i)+' '+(getInsets().top+360));
			holds[i].setBackground(new Color(128,0,0));
			getContentPane().add(holds[i]);
		}
	}
	static final long serialVersionUID=0;
}
