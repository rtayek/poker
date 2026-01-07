package poker.machine.original;
import equipment.*;
import poker.*;
import poker.machine.PayMaster;
import poker.machine.State;
// this is constructed with a view. this is not quite a normal mvc implementation
public class PokerMachine implements State.Context,State.HoldController { // maybe add changing state ???
	public PokerMachine(final Deck deck,final PayMaster payMaster,final View view) {
		this.deck=deck;
		this.payMaster=payMaster;
		this.view=view;
	}
	public final void bet() {
		state.currentSubState().bet();
		view.update(state);
	}
	public final void deal() {
		state.currentSubState().deal();
		view.update(state);
	}
	public final void draw() {
		state.currentSubState().draw();
		view.update(state);
	}
	public final void toggleHold(int n) {
		state.currentSubState().toggleHold(n);
		view.update(state);
	}
	public final void quit() {
		state.currentSubState().quit();
		view.update(state);
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
	@Override public Deck deck() {
		return deck;
	}
	@Override public PayMaster payMaster() {
		return payMaster;
	}
	@Override public int maxBets() {
		return maxBets;
	}
	private final State state=new State(this);
	private int verbosity=0;
	private/* final */Deck deck;
	private/* final */PayMaster payMaster;
	private/* final */View view;
	public static final int maxBets=5;
}
