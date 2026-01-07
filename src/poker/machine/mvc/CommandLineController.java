package poker.machine.mvc;
import poker.machine.CommandLineControllerBase;
public class CommandLineController extends CommandLineControllerBase {
	public CommandLineController(PokerMachine pokerMachine) {
		this.pokerMachine=pokerMachine;
	}
	void usage() {
		System.out.println("a - automatic");
		System.out.println("b - bet");
		System.out.println("B - bet max");
		System.out.println("d - deal");
		System.out.println("r - draw");
		System.out.println("[1-5] - toggle hold/discard");
		System.out.println("q - quit");
	}
	boolean play() {
		loop:do {
			int c;
			if((c=get())==-1) {
				playMore=false;
				break loop;
			}
			if(!automatic)
				System.out.println(prompt);
			System.out.println("got a "+(char)c);
			playMore=processEvent(pokerMachine,(char)c);
			if(pokerMachine.state().credits()==0) {
				System.out.println("bankrupt");
				playMore=false;
			}
		} while(playMore);
		return playMore;
	}
	public void run() {
		System.out.println(prompt);
		while(play()&&pokerMachine.state().credits()>0) {
			if(automatic&&pokerMachine.state().hands()%1000==0)
				System.out.println(" "+pokerMachine.state().hands());
			System.out.flush();
		}
		waitForEnter("done");
	}
	boolean playMore;
	PokerMachine pokerMachine;
	private static final String prompt="b - bet, B - bet max, d - deal, [1-5] - toggle, r - draw, q - quit";
}
