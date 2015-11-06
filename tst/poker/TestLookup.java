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
	public void testAllCombinationsOfFiveCards() {
		int n=52,n1=n-1,n2=n-2,n3=n-3,n4=n-4;
		int c1,c2,c3,c4,c5;
		// why not use deck here?
		final Card[] deck=Deck.create(1,0);
		final Card[] cards=new Card[5];
		for(hands=0,time=0,c1=0;c1<n4;c1++) {
			cards[0]=deck[c1];
			for(c2=c1+1;c2<n3;c2++) {
				cards[1]=deck[c2];
				for(c3=c2+1;c3<n2;c3++) {
					cards[2]=deck[c3];
					for(c4=c3+1;c4<n1;c4++) {
						cards[3]=deck[c4];
						for(c5=c4+1;c5<n;c5++) {
							cards[4]=deck[c5];
							final int highHandNumber=OldLookup.lookup(cards);
							assertTrue("lookup fails on: "+new PokerHand(cards),highHandNumber!=0);
							hands++;
						}
					}
				}
			}
		}
		assertEquals("hands should equal total",naturalFiveCardCombinations,hands);
	}
	int hands;
	long time;
}
