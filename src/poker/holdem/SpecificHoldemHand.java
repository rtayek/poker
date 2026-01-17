package poker.holdem;
import java.util.List;
import equipment.Card;
import equipment.Hand;
import equipment.Rank;
import equipment.Suit;
// should be an enum, but there are 1326 of them, so write a program later to generate the enums
public class SpecificHoldemHand extends Hand {
	public static SpecificHoldemHand of(Card card1,Card card2) {
		if (card1.rank().ordinal()<card2.rank().ordinal()) return new SpecificHoldemHand(card2,card1);
		return new SpecificHoldemHand(card1,card2);
	}
	public SpecificHoldemHand(HoldemHand holdemHand,Suit suit1,Suit suit2) {
		super(Card.instance(holdemHand.r1(),suit1),Card.instance(holdemHand.r2(),suit2));
		//System.out.println(Card.instance(holdemHand.r1(),suit1));
		//System.out.println(Card.instance(holdemHand.r2(),suit2));
		this.holdemHand=holdemHand;
		this.suit1=suit1;
		this.suit2=suit2;
	}
	// write some tests
	public SpecificHoldemHand(Card card1,Card card2) {
		this(new Card[]{card1,card2});
	}
	public SpecificHoldemHand(Card[] cards) {
		super(cards);
		holdemHand=HoldemHand.type(cards);
		suit1=cards[0].suit();
		suit2=cards[1].suit();
	}
	static boolean areCardsInDeck(Card[] cards,boolean[][] deck) {
		if (!deck[cards[0].rank().ordinal()][cards[0].suit().ordinal()]) return false;
		if (!deck[cards[1].rank().ordinal()][cards[1].suit().ordinal()]) return false;
		return true;
	}
	static boolean isHandInDeck(SpecificHoldemHand h,boolean[][] deck) {
		if (!deck[h.holdemHand().r1().ordinal()][h.suit1.ordinal()]) return false;
		if (!deck[h.holdemHand().r2().ordinal()][h.suit2.ordinal()]) return false;
		return true;
	}
	static List<SpecificHoldemHand> findAllHandsInDeck(HoldemHand holdemHand,boolean[][] deck) {
		return null;
	}
	static SpecificHoldemHand findHandInDeck(HoldemHand holdemHand,boolean[][] deck) {
		// needs to be cased out by type of hand.
		// then generate all samples of these as static a final collection
		// then get them with SpecificHoldemHand[]=get(HoldemHand,boolean[][]
		// deck)
		// or SpecificHoldemHand[]=get(HoldemHand,SpecificHoldemHand[] used)
		// now we can get equity for 1 hand vs another heads up and and more
		// players later.
		// first against all other hands
		// and then maybe agains hand ranges.
		// this will net a get(HoldemHand,SpecificHoldemHand[] used) over his
		// range
		// more work needed here.
		for(Suit s1:Suit.values())
			if (!s1.equals(Suit.jokerSuit)) for(Suit s2:Suit.values())
				if (!s2.equals(Suit.jokerSuit)) if (s2.ordinal()>s1.ordinal()) {
					SpecificHoldemHand h=new SpecificHoldemHand(holdemHand,s1,s2);
					if (isHandInDeck(h,deck)) return h;
				}
		return null;
	}
	static SpecificHoldemHand fromCharacters(String s) {
		// don needs the fix aces for lookup, do we need it????
		Rank r1=Rank.fromCharacter(s.charAt(0)).fixAces();
		Suit s1=Suit.fromCharacter(s.charAt(1));
		Rank r2=Rank.fromCharacter(s.charAt(2)).fixAces();
		Suit s2=Suit.fromCharacter(s.charAt(3));
		HoldemHand.Type type=null;
		if (r1==r2) {
			if (s1==s2) throw new RuntimeException("oops");
			if(!r1.equals(r2)) throw new RuntimeException("oops");
			type=HoldemHand.Type.pair;
		} else {
			if (r1.ordinal()<r2.ordinal()) throw new RuntimeException("oops");
			if (s1==s2) type=HoldemHand.Type.suited;
			else type=HoldemHand.Type.offsuit;
		}
		if (type==null) throw new RuntimeException("oops");
		HoldemHand holdemHand=HoldemHand.from(r1,r2,type);
		return new SpecificHoldemHand(holdemHand,s1,s2);
	}
	public HoldemHand holdemHand() {
		return holdemHand;
	}
	private final HoldemHand holdemHand;
	private final Suit suit1,suit2;
}
