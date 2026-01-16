package gui;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
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
class SwingBindings extends JPanel implements Observer {
	public SwingBindings() {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		holdButtonActionListener=e -> {
			Object object=e.getSource();
			if(!(object instanceof JButton jButton)) throw new RuntimeException("oops");
			String text=jButton.getText();
			if(state!=null) {
				int i=Integer.parseInt(jButton.getActionCommand());
				controller.processEvent(pokerMachine,(char)('1'+i));
			} else {
				if(text.equals("Hold"))
					jButton.setText("Discard");
				else if(text.equals("Discard"))
					jButton.setText("Hold");
				else throw new RuntimeException("oops");
			}
		};
		otherButtonsActionListener=e -> {
			Object object=e.getSource();
			if(!(object instanceof JButton)) throw new RuntimeException("oops");
			switch (e.getActionCommand()) {
				case ACTION_BET -> controller.processEvent(pokerMachine,'b');
				case ACTION_DEAL -> controller.processEvent(pokerMachine,'d');
				case ACTION_DRAW -> controller.processEvent(pokerMachine,'r');
				default -> throw new RuntimeException("oops");
			}
		};
		add(createCardPanels());
		add(createButtonsAndLabels());
	}
	private JPanel createButtonsAndLabels() {
		JPanel jPanel=new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
		bet=new JButton("Bet");
		bet.setActionCommand(ACTION_BET);
		jPanel.add(bet);
		bet.addActionListener(otherButtonsActionListener);
		jPanel.add(createCoins());
		deal=new JButton("Deal");
		deal.setActionCommand(ACTION_DEAL);
		jPanel.add(deal);
		deal.addActionListener(otherButtonsActionListener);
		jPanel.add(createCredits());
		draw=new JButton("Draw");
		draw.setActionCommand(ACTION_DRAW);
		jPanel.add(draw);
		draw.addActionListener(otherButtonsActionListener);
		status=new JLabel("x");
		jPanel.add(status);
		return jPanel;
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
		JPanel jPanel=new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
		for(int i=0;i<5;i++)
			jPanel.add(createCardPanel(i));
		return jPanel;
	}
	private JPanel createCardPanel(int i) {
		JPanel jPanel=new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));
		JLabel cardLabel=cardCanvas[i];
		final Dimension dimension=new Dimension(45,65);
		cardLabel.setSize(dimension);
		// cardCanvas[i].setPreferredSize(dimension);
		// cardCanvas[i].setMinimumSize(dimension);
		jPanel.add(cardLabel);
		JButton holdButton=holds[i];
		holdButton.setActionCommand(Integer.toString(i));
		holdButton.addActionListener(holdButtonActionListener);
		// jPanel.setPreferredSize(dimension);
		// jPanel.setMinimumSize(dimension);
		jPanel.add(holdButton);
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
		if(state.pokerHand()==null||state.isBetMade())
			for(JLabel jLabel:cardCanvas)
				jLabel.setText("ii");
		else {
			sb.append(state.pokerHand().toCharacters()).append(" ").append(info(state.pokerHand(),state.handNumber()));
			for(int i=0;i<5;i++)
				cardCanvas[i].setText(state.pokerHand().card(i).toCharacters());
		}
		sb.append(lineSeparator);
		if(!(state.pokerHand()==null||state.isBetMade()))
			for(int i=0;i<5;i++)
				holds[i].setText(state.isHeld(i)?"Hold":"Discard");
		if(state.isAfterDraw()&&state.payoff()>0)
			sb.append("payoff ").append(state.payoff()).append(lineSeparator);
		sb.append("hand ").append(state.hands());
		coinsValue.setText(""+state.coins());
		creditsValue.setText(""+state.credits());
		if(state.pokerHand()!=null&&(state.isInAHand()||state.isAfterDraw()))
			status.setText(state.pokerHand().toCharacters()+" is hand #"+state.handNumber());
		else status.setText("");
		String s=sb.toString();
		if(/* true|| */!automatic||state.hands()%250==0)
			println(s);
		this.state=state;
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
	void println(String s) {
		System.err.println(s);
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
		Arrays.setAll(cardCanvas,i -> new JLabel("xxx"));
		Arrays.setAll(holds,i -> new JButton("Discard"));
	}
	// RepaintThread repaintThread[]=new RepaintThread[5];
	final ActionListener holdButtonActionListener,otherButtonsActionListener;
	static String lineSeparator=System.getProperty("line.separator");
	private static final String ACTION_BET="bet";
	private static final String ACTION_DEAL="deal";
	private static final String ACTION_DRAW="draw";
	private static final long serialVersionUID=1;
}
