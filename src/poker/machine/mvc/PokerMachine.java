package poker.machine.mvc;
import equipment.Deck;
import poker.machine.PayMaster;
import poker.machine.PokerMachineBase;
public class PokerMachine extends PokerMachineBase {
	public PokerMachine(final Deck deck,final PayMaster payMaster) {
		super(deck,payMaster);
	}
	@Override protected void onStateUpdated() {
		setChanged();
		notifyObservers();
	}
}
