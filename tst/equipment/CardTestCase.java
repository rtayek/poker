package equipment;
import java.util.Arrays;
import junit.framework.TestCase;
public class CardTestCase extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testAreSuited() {
		final Card[] suited={Card.aceLowOfClubs,Card.dueceOfClubs};
		assertTrue(Cards.areSuited(suited));
		final Card[] notSuited={Card.aceLowOfClubs,Card.dueceOfDiamonds};
		assertFalse(Cards.areSuited(notSuited));
	}
	public void testAreNatural() {
		Card[] natural=new Deck().cards();
		assertTrue(Cards.areNatural(natural));
		Card[] jokers={Card.instance(Rank.jokerRank,Suit.jokerSuit)};
		assertFalse(Cards.areNatural(jokers));
		Card[] gardena=new Deck(1,1).cards();
		assertFalse(Cards.areNatural(gardena));
	}
	public void testRanks() {
		final Card[] notSuited={Card.aceLowOfClubs,Card.dueceOfDiamonds};
		final Rank[] expected={Rank.aceLow,Rank.deuce};
		final Rank[] ranks=Cards.ranks(notSuited);
		for(int i=0;i<expected.length;i++)
			System.out.println(expected[i]);
		for(int i=0;i<ranks.length;i++)
			System.out.println(expected[i]);
		assertTrue(Arrays.equals(expected,ranks));
	}
	public void testSuits() {
		final Card[] notSuited={Card.aceLowOfClubs,Card.dueceOfDiamonds};
		final Suit[] expected={Suit.clubs,Suit.diamonds};
		final Suit[] suits=Cards.suits(notSuited);
		assertTrue(Arrays.equals(expected,suits));
	}
	public void testReplace() {
		//fail("Not yet implemented");
	}
	public void testIsNatural() {
		final Card joker=Card.instance(Rank.jokerRank,Suit.jokerSuit);
		assertFalse(joker.isNatural());
	}
	public void testRank() {
		assertEquals(Rank.aceLow,Card.aceLowOfClubs.rank());
		assertEquals(Rank.ace,Card.aceOfClubs.rank());
	}
	public void testSuit() {
		assertEquals(Suit.clubs,Card.aceLowOfClubs.suit());
		assertEquals(Suit.clubs,Card.aceOfClubs.suit());
	}
	public void testToString() {
		final Card card=Card.aceLowOfClubs;
		assertEquals("Ace(low) of Clubs",card.toString());
		final Card card2=Card.aceOfClubs;
		assertEquals("Ace of Clubs",card2.toString());
	}
	public void testToStringCardArray() {
		final Card[] notSuited={Card.aceLowOfClubs,Card.dueceOfDiamonds};
		assertEquals("ac, 2d",Card.toString(notSuited));
	}
	public void testToCharacters() {
		final Card card=Card.aceLowOfClubs;
		assertEquals("ac",card.toCharacters());
		final Card card2=Card.aceOfClubs;
		assertEquals("Ac",card2.toCharacters());
	}
	public void testInstance() {
		for(Card card:Card.values())
			assertEquals(card,Card.instance(card.rank(),card.suit()));
		for(Rank rank:Rank.values())
			for(Suit suit:Suit.values())
				assertNotNull(Card.instance(rank,suit));
		System.out.println("************************************");
		for(Rank rank:Rank.values())
			if(rank.ordinal()<=2)
			for(Suit suit:Suit.values())
					System.out.println(rank+" "+suit+" "+Card.instance(rank,suit));
		System.out.println("************************************");
	}
	public void testCreate() {
		//fail("Not yet implemented");
	}
}
