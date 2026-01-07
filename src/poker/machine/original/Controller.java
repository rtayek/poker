package poker.machine.original;
public class Controller {
	public boolean processEvent(final PokerMachine pokerMachine,final char c) {
		switch(c) {
			case '1','2','3','4','5' -> pokerMachine.toggleHold(c-'1');
			case 'a' -> {
				if(!pokerMachine.state().isInAHand()) {
					processEvent(pokerMachine,'B');
					processEvent(pokerMachine,'r');
				}
			}
			case 'b' -> pokerMachine.bet();
			case 'B' -> {
				if(!pokerMachine.state().isInAHand())
					while(pokerMachine.state().coins()<PokerMachine.maxBets&&pokerMachine.state().credits()>0)
						processEvent(pokerMachine,'b');
			}
			case 'd' -> pokerMachine.deal();
			case 'r' -> pokerMachine.draw();
			case 'q' -> {
				return false;
			}
			default -> {
				System.err.println("exiting!");
				System.exit(1);
			}
		}
		return true;
	}
	private boolean automatic;
}
