package poker.machine.original;
import equipment.Deck;
import poker.machine.PayMaster;
import poker.machine.PokerMachineBase;
// this is constructed with a view. this is not quite a normal mvc implementation
public class PokerMachine extends PokerMachineBase { // maybe add changing state ???
	public PokerMachine(final Deck deck,final PayMaster payMaster,final View view) {
		super(deck,payMaster);
		this.view=view;
	}
	@Override protected void onStateUpdated() {
		view.update(state());
	}
	private final View view;
}
