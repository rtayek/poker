package gui.old;
import poker.*;
import poker.machine.*;
import poker.machine.original.*;

import java.awt.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import equipment.*;
class RepaintThread extends Thread {
	RepaintThread(Canvas canvas) {
		this.canvas=canvas;
	}
	public void run() {
		while(true) {
			synchronized(lock) {
				while(!repaintRequested) {
					try {
						lock.wait();
					} catch(InterruptedException e) {
						return;
					}
				}
				repaintRequested=false;
			}
			canvas.repaint();
			Toolkit.getDefaultToolkit().sync();
		}
	}
	void requestRepaint() {
		synchronized(lock) {
			repaintRequested=true;
			lock.notifyAll();
		}
	}
	private final Canvas canvas;
	private final Object lock=new Object();
	private boolean repaintRequested=false;
}
public class Frame1 extends Frame implements View {
	public void repaint() {
		System.err.println("repaint "+this+" "+Thread.currentThread());
		super.repaint();
	}
	static void println(String s) {
		//try { status.setText(s); }
		//catch(Exception e) { }
		System.out.println(s);
	}
	/*
	 * public void update(State state) { this.state=state; String s=""; if(state.pokerHand()==null||state.isBetMade()) s+="XXXXX"; else s+=state.pokerHand().toCharacters()+" "+info(state.pokerHand(),state.handNumber()); s=s+lineSeparator; if(!(state.pokerHand()==null||state.isBetMade())) { for(int i=0;i<5;i++) s+=" "+(state.holds[i]?'H':'T'); s=s+lineSeparator; } if(state.isAfterDraw()&&state.payoff()>0) s+="payoff "+state.payoff()+lineSeparator; s+=("hand "+state.hands()+", "+state.coins()+ " Coins, "+state.credits()+" credits"); if(!automatic||state.hands()%250==0) println(s); this.state=state; }
	 */public void update(final State state) {
		String s="";
		if(state.isAfterDraw()) {
			if(state.pokerHand()!=null) {
				s+=state.pokerHand().toCharacters();
				s+=" "+info(state.pokerHand(),state.handNumber())+" ";
				Card[] cards=new Card[5]; // state.pokerHand().cards();
				for(int i=0;i<5;i++)
					cards[i]=state.pokerHand().card(i);
				for(int i=0;i<5;i++)
					if(cards[i]!=cardCanvas[i].getCard()) {
						cardCanvas[i].setCard(cards[i]);
						repaintThread[i].requestRepaint();
					}
			}
			if(state.payoff()>0) s+=" payoff "+state.payoff();
		} else if(state.isBetMade()) {
			if(state.pokerHand()==null||state.coins()==1) // first hand or first bet?
				for(int i=0;i<5;i++) {
					System.err.println("foo");
					cardCanvas[i].setCard(null);
					repaintThread[i].requestRepaint();
				}
		} else if(state.isInAHand()) {
			s+=state.pokerHand().toCharacters()+" "+info(state.pokerHand(),state.handNumber())+" ";
			Card[] cards=new Card[5]; // state.pokerHand().cards();
			for(int i=0;i<5;i++)
				cards[i]=state.pokerHand().card(i);
			for(int i=0;i<5;i++)
				if(cards[i]!=cardCanvas[i].getCard()) {
					cardCanvas[i].setCard(cards[i]);
					repaintThread[i].requestRepaint();
				}
		} else {
			System.err.println("illegal state in upsate()");
			System.exit(1);
		}
		for(int i=0;i<5;i++)
			holds[i].setBackground(state.isHeld(i)?new Color(255,0,0):new Color(128,0,0));
		creditsValue.setText(state.credits()+"");
		coinsValue.setText(state.coins()+"");
		s+=(" hand "+state.hands());
		s+=Thread.currentThread();
		println(s);
	}
	String info(Hand hand,int handNumber) {
		int highHandNumber=handNumber;
		if(highHandNumber!=0) {
			PokerHand.HighType type=PokerHand.HighType.type(highHandNumber);
			String s=" is #"+highHandNumber+" "+type;
			// int lowHandNumber=pokerHandNumbers.lowHandNumber;
			// if(lowHandNumber!=0)
			// s+=" (low #"+lowHandNumber+" "+(type==TypeOfPokerHand.straight?TypeOfPokerHand.noPair:type)+")";
			// s+=" "+PokerCardFactory.instance.toCharacters(PokerCardFactory.instance.keyInverse(pokerHandNumbers.key));
			return s;
		}
		return "can't find this hand";
	}
	private boolean automatic=false;
	private static final Deck pokerDeck=new Deck();
	private final PokerMachine pokerMachine=new PokerMachine(pokerDeck,new HighPayoff(),this);
	private final Controller controller=new Controller();
	private static String lineSeparator=System.getProperty("line.separator");
	public Frame1() {
		// {{INIT_CONTROLS
		setLayout(null);
		setVisible(false);
		setSize(insets().left+insets().right+640,insets().top+insets().bottom+480);
		setFont(new Font("TimesRoman",Font.PLAIN,36));
		setBackground(new Color(0,0,255));
		openFileDialog1=new java.awt.FileDialog(this);
		openFileDialog1.setMode(FileDialog.LOAD);
		openFileDialog1.setTitle("Open");
		// $$ openFileDialog1.move(40,277);
		bet=new java.awt.Button();
		bet.setActionCommand("button");
		bet.setLabel("Bet");
		bet.setBounds(insets().left+48,insets().top+420,96,43);
		bet.setBackground(new Color(0,255,0));
		add(bet);
		deal=new java.awt.Button();
		deal.setActionCommand("button");
		deal.setLabel("Deal");
		deal.setBounds(insets().left+264,insets().top+420,96,42);
		deal.setBackground(new Color(0,255,0));
		add(deal);
		draw=new java.awt.Button();
		draw.setActionCommand("button");
		draw.setLabel("Draw");
		draw.setBounds(insets().left+480,insets().top+420,99,44);
		draw.setBackground(new Color(0,255,0));
		add(draw);
		coins=new java.awt.Label("Coins");
		coins.setBounds(insets().left+180,insets().top+420,48,20);
		coins.setFont(new Font("Dialog",Font.PLAIN,18));
		coins.setForeground(new Color(255,255,255));
		add(coins);
		coinsValue=new java.awt.Label("0");
		coinsValue.setBounds(insets().left+192,insets().top+444,12,16);
		coinsValue.setFont(new Font("Dialog",Font.PLAIN,18));
		coinsValue.setForeground(new Color(255,255,255));
		add(coinsValue);
		credits=new java.awt.Label("Credits");
		credits.setBounds(insets().left+396,insets().top+420,60,20);
		credits.setFont(new Font("Dialog",Font.PLAIN,18));
		credits.setForeground(new Color(255,255,255));
		add(credits);
		creditsValue=new java.awt.Label("$0");
		creditsValue.setBounds(insets().left+396,insets().top+444,60,16);
		creditsValue.setFont(new Font("Dialog",Font.PLAIN,18));
		creditsValue.setForeground(new Color(255,255,255));
		add(creditsValue);
		for(int i=0;i<5;i++) {
			cardCanvas[i]=new CardCanvas();
			cardCanvas[i].setBounds(insets().left+48+108*i,insets().top+180,96,162);
			cardCanvas[i].setBackground(new Color(255,255,255));
			add(cardCanvas[i]);
			repaintThread[i]=new RepaintThread(cardCanvas[i]);
			repaintThread[i].start();
		}
		status=new java.awt.TextField();
		status.setBounds(insets().left+12,insets().top+12,588,96);
		status.setFont(new Font("Dialog",Font.PLAIN,12));
		add(status);
		for(int i=0;i<5;i++) {
			holds[i]=new java.awt.Button();
			holds[i].setActionCommand("button");
			holds[i].setLabel("Hold");
			holds[i].setBounds(insets().left+48+108*i,insets().top+360,96,48);
			holds[i].setBackground(new Color(128,0,0));
			add(holds[i]);
		}
		setTitle("Video Poker Frame");
		// }}
		// {{INIT_MENUS
		mainMenuBar=new java.awt.MenuBar();
		menu1=new java.awt.Menu("File");
		miNew=new java.awt.MenuItem("New");
		menu1.add(miNew);
		miOpen=new java.awt.MenuItem("Open...");
		menu1.add(miOpen);
		miSave=new java.awt.MenuItem("Save");
		menu1.add(miSave);
		miSaveAs=new java.awt.MenuItem("Save As...");
		menu1.add(miSaveAs);
		menu1.addSeparator();
		miExit=new java.awt.MenuItem("Exit");
		menu1.add(miExit);
		mainMenuBar.add(menu1);
		menu2=new java.awt.Menu("Edit");
		miCut=new java.awt.MenuItem("Cut");
		menu2.add(miCut);
		miCopy=new java.awt.MenuItem("Copy");
		menu2.add(miCopy);
		miPaste=new java.awt.MenuItem("Paste");
		menu2.add(miPaste);
		mainMenuBar.add(menu2);
		menu3=new java.awt.Menu("Help");
		mainMenuBar.setHelpMenu(menu3);
		miAbout=new java.awt.MenuItem("About..");
		menu3.add(miAbout);
		mainMenuBar.add(menu3);
		setMenuBar(mainMenuBar);
		// $$ mainMenuBar.move(4,277);
		// }}
		// {{REGISTER_LISTENERS
		SymWindow aSymWindow=new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction=new SymAction();
		miOpen.addActionListener(lSymAction);
		miAbout.addActionListener(lSymAction);
		miExit.addActionListener(lSymAction);
		SymMouse aSymMouse=new SymMouse();
		bet.addMouseListener(aSymMouse);
		deal.addMouseListener(aSymMouse);
		draw.addMouseListener(aSymMouse);
		for(int i=0;i<5;i++)
			cardCanvas[i].addMouseListener(aSymMouse);
		for(int i=0;i<5;i++)
			holds[i].addMouseListener(aSymMouse);
		// }}
		{
			CardImage.loadImages(pokerDeck,cardCanvas[0]);
		}
	}
	public Frame1(String title) {
		this();
		setTitle(title);
	}
	public synchronized void show() {
		move(50,50);
		super.show();
	}
	static public void main(String args[]) {
		(new Frame1()).show();
	}
	public void addNotify() {
		// Record the size of the window prior to calling parents addNotify.
		Dimension d=getSize();
		super.addNotify();
		if(fComponentsAdjusted) return;
		// Adjust components according to the insets
		setSize(insets().left+insets().right+d.width,insets().top+insets().bottom+d.height);
		Component components[]=getComponents();
		for(int i=0;i<components.length;i++) {
			Point p=components[i].getLocation();
			p.translate(insets().left,insets().top);
			components[i].setLocation(p);
		}
		fComponentsAdjusted=true;
	}
	// Used for addNotify check.
	boolean fComponentsAdjusted=false;
	// {{DECLARE_CONTROLS
	java.awt.FileDialog openFileDialog1;
	java.awt.Button bet;
	java.awt.Button deal;
	java.awt.Button draw;
	java.awt.Label coins;
	java.awt.Label coinsValue;
	java.awt.Label credits;
	java.awt.Label creditsValue;
	CardCanvas cardCanvas[]=new CardCanvas[5];
	RepaintThread repaintThread[]=new RepaintThread[5];
	java.awt.TextField status;
	java.awt.Button holds[]=new Button[5];
	// }}
	// {{DECLARE_MENUS
	java.awt.MenuBar mainMenuBar;
	java.awt.Menu menu1;
	java.awt.MenuItem miNew;
	java.awt.MenuItem miOpen;
	java.awt.MenuItem miSave;
	java.awt.MenuItem miSaveAs;
	java.awt.MenuItem miExit;
	java.awt.Menu menu2;
	java.awt.MenuItem miCut;
	java.awt.MenuItem miCopy;
	java.awt.MenuItem miPaste;
	java.awt.Menu menu3;
	java.awt.MenuItem miAbout;
	// }}
	class SymWindow extends java.awt.event.WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent event) {
			Object object=event.getSource();
			if(object==Frame1.this) Frame1_WindowClosing(event);
		}
	}
	void Frame1_WindowClosing(java.awt.event.WindowEvent event) {
		hide(); // hide the Frame
		dispose(); // free the system resources
		System.exit(0); // close the application
	}
	class SymAction implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent event) {
			Object object=event.getSource();
			if(object==miOpen) miOpen_Action(event);
			else if(object==miAbout) miAbout_Action(event);
			else if(object==miExit) miExit_Action(event);
		}
	}
	void miAbout_Action(java.awt.event.ActionEvent event) {
		// {{CONNECTION
		// Action from About Create and show as modal
		// (new AboutDialog(this, true)).show();
		System.out.println("from about: "+Thread.currentThread());
		for(int i=0;i<5;i++) {
			println("call repaint "+i);
			repaintThread[i].requestRepaint();
		}
		// }}
	}
	void miExit_Action(java.awt.event.ActionEvent event) {
		// {{CONNECTION
		// Action from Exit Create and show as modal
		(new QuitDialog(this,true)).show();
		// }}
	}
	void miOpen_Action(java.awt.event.ActionEvent event) {
		// {{CONNECTION
		// Action from Open... Show the OpenFileDialog
		openFileDialog1.show();
		// }}
	}
	public void playSound(String path) {
		File file=new File(path);
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	class SymMouse extends java.awt.event.MouseAdapter {
		public void mouseClicked(java.awt.event.MouseEvent event) {
			Object object=event.getSource();
			char c='\0';
			if(object==bet) c='b';
			else if(object==deal) c='d';
			else if(object==draw) c='r';
			for(int i=0;i<5;i++)
				if(object==cardCanvas[i]||object==holds[i]) {
					c=(char)(i+'1');
					break;
				}
			if(c!=0) {
				System.err.println("got a "+c);
				playSound("resources/0.au");
				controller.processEvent(pokerMachine,c);
				//play("d:/usr/ray/java/poker/audio/0.au");
			} else playSound("resources/beep.au");

		}
	}
	static final long serialVersionUID=0;
}
