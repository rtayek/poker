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
		else switch(++n%3) {
			case 0:
				c='b';
				break;
			case 1:
				c='d';
				break;
			case 2:
				c='r';
				break;
		}
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
	public boolean automatic;
	PokerMachine pokerMachine;
	private static final String prompt="b - bet, B - bet max, d - deal, [1-5] - toggle, r - draw, q - quit";
	static int n=2;
	static final String lineSeparator=System.getProperty("line.separator");
}
