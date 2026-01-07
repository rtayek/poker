package poker.machine;
import lookup.OldLookup;
import poker.PokerHand;
import equipment.Card;
import equipment.Deck;
import equipment.Hand;
import equipment.Rank;
import java.util.EnumMap;
public class State {
	public enum Phase {
		afterDraw,betMade,inAHand
	}
	public interface Context {
		Deck deck();
		PayMaster payMaster();
		int maxBets();
		void analyze();
	}
	public interface HoldController {
		boolean hold(int i);
		void toggleHold(int i);
	}
	public static final class HandAnalyzer {
		public static void analyze(State state,HoldController controller) {
			Rank[] ranks=state.hand.ranks();
			PokerHand.HighType type=state.typeOfPokerHand;
			if(type==PokerHand.HighType.onePair) {
				for(int i=0;i<4;i++)
					for(int j=i+1;j<5;j++)
						if(ranks[i]==ranks[j]) {
							if(!controller.hold(i))
								controller.toggleHold(i);
							if(!controller.hold(j))
								controller.toggleHold(j);
							return;
						}
				System.err.println("analyze fails");
			} else if(type==PokerHand.HighType.twoPair||type==PokerHand.HighType.fourOfAKind) {
				holdAll(controller);
				loop:for(int i=0;i<5;i++) {
					for(int j=0;j<5;j++)
						if(i!=j) {
							if(ranks[i]==ranks[j])
								continue loop;
						}
					if(controller.hold(i))
						controller.toggleHold(i);
					return;
				}
				System.err.println("analyze fails");
			} else if(type==PokerHand.HighType.threeOfAKind) {
				for(int i=0;i<3;i++)
					for(int j=i+1;j<4;j++)
						for(int k=j+1;k<5;k++)
							if(ranks[i]==ranks[j]&&ranks[i]==ranks[k]) {
								if(!controller.hold(i))
									controller.toggleHold(i);
								if(!controller.hold(j))
									controller.toggleHold(j);
								if(!controller.hold(k))
									controller.toggleHold(k);
								return;
							}
				System.err.println("analyze fails");
			} else if(type==PokerHand.HighType.straight) {
				holdAll(controller);
			} else if(type==PokerHand.HighType.flush) {
				holdAll(controller);
			} else if(type==PokerHand.HighType.fullHouse) {
				holdAll(controller);
			} else if(type==PokerHand.HighType.straightFlush) {
				holdAll(controller);
			} else if(type==PokerHand.HighType.fiveOfAKind) {
				holdAll(controller);
			} else if(type==PokerHand.HighType.noPair) {
				throwAll(controller);
			} else {
				System.err.println("analyze fails");
				System.exit(1);
			}
		}
		private static void throwAll(HoldController controller) {
			for(int i=0;i<5;i++)
				if(controller.hold(i))
					controller.toggleHold(i);
		}
		private static void holdAll(HoldController controller) {
			for(int i=0;i<5;i++)
				if(!controller.hold(i))
					controller.toggleHold(i);
		}
		private HandAnalyzer() {}
	}
	public State(Context pokerMachine) {
		this.pokerMachine=pokerMachine;
	}
	Context pokerMachine;
	private final boolean holds[]=new boolean[5];
	public boolean isHeld(int i) {
		return holds[i];
	}
	public Phase subState() {
		return subState;
	}
	public boolean isAfterDraw() {
		return subState==Phase.afterDraw;
	}
	public boolean isBetMade() {
		return subState==Phase.betMade;
	}
	public boolean isInAHand() {
		return subState==Phase.inAHand;
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
		return switch (subState) {
			case afterDraw -> "afterDraw"; // same as before a hand
			case betMade -> "betMade";
			case inAHand -> "inAHand";
		};
	}
	private final void changeSubState(Phase subState) {
		this.subState=subState;
	}
	private int handNumber;
	private Hand hand;
	private PokerHand.HighType typeOfPokerHand;
	private Phase subState=Phase.afterDraw;
	private int hands=0,coins=0,credits=2000,payoff;
	public abstract class SubState {
		public void bet() {}
		public void deal() {}
		public void draw() {}
		public void toggleHold(int n) {}
		public void quit() {}
		protected/* was private */void deal_() {
			for(int i=0;i<5;i++)
				holds[i]=false;
			pokerMachine.deck().shuffle();
			Card[] cards=pokerMachine.deck().draw(5);
			hand=new PokerHand(cards);
			// pokerHandNumbers=Lookup.instance.lookup(pokerHand);
			handNumber=OldLookup.lookup(cards);
			typeOfPokerHand=PokerHand.HighType.type(handNumber);
			changeSubState(Phase.inAHand);
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
				changeSubState(Phase.betMade);
			}
		}
	}
	private final class BetMade extends SubState {
		public void bet() {
			if(credits>0) {
				credits--;
				if(++coins==pokerMachine.maxBets())
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
					hand.replace(i,pokerMachine.deck().draw());
			handNumber=OldLookup.lookup(hand);
			typeOfPokerHand=PokerHand.HighType.type(handNumber);
			payoff=pokerMachine.payMaster().payoff(typeOfPokerHand,handNumber);
			credits+=payoff*coins;
			hands++;
			coins=0;
			changeSubState(Phase.afterDraw);
		}
	}
	public SubState currentSubState() {
		return subStates.get(subState);
	}
	private final EnumMap<Phase,State.SubState> subStates=buildSubStates();
	private EnumMap<Phase,State.SubState> buildSubStates() {
		EnumMap<Phase,State.SubState> states=new EnumMap<>(Phase.class);
		states.put(Phase.afterDraw,new AfterDraw());
		states.put(Phase.betMade,new BetMade());
		states.put(Phase.inAHand,new InAHand());
		return states;
	}
}
