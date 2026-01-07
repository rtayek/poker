package poker.machine.mvc;
import java.util.*;
import java.io.*;

import equipment.*;
import poker.*;
import poker.machine.*;
public class CommandLineView implements Observer {
	public CommandLineView(PokerMachine pokerMachine,boolean automatic) {
		this.pokerMachine=pokerMachine;
		this.automatic=automatic;
	}
	@Override public void update(Observable o,Object arg) {
		if(o!=pokerMachine)
			return;
		State state=((PokerMachine)o).state();
		this.state=state;
		String s="";
		if(state.pokerHand()==null||state.isBetMade())
			s+="XXXXX";
		else s+=state.pokerHand().toCharacters()+" "+info(state.pokerHand(),state.handNumber());
		s=s+CommandLineController.lineSeparator;
		if(!(state.pokerHand()==null||state.isBetMade())) {
			for(int i=0;i<5;i++)
				s+=" "+(state.isHeld(i)?'H':'T');
			s+=CommandLineController.lineSeparator;
		}
		if(state.isAfterDraw()&&state.payoff()>0)
			s+="payoff "+state.payoff()+CommandLineController.lineSeparator;
		s+=("hand "+state.hands()+", "+state.coins()+" Coins, "+state.credits()+" credits");
		if(/* true|| */!automatic||state.hands()%250==0)
			System.out.println(s);
		this.state=state;
	}
	String info(final Hand hand,final int pokerHandNumber) {
		int highHandNumber=pokerHandNumber;
		if(highHandNumber!=0) {
			PokerHand.HighType type=PokerHand.HighType.type(highHandNumber);
			String s=" is #"+highHandNumber+" "+type;
			return s;
		}
		CommandLineController.waitForEnter("info fails");
		return "can't find this hand";
	}
	private boolean automatic;
	private boolean playMore;
	private State state;
	private final PokerMachine pokerMachine;
	// Controller controller;
}
