package poker.machine.original;
import poker.machine.CommandLineBase;
import equipment.*;
import poker.*;
import poker.machine.*;
public class CommandLineView extends CommandLineBase implements View {
	@Override public void update(State state) {
		this.state=state;
		String s="";
		if(state.pokerHand()==null||state.isBetMade())
			s+="XXXXX";
		else s+=state.pokerHand().toCharacters()+" "+info(state.pokerHand(),state.handNumber());
		s=s+lineSeparator;
		if(!(state.pokerHand()==null||state.isBetMade())) {
			for(int i=0;i<5;i++)
				s+=" "+(state.isHeld(i)?'H':'T');
			s=s+lineSeparator;
		}
		if(state.isAfterDraw()&&state.payoff()>0)
			s+="payoff "+state.payoff()+lineSeparator;
		s+=("hand "+state.hands()+", "+state.coins()+" Coins, "+state.credits()+" credits");
		if(/* true|| */!automatic||state.hands()%250==0)
			println(s);
		this.state=state;
	}
	void usage() {
		println("a - automatic");
		println("b - bet");
		println("B - bet max");
		println("d - deal");
		println("r - draw");
		println("[1-5] - toggle hold");
		println("q - quit");
	}
	String info(final Hand hand,final int pokerHandNumber) {
		int highHandNumber=pokerHandNumber;
		if(highHandNumber!=0) {
			PokerHand.HighType type=PokerHand.HighType.type(highHandNumber);
			String s=" is #"+highHandNumber+" "+type;
			return s;
		}
		waitForEnter("info fails");
		return "can't find this hand";
	}
	boolean play() {
		loop:do {
			int c;
			if((c=get())==-1) {
				playMore=false;
				break loop;
			}
			if(!automatic)
				println(prompt);
			System.out.println("got a "+(char)c);
			playMore=controller.processEvent(pokerMachine,(char)c);
			if(pokerMachine.state().credits()==0) {
				println("bankrupt");
				playMore=false;
			}
		} while(playMore);
		return playMore;
	}
	public void start() { // this does not belong here
		// seems like it should not be part of the view.
		// seem like something else should add this as a view.
		// PokerCardFactory.instance.hack();
		final Deck pokerDeck=new Deck();
		pokerMachine=new PokerMachine(pokerDeck,new HighPayoff(),this);
	}
	public void run() {
		println(prompt);
		while(play()&&pokerMachine.state().credits()>0) {
			if(automatic&&state.hands()%1000==0)
				println(" "+state.hands());
			System.out.flush();
		}
		waitForEnter("done");
	}
	void println(String s) {
		System.out.println(s);
	}
	public static void main(String[] args) {
		try {
			CommandLineView poker=new CommandLineView();
			if(args.length>0&&args[0].equals("-a"))
				poker.automatic=true;
			poker.start();
			poker.run();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	private boolean playMore;
	private PokerMachine pokerMachine;
	private final Controller controller=new Controller();
	private State state;
	private static final String prompt="b - bet, d - deal, [1-5] - toggle, r - draw";
}
