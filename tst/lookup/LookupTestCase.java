package lookup;
import poker.PokerHand;
import poker.PokerHand.HighType;
import equipment.*;
import experiment.Experiment;
import junit.framework.TestCase;
public class LookupTestCase extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testSort() {
		Rank[] ranks=Rank.values();
		int n=ranks.length;
		OldLookup.sortDescending(ranks);
		for(int i=0;i<n;i++)
			assertEquals(Rank.values()[n-1-i],ranks[i]);
	}
	public void testToCanonicalFormRankArray() {
		System.out.println("Not yet implemented");
	}
	public void testToCanonicalFormRankArrayBoolean() {
		System.out.println("Not yet implemented");
	}
	public void testToCanonicalFormCardArray() {
		System.out.println("Not yet implemented");
	}
	public void testKey() { // key and ketInverse may be for old code
		Rank[] zero= {Rank.jokerRank,Rank.jokerRank,Rank.jokerRank,Rank.jokerRank,Rank.jokerRank};
		assertEquals(0,OldLookup.key(zero,false));
		Rank[] big= {Rank.ace,Rank.ace,Rank.ace,Rank.ace,Rank.ace};
		assertEquals(1957340,OldLookup.key(big,false)); // magic number!
		System.out.println(Integer.toString(1957340/2,13));
		assertEquals(1957341,OldLookup.key(big,true)); // magic number!
	}
	public void testKeyInverse() { // key and ketInverse may be for old code
		Card[] eCard=new Card[5];
		eCard[0]=Card.aceLowOfClubs;
		eCard[1]=Card.aceLowOfClubs;
		eCard[2]=Card.aceLowOfClubs;
		eCard[3]=Card.aceLowOfClubs;
		eCard[4]=Card.aceLowOfClubs;
		// work here ...
		// Suit.clubs
		// rank,!isSuited&i==0?Suit.diamonds:Suit.clubs)
		// ECard[] keyInverse(long key)
		System.out.println("Not yet implemented");
	}
	int lookup(Card[] cards) {
		int n=OldLookup.lookup(cards);
		if (!(HighType.fiveOfAKind.first()<=n&&n<=HighType.noPair.last())) throw new RuntimeException("oops");
		return n;
	}
	public void testPokerHands() {
		for(PokerHand.HighType highType:HighType.values()) {
			assertEquals(highType.first(),lookup(highType.firstSample().cards()));
			assertEquals(highType.last(),lookup(highType.lastSample().cards()));
		}
	}
	public void testLookupHand() {
		System.out.println("Not yet implemented");
	}
	public void testLookupCardArray() {
		System.out.println("Not yet implemented");
	}
	public void testLookupRankArrayBoolean() {
		System.out.println("Not yet implemented");
	}
	public void testLookup_() {
		System.out.println("Not yet implemented");
	}
	public void testInverseLookup() {
		System.out.println("Not yet implemented");
	}
	public void testVerify() {
		System.out.println("Not yet implemented");
	}
	public void testInitialize_lookup() {
		System.out.println("Not yet implemented");
	}
	public void testMain() {
		System.out.println("Not yet implemented");
	}
	public void testFlush() {
		System.out.println("Not yet implemented");
	}
	public void testOther() {
		System.out.println("Not yet implemented");
	}
	public void testObject() {
		System.out.println("Not yet implemented");
	}
	public void testToString() {
		System.out.println("Not yet implemented");
	}
}
