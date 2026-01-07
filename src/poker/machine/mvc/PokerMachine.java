package poker.machine.mvc;
import java.util.*;
import equipment.*;
import poker.machine.*;
public class PokerMachine extends Observable implements State.Context,State.HoldController {
	public PokerMachine(final Deck deck,final PayMaster payMaster) {
		this.deck=deck;
		this.payMaster=payMaster;
	}
	public final void bet() {
		state.currentSubState().bet();
		setChanged();
		notifyObservers();
	}
	public final void deal() {
		state.currentSubState().deal();
		setChanged();
		notifyObservers();
	}
	public final void draw() {
		state.currentSubState().draw();
		setChanged();
		notifyObservers();
	}
	public final void toggleHold(int n) {
		state.currentSubState().toggleHold(n);
		setChanged();
		notifyObservers();
	}
	public final void quit() {
		state.currentSubState().quit();
		setChanged();
		notifyObservers();
	}
	public final boolean hold(int i) {
		return state.isHeld(i);
	}
	public final State state() {
		return state;
	}
	@Override public void analyze() { // add straight & flush draws!!!
		State.HandAnalyzer.analyze(state,this);
	}
	private State state=new State(this);
	private int verbosity=0;
	public/* final */Deck deck;
	public/* final */PayMaster payMaster;
	public static final int maxBets=5;
	@Override public Deck deck() {
		return deck;
	}
	@Override public PayMaster payMaster() {
		return payMaster;
	}
	@Override public int maxBets() {
		return maxBets;
	}
}
