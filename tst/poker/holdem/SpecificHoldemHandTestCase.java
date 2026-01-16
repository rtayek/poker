package poker.holdem;
import poker.holdem.Don;
import poker.holdem.HoldemHand;
import poker.holdem.SpecificHoldemHand;
import java.util.Arrays;
import equipment.Card;
import equipment.Cards;
import equipment.Rank;
import equipment.Suit;
import junit.framework.TestCase;
public class SpecificHoldemHandTestCase extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
		Don.initializeDeck(deck);
	}
	// add a test to make sure we can construct one from any two cards
	// but make sure the cards are in order (highest rank first)!
	// 1/15/26 looks like we do not sort them!
	public void testAll2cards() {
		for(Card card1:Card.values())
			if(card1.rank().ordinal()>Rank.aceLow.ordinal()) for(Card card2:Card.values())
				if(card2.rank().ordinal()>Rank.aceLow.ordinal()) if(!card1.equals(card2)) {
					// looks like we need to check the order here
					SpecificHoldemHand hand=SpecificHoldemHand.of(card1,card2);
					assertTrue(hand!=null);
				}
	}
	public void testForSmoke() {
		SpecificHoldemHand h=new SpecificHoldemHand(Card.aceOfDiamonds,Card.aceOfHearts);
		assertEquals(HoldemHand.aceAce,h.holdemHand());
		h=new SpecificHoldemHand(Card.aceOfDiamonds,Card.queenOfHearts);
		assertEquals(HoldemHand.aceQueen,h.holdemHand());
		h=new SpecificHoldemHand(Card.aceOfDiamonds,Card.kingOfDiamonds);
		assertEquals(HoldemHand.aceKingSuited,h.holdemHand());
	}
	public void testIsHandInDeck() {
		int n=0;
		for(HoldemHand holdemHand:HoldemHand.values())
			switch(holdemHand.declaredType()) {
				case pair:
					for(Suit s1:Suit.values())
						if(!s1.equals(Suit.jokerSuit)) for(Suit s2:Suit.values())
							if(!s2.equals(Suit.jokerSuit)) if(s2.ordinal()>s1.ordinal()) {
								SpecificHoldemHand h=new SpecificHoldemHand(holdemHand,s1,s2);
								assertTrue(SpecificHoldemHand.isHandInDeck(h,deck));
								n++;
							}
					break;
				case suited:
					for(Suit s1:Suit.values())
						if(!s1.equals(Suit.jokerSuit)) {
							SpecificHoldemHand h=new SpecificHoldemHand(holdemHand,s1,s1);
							assertTrue(SpecificHoldemHand.isHandInDeck(h,deck));
							n++;
						}
					break;
				case offsuit:
					for(Suit s1:Suit.values())
						if(!s1.equals(Suit.jokerSuit)) for(Suit s2:Suit.values())
							if(!s2.equals(Suit.jokerSuit)) if(!s2.equals(s1)) {
								SpecificHoldemHand h=new SpecificHoldemHand(holdemHand,s1,s2);
								assertTrue(SpecificHoldemHand.isHandInDeck(h,deck));
								n++;
							}
					break;
			}
		assertEquals(Cards.c(52,2),n);
	}
	public void testFindHandInDeck() {
		int n=0;
		for(HoldemHand holdemHand:HoldemHand.values())
			switch(holdemHand.declaredType()) {
				case pair:
					assertTrue(holdemHand.r1.equals(holdemHand.r2));
					for(Suit s1:Suit.values())
						if(!s1.equals(Suit.jokerSuit)) for(Suit s2:Suit.values())
							if(!s2.equals(Suit.jokerSuit)) if(s2.ordinal()>s1.ordinal()) { // ?
								System.out.println(s1+" "+s2);
								System.out.println("hh: "+holdemHand);
								System.out.println("hh: "+Arrays.asList(holdemHand.cards()));
								SpecificHoldemHand h=new SpecificHoldemHand(holdemHand,s1,s2);
								System.out.println("h: "+h);
								assertTrue(h.card(0).rank().equals(h.card(1).rank()));
								assertTrue(SpecificHoldemHand.isHandInDeck(h,deck));
								n++;
							}
					break;
				case suited:
					for(Suit s1:Suit.values())
						if(!s1.equals(Suit.jokerSuit)) {
							SpecificHoldemHand h=new SpecificHoldemHand(holdemHand,s1,s1);
							assertTrue(!h.card(0).rank().equals(h.card(1).rank()));
							assertTrue(SpecificHoldemHand.isHandInDeck(h,deck));
							n++;
						}
					break;
				case offsuit:
					for(Suit s1:Suit.values())
						if(!s1.equals(Suit.jokerSuit)) for(Suit s2:Suit.values())
							if(!s2.equals(Suit.jokerSuit)) if(!s2.equals(s1)) {
								SpecificHoldemHand h=new SpecificHoldemHand(holdemHand,s1,s2);
								assertTrue(SpecificHoldemHand.isHandInDeck(h,deck));
								n++;
							}
					break;
			}
	}
	boolean[][] deck=new boolean[Rank.values().length][Suit.values().length];
}
