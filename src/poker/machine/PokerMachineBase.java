package poker.machine;
import java.util.Observable;
import equipment.Deck;
public abstract class PokerMachineBase extends Observable implements State.Context,State.HoldController,CommandLineMachine {
	protected PokerMachineBase(final Deck deck,final PayMaster payMaster) {
		this.deck=deck;
		this.payMaster=payMaster;
	}
	protected abstract void onStateUpdated();
	public final void bet() {
		state.currentSubState().bet();
		onStateUpdated();
	}
	public final void deal() {
		state.currentSubState().deal();
		onStateUpdated();
	}
	public final void draw() {
		state.currentSubState().draw();
		onStateUpdated();
	}
	public final void toggleHold(int n) {
		state.currentSubState().toggleHold(n);
		onStateUpdated();
	}
	public final void quit() {
		state.currentSubState().quit();
		onStateUpdated();
	}
	public final boolean hold(int i) {
		return state.isHeld(i);
	}
	public final State state() {
		return state;
	}
	@Override public final void analyze() { // add straight & flush draws!!!
		State.HandAnalyzer.analyze(state,this);
	}
	@Override public final Deck deck() {
		return deck;
	}
	@Override public final PayMaster payMaster() {
		return payMaster;
	}
	@Override public int maxBets() {
		return maxBets;
	}
	private final State state=new State(this);
	private final Deck deck;
	private final PayMaster payMaster;
	public static final int maxBets=5;
}
