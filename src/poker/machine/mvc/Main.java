package poker.machine.mvc;
import poker.machine.HighPayoff;
import equipment.Deck;
public class Main {
	public static void main(String[] args) {
		try {
			final Deck deck=new Deck();
			PokerMachine pokerMachine=new PokerMachine(deck,new HighPayoff());
			CommandLineView commandLineView=new CommandLineView(pokerMachine,args.length>0&&args[0].equals("-a"));
			// PokerCardFactory.instance.hack();
			pokerMachine.addObserver(commandLineView);
			CommandLineController controller=new CommandLineController(pokerMachine);
			controller.run();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
