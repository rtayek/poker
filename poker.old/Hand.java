package poker;
public class Hand extends Cards {
	public enum HighType {
		noPair((byte)0,'n',"No Pair",(short)6199,(short)7475),onePair((byte)1,'1',"One Pair",(short)3339,(short)6198),twoPair((byte)2,'2',"Two Pair",(short)2481,(short)3338),threeOfAKind((byte)3,'3',"Three of a Kind",(short)1623,(short)2480),straight((byte)4,'s',"Straight",(short)1613,(short)1622),flush((byte)5,'f',"Flush",(short)336,(short)1612),fullHouse((byte)6,'p',"Full House",(short)180,(short)335),fourOfAKind((byte)7,'4',"Four of a Kind",(short)24,(short)179),straightFlush((byte)8,'S',"Straight Flush",(short)14,(short)23),fiveOfAKind((byte)9,'5',"Five of a Kind",(short)1,(short)13);
		/*
		 * new HandRange('5',(short)1,(short)13), new HandRange('S',(short)14,(short)23), new HandRange('4',(short)24,(short)179), new HandRange('p',(short)180,(short)335), new HandRange('f',(short)336,(short)1612), new HandRange('s',(short)1613,(short)1622), new HandRange('3',(short)1623,(short)2480), new HandRange('2',(short)2481,(short)3338), new HandRange('1',(short)3339,(short)6198), new HandRange('n',(short)6199,(short)7475)};
		 */
		public static final Hand.HighType type(int handNumber) {
			assert 1<=handNumber&&handNumber<=noPair.last;
			for(HighType t:HighType.values())
				if(t.first<=handNumber&&handNumber<=t.last)
					return t;
			return null;
		}
		HighType(final byte b,final char c,final String s,final short first,final short last) {
			this.b=b;
			this.c=c;
			this.s=s;
			this.first=first;
			this.last=last;
		}
		public short first() {
			return first;
		}
		public short last() {
			return last;
		}
		public String toString() {
			return s;
		}
		public Character toCharacter() {
			return new Character(c);
		}
		public int toInt() {
			return b;
		}
		public static HighType fromCharacter(char c) {
			for(HighType t:HighType.values())
				if(t.c==c)
					return t;
			return null;
		}
		public static HighType fromInteger(int i) {
			for(HighType t:HighType.values())
				if(t.b==i)
					return t;
			return null;
		}
		private final byte b;
		private final char c;
		private final String s;
		private final short first,last;
	}
	public Hand(final Card[] cards) {
		super(cards);
		for(Card c:cards)
			assert c.isNatural();
	}
	Hand(final Card card1,final Card card2,final Card card3,final Card card4,final Card card5) {
		this(toArray(card1,card2,card3,card4,card5));
	}
	public void replace(int i,Card card) {
		super.replace(i,card);
		assert card.isNatural();
	}
	public Card.Rank[] ranks() {
		return super.ranks();
	}
	public Card.Suit[] suits() {
		return super.suits();
	}
	public boolean isSuited() {
		return areSuited();
	}
	public boolean isNatural() {
		return true/* wtf? */;
	}
}
