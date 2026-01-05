package equipment;
import equipment.Suit;
import junit.framework.TestCase;
public class SuitTestCase extends TestCase {
	public void testFromCharacter() {
		for(Suit s:Suit.values())
			assertEquals(s,Suit.fromCharacter(s.toCharacter()));
	}
	public void testToCharacter() { // worlds dumbest test?
		for(Suit s:Suit.values())
			assertEquals(s.c,s.toCharacter());
	}
	public void testToAndFromCharacter() {
		for(Suit s:Suit.values())
			assertEquals(s,Suit.fromCharacter(s.toCharacter()));
	}
	public void testToString() {
		for(Suit s:Suit.values())
		assertEquals("Joker",Suit.jokerSuit.toString());
		assertEquals("Club",Suit.clubs.toString());
		assertEquals("Diamond",Suit.diamonds.toString());
		assertEquals("Heart",Suit.hearts.toString());
		assertEquals("Spade",Suit.spades.toString());
	}
	public void testToStringSuitArray() {
		Suit[] suit={Suit.clubs,Suit.diamonds,Suit.hearts,Suit.spades};
		assertEquals("cdhs",Suit.toString(suit));
	}
	public void testAreSuited() {
		Suit[] suited={Suit.clubs,Suit.clubs};
		assertTrue(Suit.areSuited(suited));
		Suit[] notSuited={Suit.clubs,Suit.hearts};
		assertFalse(Suit.areSuited(notSuited));
	}
}
