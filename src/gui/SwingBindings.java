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
	int index(JButton jButton) {
		int index=-1;
		for(int i=0;i<5;i++)
			if(holds[i]==jButton)
				return i;
		return index;
	}
	public SwingBindings() {
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		holdButtonActionListener=new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				Object object=e.getSource();
				if(object instanceof JButton) {
					JButton jButton=(JButton)object;
					String text=jButton.getText();
					if(state!=null) {
						int i=index(jButton);
						controller.processEvent(pokerMachine,(char)('1'+i));
					} else {
						if(text.equals("Hold"))
							jButton.setText("Discard");
						else if(text.equals("Discard"))
							jButton.setText("Hold");
						else throw new RuntimeException("oops");
					}
				} else throw new RuntimeException("oops");
			}
		};
		otherButtonsActionListener=new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				Object object=e.getSource();
				if(object instanceof JButton) {
					JButton jButton=(JButton)object;
					String text=jButton.getText();
					if(text.equals("Bet"))
						controller.processEvent(pokerMachine,'b');
					else if(text.equals("Deal"))
						controller.processEvent(pokerMachine,'d');
					else if(text.equals("Draw"))
						controller.processEvent(pokerMachine,'r');
					else throw new RuntimeException("oops");
				}
			}
		};
		add(createCardPanels());
		add(createButtonsAndLabels());
	}
	private JPanel createButtonsAndLabels() {
		JPanel jPanel=new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.X_AXIS));
		bet=new JButton("Bet");
		jPanel.add(bet);
		bet.addActionListener(otherButtonsActionListener);
		jPanel.add(createCoins());
		deal=new JButton("Deal");
		jPanel.add(deal);
		deal.addActionListener(otherButtonsActionListener);
		jPanel.add(createCredits());
		draw=new JButton("Draw");
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
		cardCanvas[i]=new JLabel("xxx");
		final Dimension dimension=new Dimension(45,65);
		cardCanvas[i].setSize(dimension);
		// cardCanvas[i].setPreferredSize(dimension);
		// cardCanvas[i].setMinimumSize(dimension);
		jPanel.add(cardCanvas[i]);
		holds[i]=new JButton("Discard");
		holds[i].addActionListener(holdButtonActionListener);
		// jPanel.setPreferredSize(dimension);
		// jPanel.setMinimumSize(dimension);
		jPanel.add(holds[i]);
		Border b=BorderFactory.createLineBorder(Color.black);
		jPanel.setBorder(b);
		return jPanel;
	}
	@Override public void update(Observable o,Object arg) {
		update(((PokerMachine)o).state());
	}
	public void update(State state) {
		this.state=state;
		String s="";
		if(state.pokerHand()==null||state.isBetMade())
			for(JLabel jLabel:cardCanvas)
				jLabel.setText("ii");
		else {
			s+=state.pokerHand().toCharacters()+" "+info(state.pokerHand(),state.handNumber());
			for(int i=0;i<5;i++)
				cardCanvas[i].setText(state.pokerHand().card(i).toCharacters());
		}
		s=s+lineSeparator;
		if(!(state.pokerHand()==null||state.isBetMade()))
			for(int i=0;i<5;i++)
				holds[i].setText(state.isHeld(i)?"Hold":"Discard");
		if(state.isAfterDraw()&&state.payoff()>0)
			s+="payoff "+state.payoff()+lineSeparator;
		s+="hand "+state.hands();
		coinsValue.setText(""+state.coins());
		creditsValue.setText(""+state.credits());
		if(state.pokerHand()!=null&&(state.isInAHand()||state.isAfterDraw()))
			status.setText(state.pokerHand().toCharacters()+" is hand #"+state.handNumber());
		else status.setText("");
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
	// RepaintThread repaintThread[]=new RepaintThread[5];
	final ActionListener holdButtonActionListener,otherButtonsActionListener;
	static String lineSeparator=System.getProperty("line.separator");
	private static final long serialVersionUID=1;
}
