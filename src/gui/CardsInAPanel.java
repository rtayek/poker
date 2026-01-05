package gui;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
public class CardsInAPanel {
	CardsInAPanel() {
		JFrame f=new JFrame("cards in a panel");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		top.add(new JButton("xxx"));
		addContent();
		//addContent2();
		top.add(new JButton("xxx"));
		f.getContentPane().add(top);
		f.pack();
		f.setVisible(true);
	}
	public static void main(String[] a) {
		new CardsInAPanel();
	}
	void addContent() {
		for(int i=0;i<5;i++)
			cardsAndButtons.add(ccp(i));
		top.add(cardsAndButtons);
	}
	void addContent2() {
		for(int i=0;i<5;i++)
			cardsAndButtons.add(ccp2(i));
		top.add(cardsAndButtons);
	}
	private JPanel ccp(int i) {
		JPanel p=new JPanel();
		Dimension d2=new Dimension(d);
		d2.height=d2.height+30;
		p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		// cp[i].setSize(d);
		p.setMaximumSize(d2);
		// cp[i].setMinimumSize(d);
		p.setPreferredSize(d2);
		cardCanvas[i].setSize(d);
		cardCanvas[i].setPreferredSize(d);
		cardCanvas[i].setMinimumSize(d);
		p.setBorder(b);
		p.add(cardCanvas[i]);
		p.add(holds[i]);
		return p;
	}
	private JPanel ccp2(int i) {
		JPanel p=new JPanel();
		p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		p.setMaximumSize(d);
		// cp[i].setMinimumSize(d);
		p.setPreferredSize(d);
		cardCanvas[i].setSize(d);
		cardCanvas[i].setPreferredSize(d);
		cardCanvas[i].setMinimumSize(d);
		p.setBorder(b);
		p.add(cardCanvas[i]);
		// jPanel.setPreferredSize(dimension);
		// jPanel.setMinimumSize(dimension);
		p.add(holds[i]);
		return p;
	}
	JPanel top=new JPanel();
	{
		top.setLayout(new BoxLayout(top,BoxLayout.Y_AXIS));
	}
	JLabel cardCanvas[]=new JLabel[5];
	{
		for(int i=0;i<5;i++)
			cardCanvas[i]=new JLabel("a card");
	}
	JButton holds[]=new JButton[5];
	{
		for(int i=0;i<5;i++)
			holds[i]=new JButton("Discard");
	}
	Border b=BorderFactory.createLineBorder(Color.black);
	Dimension d=new Dimension(45,65);
	JPanel cardsAndButtons=new JPanel();
	{
		cardsAndButtons.setLayout(new BoxLayout(cardsAndButtons,BoxLayout.X_AXIS));
	}
}