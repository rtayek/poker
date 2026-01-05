package poker;
import equipment.*;
import junit.framework.TestCase;
public class Lookup2TestCase extends TestCase {
	public static void main(String[] args) {
		junit.textui.TestRunner.run(Lookup2TestCase.class);
	}
	public Lookup2TestCase(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	int lookup(Card[] cards) {
		Hand hand=new PokerHand(cards);
		//final Lookup2.PokerHandNumbers phn=Lookup2.lookup(cards);
		//assertNotNull(phn);
		//short highHandNumber=phn.highHandNumber;
		//return highHandNumber;
		return 0;
	}
	public void testKey() {}
	public void testKeyInverse() {}
	public void testLookupCardArray() {}
	public void testLookupint() {}
}
