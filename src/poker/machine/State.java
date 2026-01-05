package poker.machine;
import lookup.OldLookup;
import poker.PokerHand;
import poker.machine.mvc.PokerMachine;
import equipment.Card;
import equipment.Hand;
public class State {
	public State(PokerMachine pokerMachine) {
		this.pokerMachine=pokerMachine;
	}
	PokerMachine pokerMachine;
	public final boolean holds[]=new boolean[5];
	public/* static */final int afterDraw=0,betMade=1,inAHand=2;
	public int subState() {
		return subState;
	}
	public int hands() {
		return hands;
	}
	public int coins() {
		return coins;
	}
	public int credits() {
		return credits;
	}
	public int payoff() {
		return payoff;
	}
	public int handNumber() {
		return handNumber;
	}
	public Hand pokerHand() {
		return hand;
	}
	public String toString() {
		switch(subState) {
			case afterDraw:
				return "afterDraw"; // same as before a hand
			case betMade:
				return "betMade";
			case inAHand:
				return "inAHand";
			default:
				throw new RuntimeException(subState+" is an illegal value for subState");
		}
	}
	private final void changeSubState(int subState) {
		this.subState=subState;
	}
	int handNumber;
	public Hand hand;
	public PokerHand.HighType typeOfPokerHand;
	public int subState=afterDraw,hands=0,coins=0,credits=2000,payoff;
	public abstract class SubState {
		public void bet() {}
		public void deal() {}
		public void draw() {}
		public void toggleHold(int n) {}
		public void quit() {}
		protected/* was private */void deal_() {
			for(int i=0;i<5;i++)
				holds[i]=false;
			pokerMachine.deck.shuffle();
			Card[] cards=pokerMachine.deck.draw(5);
			hand=new PokerHand(cards);
			// pokerHandNumbers=Lookup.instance.lookup(pokerHand);
			handNumber=OldLookup.lookup(cards);
			typeOfPokerHand=PokerHand.HighType.type(handNumber);
			changeSubState(inAHand);
			pokerMachine.analyze();
		}
	}
	private final class AfterDraw extends SubState {
		public void bet() {
			if(credits>0) {
				for(int i=0;i<5;i++)
					holds[i]=false;
				credits--;
				coins++;
				changeSubState(betMade);
			}
		}
	}
	private final class BetMade extends SubState {
		public void bet() {
			if(credits>0) {
				credits--;
				if(++coins==pokerMachine.maxBets)
					deal_();
			}
		}
		public void deal() {
			deal_();
		}
	}
	private final class InAHand extends SubState {
		public void toggleHold(int n) {
			holds[n]=!holds[n];
		}
		public void draw() {
			for(int i=0;i<5;i++)
				if(!holds[i])
					hand.replace(i,pokerMachine.deck.draw());
			handNumber=OldLookup.lookup(hand);
			typeOfPokerHand=PokerHand.HighType.type(handNumber);
			payoff=pokerMachine.payMaster.payoff(typeOfPokerHand,handNumber);
			credits+=payoff*coins;
			hands++;
			coins=0;
			changeSubState(afterDraw);
		}
	}
	public final State.SubState[] subStates={new AfterDraw(),new BetMade(),new InAHand()};
}