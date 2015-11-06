package poker.machine.mvc;
import java.util.*;
import equipment.*;
import lookup.OldLookup;
import poker.*;
import poker.machine.*;
public class PokerMachine extends Observable {
	public PokerMachine(final Deck deck,final PayMaster payMaster) {
		this.deck=deck;
		this.payMaster=payMaster;
	}
	public final void bet() {
		state.subStates[state.subState].bet();
		setChanged();
		notifyObservers();
	}
	public final void deal() {
		state.subStates[state.subState].deal();
		setChanged();
		notifyObservers();
	}
	public final void draw() {
		state.subStates[state.subState].draw();
		setChanged();
		notifyObservers();
	}
	public final void toggleHold(int n) {
		state.subStates[state.subState].toggleHold(n);
		setChanged();
		notifyObservers();
	}
	public final void quit() {
		state.subStates[state.subState].quit();
		setChanged();
		notifyObservers();
	}
	public final boolean hold(int i) {
		return state.holds[i];
	}
	public final State state() {
		return state;
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
	public void analyze() { // add straight & flush draws!!!
		Rank[] ranks=state().hand.ranks();
		PokerHand.HighType type=state().typeOfPokerHand;
		if(type==PokerHand.HighType.onePair) {
			for(int i=0;i<4;i++)
				for(int j=i+1;j<5;j++)
					if(ranks[i]==ranks[j]) {
						if(!hold(i))
							toggleHold(i);
						if(!hold(j))
							toggleHold(j);
						return;
					}
			System.err.println("analyze fails");
		} else if(type==PokerHand.HighType.twoPair||type==PokerHand.HighType.fourOfAKind) {
			for(int i=0;i<5;i++)
				if(!hold(i))
					toggleHold(i);
			loop:for(int i=0;i<5;i++) {
				for(int j=0;j<5;j++)
					if(i!=j) {
						if(ranks[i]==ranks[j])
							continue loop;
					}
				if(hold(i))
					toggleHold(i);
				return;
			}
			System.err.println("analyze fails");
		} else if(type==PokerHand.HighType.threeOfAKind) {
			for(int i=0;i<3;i++)
				for(int j=i+1;j<4;j++)
					for(int k=j+1;k<5;k++)
						if(ranks[i]==ranks[j]&&ranks[i]==ranks[k]) {
							if(!hold(i))
								toggleHold(i);
							if(!hold(j))
								toggleHold(j);
							if(!hold(k))
								toggleHold(k);
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
	private State state=new State(this);
	private int verbosity=0;
	public/* final */Deck deck;
	public/* final */PayMaster payMaster;
	public static final int maxBets=5;
}
