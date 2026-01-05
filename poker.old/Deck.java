package poker;
import java.util.Random;
public class Deck extends Cards {
	public final int stub() {
		return size()-nextCard;
	}
	public void shuffle() {
		super.shuffle();
		nextCard=0;
	}
	public final Card draw() {
		return card(nextCard++);
	}
	public final Card[] draw(final int n) {
		final Card[] cards=new Card[n];
		for(int i=0;i<n;i++)
			cards[i]=draw();
		return cards;
	}
	public Card[] cards() {
		return super.cards();
	}
	public Hand randomHand(int n) {
		Card[] cards=new Card[n];
		Random random=new Random();
		int l=size();
		for(int i=0;i<n;i++) {
			int r=random.nextInt();
			cards[i]=card(r%(r>0?l:-l));
		}
		return new Hand(cards);
	}
	public Deck() {
		this(Card.create(1,0));
	}
	public Deck(int standards,int jokers) {
		this(Card.create(standards,jokers));
	}
	Deck(Card[] cards) {
		super(cards);
	}
	private int nextCard;
}