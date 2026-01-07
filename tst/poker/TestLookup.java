package poker;
import static poker.Constants.*;
import equipment.*;
import junit.framework.TestCase;
import lookup.OldLookup;
public class TestLookup extends TestCase {
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestLookup.class);
	}
	public TestLookup(String name) {
		super(name);
		System.out.println(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testVerify() {
		assertTrue(OldLookup.verify());
	}
	public void testSampleForOldCCode() {
		final Card[] cards=new Card[] {Card.dueceOfClubs,Card.treyOfClubs,Card.fourOfClubs,Card.fiveOfClubs,Card.sixOfClubs};
		System.out.println(Card.toString(cards));
		int highHandNumber=OldLookup.lookup(cards);
		System.out.print("hand number="+highHandNumber);
		System.out.println(", "+Card.toString(OldLookup.inverseLookup(highHandNumber)));
		cards[4]=Card.sevenOfClubs;
		System.out.println(Card.toString(cards));
		highHandNumber=OldLookup.lookup(cards);
		System.out.print("hand number="+highHandNumber);
		System.out.println(", "+Card.toString(OldLookup.inverseLookup(highHandNumber)));

		cards[4]=Card.sixOfDiamonds;
		System.out.println(Card.toString(cards));
		highHandNumber=OldLookup.lookup(cards);
		System.out.print("hand number="+highHandNumber);
		System.out.println(", "+Card.toString(OldLookup.inverseLookup(highHandNumber)));
		cards[4]=Card.sevenOfDiamonds;
		System.out.println(Card.toString(cards));
		highHandNumber=OldLookup.lookup(cards);
		System.out.print("hand number="+highHandNumber);
		System.out.println(", "+Card.toString(OldLookup.inverseLookup(highHandNumber)));
	}
	public void testSampleForOldCCodeWithAces() {
		final Card[] cards=new Card[] {Card.aceLowOfClubs,Card.dueceOfClubs,Card.treyOfClubs,Card.fourOfClubs,Card.fiveOfClubs};
		System.out.println(Card.toString(cards));
		int highHandNumber=OldLookup.lookup(cards);
		System.out.print("hand number="+highHandNumber);
		System.out.println(", "+Card.toString(OldLookup.inverseLookup(highHandNumber)));
		cards[4]=Card.sixOfClubs;
		System.out.println(Card.toString(cards));
		highHandNumber=OldLookup.lookup(cards);
		System.out.print("hand number="+highHandNumber);
		System.out.println(", "+Card.toString(OldLookup.inverseLookup(highHandNumber)));

		cards[4]=Card.fiveOfDiamonds;
		System.out.println(Card.toString(cards));
		highHandNumber=OldLookup.lookup(cards);
		System.out.print("hand number="+highHandNumber);
		System.out.println(", "+Card.toString(OldLookup.inverseLookup(highHandNumber)));
		cards[4]=Card.sixOfDiamonds;
		System.out.println(Card.toString(cards));
		highHandNumber=OldLookup.lookup(cards);
		System.out.print("hand number="+highHandNumber);
		System.out.println(", "+Card.toString(OldLookup.inverseLookup(highHandNumber)));
	}
}
