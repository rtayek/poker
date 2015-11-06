package poker.machine.original;
public class Controller {
	public boolean processEvent(final PokerMachine pokerMachine,final char c) {
		switch(c) {
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
				pokerMachine.toggleHold(c-'1');
				break;
			case 'a':
				if(pokerMachine.state().subState()!=pokerMachine.state().inAHand) {
					processEvent(pokerMachine,'B');
					processEvent(pokerMachine,'r');
				}
				break;
			case 'b':
				pokerMachine.bet();
				break;
			case 'B':
				if(pokerMachine.state().subState()!=pokerMachine.state().inAHand)
					while(pokerMachine.state().coins()<PokerMachine.maxBets&&pokerMachine.state().credits>0)
						processEvent(pokerMachine,'b');
				break;
			case 'd':
				pokerMachine.deal();
				break;
			case 'r':
				pokerMachine.draw();
				break;
			case 'q':
				return false;
			default:
				System.err.println("exiting!");
				System.exit(1);
		}
		return true;
	}
	public boolean automatic;
}
