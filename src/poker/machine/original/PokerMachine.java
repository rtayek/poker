package poker.machine.original;
import equipment.*;
import lookup.OldLookup;
import poker.*;
import poker.machine.PayMaster;
// this is constructed with a view. this is not quite a normal mvc implementation
public class PokerMachine { // maybe add changing state ???
	public PokerMachine(final Deck deck,final PayMaster payMaster,final View view) {
		this.deck=deck;
		this.payMaster=payMaster;
		this.view=view;
	}
	public final void bet() {
		state.subStates[state.subState].bet();
		view.update(state);
	}
	public final void deal() {
		state.subStates[state.subState].deal();
		view.update(state);
	}
	public final void draw() {
		state.subStates[state.subState].draw();
		view.update(state);
	}
	public final void toggleHold(int n) {
		state.subStates[state.subState].toggleHold(n);
		view.update(state);
	}
	public final void quit() {
		state.subStates[state.subState].quit();
		view.update(state);
	}
	public final boolean hold(int i) {
		return state.holds[i];
	}
	public final State state() {
		return state;
	}
	public class State {
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
		Hand hand;
		PokerHand.HighType typeOfPokerHand;
		int subState=afterDraw,hands=0,coins=0,credits=2000,payoff;
		abstract class SubState {
			void bet() {}
			void deal() {}
			void draw() {}
			void toggleHold(int n) {}
			void quit() {}
			protected/* was private */void deal_() {
				for(int i=0;i<5;i++)
					holds[i]=false;
				deck.shuffle();
				Card[] cards=deck.draw(5);
				hand=new PokerHand(cards);
				// pokerHandNumbers=Lookup.instance.lookup(pokerHand);
				handNumber=OldLookup.lookup(cards);
				typeOfPokerHand=PokerHand.HighType.type(handNumber);
				changeSubState(inAHand);
				analyze(PokerMachine.this);
			}
		}
		private final class AfterDraw extends SubState {
			void bet() {
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
			void bet() {
				if(credits>0) {
					credits--;
					if(++coins==maxBets)
						deal_();
				}
			}
			void deal() {
				deal_();
			}
		}
		private final class InAHand extends SubState {
			void toggleHold(int n) {
				holds[n]=!holds[n];
			}
			void draw() {
				for(int i=0;i<5;i++)
					if(!holds[i])
						hand.replace(i,deck.draw());
				handNumber=OldLookup.lookup(hand);
				typeOfPokerHand=PokerHand.HighType.type(handNumber);
				payoff=PokerMachine.this.payMaster.payoff(typeOfPokerHand,handNumber);
				credits+=payoff*coins;
				hands++;
				coins=0;
				changeSubState(afterDraw);
			}
			InAHand() {}
		}
		private final State.SubState[] subStates={new AfterDraw(),new BetMade(),new InAHand()};
	}
	private void throwAll() {
		for(int i=0;i<5;i++)
			if(hold(i))
				toggleHold(i);
	}
	private void holdAll() {
		for(int i=0;i<5;i++)
			if(!hold(i))
				toggleHold(i);
	}
	private void analyze(PokerMachine pokerMachine) { // add straight & flush draws!!!
		Rank[] ranks=pokerMachine.state().hand.ranks();
		PokerHand.HighType type=pokerMachine.state().typeOfPokerHand;
		if(type==PokerHand.HighType.onePair) {
			for(int i=0;i<4;i++)
				for(int j=i+1;j<5;j++)
					if(ranks[i]==ranks[j]) {
						if(!pokerMachine.hold(i))
							pokerMachine.toggleHold(i);
						if(!pokerMachine.hold(j))
							pokerMachine.toggleHold(j);
						return;
					}
			System.err.println("analyze fails");
		} else if(type==PokerHand.HighType.twoPair||type==PokerHand.HighType.fourOfAKind) {
			for(int i=0;i<5;i++)
				if(!pokerMachine.hold(i))
					pokerMachine.toggleHold(i);
			loop:for(int i=0;i<5;i++) {
				for(int j=0;j<5;j++)
					if(i!=j) {
						if(ranks[i]==ranks[j])
							continue loop;
					}
				if(pokerMachine.hold(i))
					pokerMachine.toggleHold(i);
				return;
			}
			System.err.println("analyze fails");
		} else if(type==PokerHand.HighType.threeOfAKind) {
			for(int i=0;i<3;i++)
				for(int j=i+1;j<4;j++)
					for(int k=j+1;k<5;k++)
						if(ranks[i]==ranks[j]&&ranks[i]==ranks[k]) {
							if(!pokerMachine.hold(i))
								pokerMachine.toggleHold(i);
							if(!pokerMachine.hold(j))
								pokerMachine.toggleHold(j);
							if(!pokerMachine.hold(k))
								pokerMachine.toggleHold(k);
							return;
						}
			System.err.println("analyze fails");
		} else if(type==PokerHand.HighType.straight) {
			holdAll();
		} else if(type==PokerHand.HighType.flush) {
			holdAll();
		} else if(type==PokerHand.HighType.fullHouse) {
			holdAll();
		} else if(type==PokerHand.HighType.straightFlush) {
			holdAll();
		} else if(type==PokerHand.HighType.fiveOfAKind) {
			holdAll();
		} else if(type==PokerHand.HighType.noPair) {
			throwAll();
		} else {
			System.err.println("analyze fails");
			System.exit(1);
		}
	}
	private State state=new State();
	private int verbosity=0;
	private/* final */Deck deck;
	private/* final */PayMaster payMaster;
	private/* final */View view;
	public static final int maxBets=5;
}
