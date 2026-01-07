package poker;
import java.util.Arrays;
import lookup.OldLookup;
import equipment.*;
public class PokerHand extends Hand { // a five card poker hand
	public enum HighType {
		noPair('n',"No Pair",6199,7475),onePair('1',"One Pair",3339,6198),twoPair('2',"Two Pair",2481,3338),threeOfAKind('3',"Three of a Kind",1623,2480),straight('s',"Straight",1613,1622),flush('f',"Flush",336,1612),fullHouse('p',"Full House",180,335),fourOfAKind('4',"Four of a Kind",24,179),straightFlush('S',"Straight Flush",14,23),fiveOfAKind('5',"Five of a Kind",1,13);
		/*
		 * new HandRange('5',(short)1,(short)13), new HandRange('S',(short)14,(short)23), new HandRange('4',(short)24,(short)179), new HandRange('p',(short)180,(short)335), new HandRange('f',(short)336,(short)1612), new HandRange('s',(short)1613,(short)1622), new HandRange('3',(short)1623,(short)2480), new HandRange('2',(short)2481,(short)3338), new HandRange('1',(short)3339,(short)6198), new HandRange('n',(short)6199,(short)7475)};
		 */
		public static final PokerHand.HighType type(int handNumber) {
			if(!(1<=handNumber&&handNumber<=noPair.last))
				throw new RuntimeException("oops");
			assert 1<=handNumber&&handNumber<=noPair.last;
			for(HighType t:HighType.values())
				if(t.first<=handNumber&&handNumber<=t.last)
					return t;
			return null;
		}
		HighType(final char c,final String s,final int first,final int last) {
			this.c=c;
			this.s=s;
			this.first=(short)first;
			this.last=(short)last;
			this.firstSample=from(first);
			this.lastSample=from(last);
		}
		public short first() {
			return first;
		}
		public short last() {
			return last;
		}
		public PokerHand firstSample() {
			return firstSample;
		}
		public PokerHand lastSample() {
			return lastSample;
		}
		public String toString() {
			return s+" ("+last+"-"+first+")"+" "+"("+lastSample+" - "+firstSample+")";
		}
		public Character toCharacter() {
			return Character.valueOf(c);
		}
		public static HighType fromCharacter(char c) {
			for(HighType t:HighType.values())
				if(t.c==c)
					return t;
			return null;
		}
		public static HighType fromInteger(int i) {
			return values()[i];
		}
		private final char c;
		private final String s;
		private final short first,last;
		private final PokerHand firstSample,lastSample;
		
	}
	public PokerHand(final Card[] cards) {
		super(cards);
		if(cards.length!=5)
			throw new RuntimeException("a poker hand must have 5 cards!");
		for(Card c:cards)
			assert c.isNatural();
		handNumber=OldLookup.lookup(cards);
	}
	public static PokerHand from(int handNumber) {
		Card[] cards=OldLookup.inverseLookup(handNumber);
		Card[] canonical=OldLookup.toCanonicalForm(cards);
		return new PokerHand(canonical);
	}
	public PokerHand(final Card card1,final Card card2,final Card card3,final Card card4,final Card card5) {
		this(toArray(card1,card2,card3,card4,card5));
	}
	public static void main(String[] arguments) {
		for(HighType highType:HighType.values())
			System.out.println(highType);
			
	}
	public final int handNumber;

}
