package poker.machine;
import poker.machine.original.*;
import equipment.Deck;
import junit.framework.TestCase;

public class PokerTestCase extends TestCase {
	public static void main(String[] args) {
		junit.textui.TestRunner.run(PokerTestCase.class);
	}
	public void testRun() {
		final CommandLineView p=new CommandLineView();
		final Deck d=new Deck();
    	final PokerMachine pm=new PokerMachine(d,new HighPayoff(),p);
    	final Controller c=new Controller();
    	c.processEvent(pm,'b');
    	c.processEvent(pm,'d');
    	System.out.println("state="+pm.state());
	}
}
