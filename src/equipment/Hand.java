package equipment;
public class Hand extends Cards {
	public Hand(Card[] cards) {
		super(cards);
	}
	public Hand(Card card1,Card card2) {
		// sort?
		super(new Card[] {card1,card2});
	}
	public Hand(Card card1,Card card2,Card card3,Card card4,Card card5) {
		// sort?
		super(new Card[] {card1,card2});
	}
	public void replace(int i,Card card) {
		assert card.isNatural();
		super.replace(i,card);
	}
	public Rank[] ranks() {
		return super.ranks();
	}
	public Suit[] suits() {
		return super.suits();
	}
	public boolean isNatural() {
		return true/* wtf? */;
	}
	public static void main(String[] arguments) {}
}