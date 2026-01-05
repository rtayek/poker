package equipment;
import static equipment.Rank.ace;
import static equipment.Rank.deuce;
import static equipment.Suit.clubs;
import static equipment.Suit.spades;
import java.math.BigInteger;
import java.util.EnumSet;
public class Deck extends Cards {
	public Deck() {
		this(create(1,0));
	}
	public Deck(int standards,int jokers) {
		this(create(standards,jokers));
	}
	public /* for kuhn */ Deck(Card[] cards) {
		super(cards);
	}
	public final int stubLength() {
		return size()-nextCard;
	}
	public void shuffle() {
		super.shuffle();
		nextCard=0;
	}
	public void burn() {
		nextCard++;
		// add to burn list?
	}
	public final Card draw() {
		return card(++nextCard);
	}
	public void draw(Card[] cards) {
		for(int i=0;i<cards.length;i++)
			cards[i]=draw();
	}
	public final Card[] draw(final int n) {
		final Card[] cards=new Card[n];
		draw(cards);
		return cards;
	}
	/*
	public Card[] cards() {
		return super.cards();
	}
	*/
	public static Card[] create(int standards,int jokers) {
		int n=0;
		Card cards[]=new Card[standards*52+jokers];
		for(int i=0;i<standards;i++)
			for(Suit suit:EnumSet.range(clubs,spades))
				for(Rank rank:EnumSet.range(deuce,ace))
					cards[n++]=Card.instance(rank,suit);
		for(int i=0;i<jokers;i++)
			cards[n++]=Card.instance(Rank.jokerRank,Suit.jokerSuit);
		return cards;
	}
	static boolean isDeckSuited(Cards c) { // suit order may be wrong for this
		Card[] cards = c.cards();
		for (int i = 0; i < Math.min(cards.length, deck.cards().length); i++)
			if (cards[i] != deck.cards()[i]) return false;
		return true;
	}
	static Deck deck=new Deck();
	public static void main(String[] arguments) {
		Deck deck = new Deck();
		System.out.println(deck);
		System.out.println(isDeckSuited(deck));
		BigInteger i=BigInteger.ZERO;
		BigInteger m=BigInteger.valueOf(1000000000);
		for(boolean done=false;!done;i=i.add(BigInteger.ONE)) {
			if(i.mod(m).equals(BigInteger.ZERO))
				System.out.println(i);
			deck.shuffle();
			done=isDeckSuited(deck);
			//System.out.println(deck);
		}
		System.out.println(deck);
		System.out.println(isDeckSuited(deck));
		System.out.println(i);
	}
	private int nextCard;
}