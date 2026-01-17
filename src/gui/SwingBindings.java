package gui;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import equipment.Hand;
import poker.*;
import poker.machine.*;
import poker.machine.mvc.*;
// xlarge screens are at least 960dp x 720dp
// large screens are at least 640dp x 480dp
// normal screens are at least 470dp x 320dp
// small screens are at least 426dp x 320dp
// aspect ratio 19:9, 20:9, Screr Standard (16:9)
// Modern Standard (18:9, 19:9, 20:9): 
// Cinematic (21:9): Some phones offer even wider screens for a true movie-watching experience. 
// 4:3: This is the native aspect ratio for most
class SwingBindings extends JPanel implements Observer {
	public SwingBindings() {
		setLayout(new BorderLayout());
		holdButtonActionListener=e-> {
			Object object=e.getSource();
			if(!(object instanceof JButton jButton)) throw new RuntimeException("oops");
			String text=jButton.getText();
			if(state!=null) {
				int i=Integer.parseInt(jButton.getActionCommand());
				controller.processEvent(pokerMachine,(char)('1'+i));
			} else {
				if(text.equals("Hold")) jButton.setText("Discard");
				else if(text.equals("Discard")) jButton.setText("Hold");
				else throw new RuntimeException("oops");
			}
		};
		otherButtonsActionListener=e-> {
			Object object=e.getSource();
			if(!(object instanceof JButton)) throw new RuntimeException("oops");
			switch(e.getActionCommand()) {
				case ACTION_BET -> controller.processEvent(pokerMachine,'b');
				case ACTION_DEAL -> controller.processEvent(pokerMachine,'d');
				case ACTION_DRAW -> controller.processEvent(pokerMachine,'r');
				default -> throw new RuntimeException("oops");
			}
		};
		add(createCardPanels(),BorderLayout.CENTER);
		add(createButtonsAndLabels(),BorderLayout.SOUTH);
		addComponentListener(new ComponentAdapter() {
			@Override public void componentResized(ComponentEvent e) {
				updateLayoutSizes();
			}
		});
		addHierarchyListener(e-> {
			if((e.getChangeFlags()&HierarchyEvent.SHOWING_CHANGED)!=0&&isShowing()) EventQueue.invokeLater(this::logCardRowSize);
		});
	}
	@Override public void addNotify() {
		super.addNotify();
		updateLayoutSizes();
		if(!sizeLogScheduled) {
			sizeLogScheduled=true;
			EventQueue.invokeLater(this::scheduleSizeLog);
		}
	}
	private JPanel createButtonsAndLabels() {
		JPanel controls=new JPanel();
		controls.setLayout(new BoxLayout(controls,BoxLayout.X_AXIS));
		bet=new JButton("Bet");
		bet.setActionCommand(ACTION_BET);
		controls.add(bet);
		bet.addActionListener(otherButtonsActionListener);
		controls.add(createCoins());
		deal=new JButton("Deal");
		deal.setActionCommand(ACTION_DEAL);
		controls.add(deal);
		deal.addActionListener(otherButtonsActionListener);
		controls.add(createCredits());
		draw=new JButton("Draw");
		draw.setActionCommand(ACTION_DRAW);
		controls.add(draw);
		draw.addActionListener(otherButtonsActionListener);
		status=new JLabel("x");
		status.setHorizontalAlignment(SwingConstants.LEFT);
		JPanel bottom=new JPanel(new BorderLayout(12,0));
		bottom.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
		bottom.add(controls,BorderLayout.WEST);
		bottom.add(status,BorderLayout.CENTER);
		buttonRow=bottom;
		return bottom;
	}
	private JPanel createCoins() {
		JPanel jPanel=new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));
		coins=new JLabel("Coins");
		jPanel.add(coins);
		coinsValue=new JLabel("x");
		jPanel.add(coinsValue);
		return jPanel;
	}
	private JPanel createCredits() {
		JPanel jPanel=new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));
		credits=new JLabel("Credits");
		jPanel.add(credits);
		creditsValue=new JLabel("x");
		jPanel.add(creditsValue);
		return jPanel;
	}
	private JPanel createCardPanels() {
		cardRow.setLayout(new GridLayout(1,5,7,0));
		cardRow.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
		for(int i=0;i<5;i++) {
			JPanel cardPanel=createCardPanel(i);
			cardPanels[i]=cardPanel;
			cardRow.add(cardPanel);
		}
		cardRow.addComponentListener(new ComponentAdapter() {
			@Override public void componentResized(ComponentEvent e) {
				EventQueue.invokeLater(SwingBindings.this::logCardRowSize);
				scaledIconSize=null;
				scaledIconCache.clear();
				refreshCardIcons();
			}
		});
		return cardRow;
	}
	private JPanel createCardPanel(int i) {
		JPanel jPanel=new JPanel(new BorderLayout());
		JLabel cardLabel=cardCanvas[i];
		cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cardLabel.setVerticalAlignment(SwingConstants.CENTER);
		// cardCanvas[i].setPreferredSize(dimension);
		// cardCanvas[i].setMinimumSize(dimension);
		jPanel.add(cardLabel,BorderLayout.CENTER);
		JButton holdButton=holds[i];
		holdButton.setActionCommand(Integer.toString(i));
		holdButton.addActionListener(holdButtonActionListener);
		// jPanel.setPreferredSize(dimension);
		// jPanel.setMinimumSize(dimension);
		jPanel.add(holdButton,BorderLayout.SOUTH);
		Border b=BorderFactory.createLineBorder(Color.black);
		jPanel.setBorder(b);
		return jPanel;
	}
	@Override public void update(Observable o,Object arg) {
		update(((PokerMachine)o).state());
	}
	public void update(State state) {
		this.state=state;
		StringBuilder sb=new StringBuilder();
		if(state.pokerHand()==null||state.isBetMade()) {
			for(int i=0;i<cardCanvas.length;i++) {
				JLabel jLabel=cardCanvas[i];
				ImageIcon icon=iconFor("e");
				jLabel.setIcon(icon);
				jLabel.setText("");
			}
		} else {
			sb.append(state.pokerHand().toCharacters()).append(" ").append(info(state.pokerHand(),state.handNumber()));
			for(int i=0;i<5;i++) {
				String cardCode=state.pokerHand().card(i).toCharacters();
				ImageIcon icon=iconFor(cardCode);
				cardCanvas[i].setIcon(icon);
				cardCanvas[i].setText("");
			}
		}
		sb.append(lineSeparator);
		if(!(state.pokerHand()==null||state.isBetMade())) for(int i=0;i<5;i++)
			holds[i].setText(state.isHeld(i)?"Hold":"Discard");
		if(state.isAfterDraw()&&state.payoff()>0) sb.append("payoff ").append(state.payoff()).append(lineSeparator);
		sb.append("hand ").append(state.hands());
		coinsValue.setText(""+state.coins());
		creditsValue.setText(""+state.credits());
		if(state.pokerHand()!=null&&(state.isInAHand()||state.isAfterDraw())) status.setText(state.pokerHand().toCharacters()+" is hand #"+state.handNumber());
		else status.setText("");
		String s=sb.toString();
		if(/* true|| */!automatic||state.hands()%250==0) println(s);
		this.state=state;
		revalidate();
		repaint();
	}
	String info(Hand hand,int handNumber) {
		int highHandNumber=handNumber;
		if(highHandNumber!=0) {
			PokerHand.HighType type=PokerHand.HighType.type(highHandNumber);
			String s=" is #"+highHandNumber+" "+type;
			// int lowHandNumber=pokerHandNumbers.lowHandNumber;
			// if(lowHandNumber!=0)
			// s+=" (low #"+lowHandNumber+"
			// "+(type==TypeOfPokerHand.straight?TypeOfPokerHand.noPair:type)+")";
			// s+="
			// "+PokerCardFactory.instance.toCharacters(PokerCardFactory.instance.keyInverse(pokerHandNumbers.key));
			return s;
		}
		return "can't find this hand";
	}
	void println(String s) {
		System.err.println(s);
	}
	private ImageIcon iconFor(String code) {
		ImageIcon base=iconCache.computeIfAbsent(code,this::loadIcon);
		Dimension targetSize=desiredIconSize(base);
		if(targetSize==null) return base;
		if(scaledIconSize==null||!scaledIconSize.equals(targetSize)) {
			scaledIconSize=targetSize;
			scaledIconCache.clear();
		}
		return scaledIconCache.computeIfAbsent(code,c->scaleIcon(base,targetSize));
	}
	static String name(String name) {
		if(name.equals("e")) return "BACK1";
		if(name.length()!=2) throw new RuntimeException(name+" is not a valid card name");
		Character rank=name.charAt(0);
		switch(rank) {
			case '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A':
				break;
			default:
				throw new RuntimeException(rank+" is not a valid rank");
		}
		//
		Character suit=name.charAt(1);
		switch(suit) {
			case 'c', 'd', 'h', 's':
				suit=(char)Character.toUpperCase(suit);
				break;
			default:
				throw new RuntimeException(suit+" is not a valid suit");
		}
		return "CARD"+rank+suit;
	}
	private ImageIcon loadIcon(String code) {
		String path=null;
		if(true) {
			path=BITMAP_DIR+'/'+name(code)+".BMP";
		} else path=CARD_IMAGE_DIR+'/'+code+".gif";
		System.out.println(path);
		File file=new File(path);
		if(!file.exists()) throw new RuntimeException("file not found: "+path);
		else System.out.println(path+" exists");
		try {
			var image=ImageIO.read(file);
			if(image==null) throw new RuntimeException("ImageIO.read returned null: "+path);
			ImageIcon icon=new ImageIcon(image);
			System.out.println("loaded icon "+path+" img="+image.getWidth()+"x"+image.getHeight()+" icon="+icon.getIconWidth()+"x"+icon.getIconHeight());
			return icon;
		} catch(Exception e) {
			throw new RuntimeException("failed to load image: "+path,e);
		}
	}
	private Dimension desiredIconSize(ImageIcon base) {
		if(cardPanels[0]==null) return null;
		int panelWidth=cardPanels[0].getWidth();
		int panelHeight=cardPanels[0].getHeight();
		if(panelWidth<=0||panelHeight<=0) return null;
		int holdHeight=holds[0].getHeight();
		if(holdHeight<=0) holdHeight=holds[0].getPreferredSize().height;
		int availableHeight=panelHeight-holdHeight;
		int availableWidth=panelWidth;
		if(availableWidth<=0||availableHeight<=0) return null;
		int baseWidth=base.getIconWidth();
		int baseHeight=base.getIconHeight();
		if(baseWidth<=0||baseHeight<=0) return null;
		double targetAspect=CARD_ASPECT;
		double availableAspect=availableWidth/(double)availableHeight;
		int width;
		int height;
		if(availableAspect>=targetAspect) {
			height=availableHeight;
			width=(int)Math.round(height*targetAspect);
		} else {
			width=availableWidth;
			height=(int)Math.round(width/targetAspect);
		}
		width=Math.max(1,(int)Math.round(width*CARD_SCALE));
		height=Math.max(1,(int)Math.round(height*CARD_SCALE));
		return new Dimension(width,height);
	}
	private static ImageIcon scaleIcon(ImageIcon base,Dimension targetSize) {
		Image scaled=base.getImage().getScaledInstance(targetSize.width,targetSize.height,Image.SCALE_SMOOTH);
		return new ImageIcon(scaled);
	}
	private void refreshCardIcons() {
		if(state==null) return;
		if(state.pokerHand()==null||state.isBetMade()) {
			for(JLabel jLabel:cardCanvas) {
				jLabel.setIcon(iconFor("e"));
				jLabel.setText("");
			}
		} else {
			for(int i=0;i<5;i++) {
				String cardCode=state.pokerHand().card(i).toCharacters();
				cardCanvas[i].setIcon(iconFor(cardCode));
				cardCanvas[i].setText("");
			}
		}
	}
	private void logCardRowSize() {
		Dimension size=cardRow.getSize();
		System.out.println("cardRow size: "+size.width+"x"+size.height);
	}
	private void scheduleSizeLog() {
		Timer timer=new Timer(250,e-> {
			Dimension size=cardRow.getSize();
			System.out.println("cardRow size: "+size.width+"x"+size.height);
			sizeLogAttempts++;
			if((size.width>0&&size.height>0)||sizeLogAttempts>=10) ((Timer)e.getSource()).stop();
		});
		timer.setRepeats(true);
		timer.start();
	}
	private void updateLayoutSizes() {
		if(buttonRow==null) return;
		int height=getHeight();
		int width=getWidth();
		if(height<=0||width<=0) return;
		int bottomHeight=(int)Math.round(height*BOTTOM_BAR_RATIO);
		bottomHeight=Math.max(bottomHeight,MIN_BOTTOM_BAR_HEIGHT);
		bottomHeight=Math.min(bottomHeight,Math.max(0,height-MIN_CARD_ROW_HEIGHT));
		buttonRow.setPreferredSize(new Dimension(width,bottomHeight));
		buttonRow.revalidate();
	}
	PokerMachine pokerMachine;
	State state;
	CommandLineController controller;
	boolean automatic=false;
	JButton bet,deal,draw,holds[]=new JButton[5];
	JLabel coins,coinsValue,credits,creditsValue,status;
	// CardCanvas cardCanvas[]=new CardCanvas[5];
	JLabel cardCanvas[]=new JLabel[5];
	{
		Arrays.setAll(cardCanvas,i->new JLabel("xxx"));
		Arrays.setAll(holds,i->new JButton("Discard"));
	}
	private final JPanel cardRow=new JPanel();
	private final JPanel[] cardPanels=new JPanel[5];
	private JPanel buttonRow;
	// RepaintThread repaintThread[]=new RepaintThread[5];
	final ActionListener holdButtonActionListener,otherButtonsActionListener;
	static String lineSeparator=System.getProperty("line.separator");
	private static final String ACTION_BET="bet";
	private static final String ACTION_DEAL="deal";
	private static final String ACTION_DRAW="draw";
	private static final String CARD_IMAGE_DIR="resources/cards";
	private static final String BITMAP_DIR="resources/GRAJUN12";
	private static final double CARD_SCALE=0.95;
	private static final double CARD_ASPECT=5.0/7.0;
	private static final double BOTTOM_BAR_RATIO=0.35;
	private static final int MIN_BOTTOM_BAR_HEIGHT=80;
	private static final int MIN_CARD_ROW_HEIGHT=120;
	private final Map<String,ImageIcon> iconCache=new HashMap<>();
	private final Map<String,ImageIcon> scaledIconCache=new HashMap<>();
	private Dimension scaledIconSize;
	private boolean sizeLogScheduled;
	private int sizeLogAttempts;
	private static final long serialVersionUID=1;
}
