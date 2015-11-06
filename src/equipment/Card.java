package equipment;
import static equipment.Rank.*;
import static equipment.Suit.*;
import java.util.EnumSet;
public enum Card {
	joker(jokerRank,Suit.jokerSuit),aceLowOfClubs(aceLow,clubs),aceLowOfDiamonds(aceLow,diamonds),aceLowOfHearts(aceLow,hearts),aceLowOfSpades(aceLow,spades),dueceOfClubs(
			deuce,clubs),dueceOfDiamonds(deuce,diamonds),dueceOfHearts(deuce,hearts),dueceOfSpades(deuce,spades),treyOfClubs(trey,clubs),treyOfDiamonds(trey,
			diamonds),treyOfHearts(trey,hearts),treyOfSpades(trey,spades),fourOfClubs(four,clubs),fourOfDiamonds(four,diamonds),fourOfHearts(four,hearts),fourOfSpades(
			four,spades),fiveOfClubs(five,clubs),fiveOfDiamonds(five,diamonds),fiveOfHearts(five,hearts),fiveOfSpades(five,spades),sixOfClubs(six,clubs),sixOfDiamonds(
			six,diamonds),sixOfHearts(six,hearts),sixOfSpades(six,spades),sevenOfClubs(seven,clubs),sevenOfDiamonds(seven,diamonds),sevenOfHearts(seven,hearts),sevenOfSpades(
			seven,spades),eightOfClubs(eight,clubs),eightOfDiamonds(eight,diamonds),eightOfHearts(eight,hearts),eightOfSpades(eight,spades),nineOfClubs(nine,
			clubs),nineOfDiamonds(nine,diamonds),nineOfHearts(nine,hearts),nineOfSpades(nine,spades),tenOfClubs(ten,clubs),tenOfDiamonds(ten,diamonds),tenOfHearts(
			ten,hearts),tenOfSpades(ten,spades),jackOfClubs(jack,clubs),jackOfDiamonds(jack,diamonds),jackOfHearts(jack,hearts),jackOfSpades(jack,spades),queenOfClubs(
			queen,clubs),queenOfDiamonds(queen,diamonds),queenOfHearts(queen,hearts),queenOfSpades(queen,spades),kingOfClubs(king,clubs),kingOfDiamonds(king,
			diamonds),kingOfHearts(king,hearts),kingOfSpades(king,spades),aceOfClubs(ace,clubs),aceOfDiamonds(ace,diamonds),aceOfHearts(ace,hearts),aceOfSpades(
			ace,spades);
	Card(Rank rank,Suit suit) {
		this.rank=rank;
		this.suit=suit;
	}
	public boolean isNatural() {
		return rank!=Rank.jokerRank&&suit!=Suit.jokerSuit;
	}
	public Rank rank() {
		return rank;
	}
	public Suit suit() {
		return suit;
	}
	public String toString() {
		if (rank.equals(Rank.jokerRank)||suit.equals(Suit.jokerSuit)) return "Joker";
		else return rank+" of "+suit+'s';
	}
	public String toCharacters() {
		return ""+rank().toCharacter()+suit().toCharacter();
	}
	public static String toString(final Card[] card) {
		String s=new String();
		for(int i=0;i<card.length;i++) {
			if (i>0) s+=", ";
			s+=card[i].toCharacters();
		}
		return s;
	}
	public static void replace(final Card[] cards,final int i,final Card card) {
		cards[i]=card;
	}
	public static Card instance(Rank rank,Suit suit) {
		return cards[4*rank.ordinal()+suit.ordinal()];
	}
	public static void main(String[] args) {
		System.out.println(values().length+" cards");
		System.out.println(naturals.size()+" natural");
		System.out.println(wildCards.size()+" wild cards");
	}
	private final Rank rank;
	private final Suit suit;
	private static final Card[] cards= {joker,joker,joker,joker,joker,aceLowOfClubs,aceLowOfDiamonds,aceLowOfHearts,aceLowOfSpades,dueceOfClubs,
			dueceOfDiamonds,dueceOfHearts,dueceOfSpades,treyOfClubs,treyOfDiamonds,treyOfHearts,treyOfSpades,fourOfClubs,fourOfDiamonds,fourOfHearts,
			fourOfSpades,fiveOfClubs,fiveOfDiamonds,fiveOfHearts,fiveOfSpades,sixOfClubs,sixOfDiamonds,sixOfHearts,sixOfSpades,sevenOfClubs,sevenOfDiamonds,
			sevenOfHearts,sevenOfSpades,eightOfClubs,eightOfDiamonds,eightOfHearts,eightOfSpades,nineOfClubs,nineOfDiamonds,nineOfHearts,nineOfSpades,
			tenOfClubs,tenOfDiamonds,tenOfHearts,tenOfSpades,jackOfClubs,jackOfDiamonds,jackOfHearts,jackOfSpades,queenOfClubs,queenOfDiamonds,queenOfHearts,
			queenOfSpades,kingOfClubs,kingOfDiamonds,kingOfHearts,kingOfSpades,aceOfClubs,aceOfDiamonds,aceOfHearts,aceOfSpades};
	// make these immutable!
	public static final EnumSet<Card> wildCards=EnumSet.noneOf(Card.class);
	static {
		wildCards.add(Card.joker);
		// maybe remove the high cards instead?
	}
	// make these immutable!
	public static final EnumSet<Card> naturals=EnumSet.allOf(Card.class);
	static {
		naturals.remove(Card.aceLowOfClubs);
		naturals.remove(Card.aceLowOfDiamonds);
		naturals.remove(Card.aceLowOfHearts);
		naturals.remove(Card.aceLowOfSpades);
		for(Card card:wildCards)
			naturals.remove(card);
	}
	
}