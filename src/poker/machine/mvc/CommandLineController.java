package poker.machine.mvc;
import java.io.IOException;
public class CommandLineController {
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
	int getChar() {
		int c=-1;
		if(true) {
			try {
				c=System.in.read();
			} catch(IOException e) {}
		}
		if(lineSeparator.indexOf(c)!=-1)
			return getChar();
		return c;
	}
	int get() {
		int c=-1;
		if(!automatic)
			c=getChar();
		else
			c=switch(++n%3) {
				case 0 -> 'b';
				case 1 -> 'd';
				default -> 'r';
			};
		return c;
	}
	static void waitForEnter(String s) {
		System.out.println(s);
		waitForEnter();
	}
	static void waitForEnter() {
		System.out.println("type enter to continue");
		System.out.flush();
		try {
			System.in.read();
		} catch(IOException e) {}
	}
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
	private boolean automatic;
	PokerMachine pokerMachine;
	private static final String prompt="b - bet, B - bet max, d - deal, [1-5] - toggle, r - draw, q - quit";
	static int n=2;
	static final String lineSeparator=System.getProperty("line.separator");
}
