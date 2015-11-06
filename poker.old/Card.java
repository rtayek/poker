// this is the old one
package poker;
import java.util.*;
public class Card {
	public enum Rank {
		joker(new Character('*'),"Joker"),aceLow(new Character('a'),"Ace(low)"),deuce(new Character('2'),"Deuce"),trey(new Character('3'),"Trey"),four(new Character('4'),"Four"),five(new Character('5'),"Five"),six(new Character('6'),"Six"),seven(new Character('7'),"Seven"),eight(new Character('8'),"Eight"),nine(new Character('9'),"Nine"),ten(new Character('T'),"Ten"),jack(new Character('J'),"Jack"),queen(new Character('Q'),"Queen"),king(new Character('K'),"King"),aceHigh(new Character('A'),"Ace");
		// joker((byte)0,new Character('*'),"Joker"),aceLow((byte)1,new Character('a'),"Ace(low)"),deuce((byte)2,new Character('2'),"Deuce"),trey((byte)3,new Character('3'),"Trey"),four((byte)4,new Character('4'),"Four"),five((byte)5,new Character('5'),"Five"),six((byte)6,new Character('6'),"Six"),seven((byte)7,new Character('7'),"Seven"),eight((byte)8,new Character('8'),"Eight"),nine((byte)9,new Character('9'),"Nine"),ten((byte)10,new Character('T'),"Ten"),jack((byte)11,new Character('J'),"Jack"),queen((byte)12,new Character('Q'),"Queen"),king((byte)13,new Character('K'),"King"),aceHigh((byte)14,new Character('A'),"Ace");
		Rank(final Character c,final String s) {
			// this.b_=b;
			this.c=c;
			this.s=s;
		}
			static Card[] keyInverse(long key) { // might be for other lookup
			int i,n;
			long k;
			boolean isSuited=(key&1)!=0;
			key>>=1;
			for(n=0,k=key;(k&0xf)!=0;n++,k>>=4);
			final Card[] card=new Card[n];
			for(i=0,k=key;i<n;i++,k>>=4) {
				Rank rank=fromInt((int)k&0xf);
				if(rank==Rank.aceLow)
					rank=Rank.aceHigh;
				card[n-i-1]=Card.instance(rank,!isSuited&i==0?Suit.diamonds:Suit.clubs);
			}
			return card;
		}
		public static Rank[] toCanonicalForm(Rank[] rank) {
			for(int i=0;i<rank.length;i++)
				if(rank[i]==Rank.aceLow)
					rank[i]=Rank.aceHigh;
			for(int i=0;i<rank.length-1;i++)
				for(int j=i+1;j<rank.length;j++)
					if(rank[i].ordinal()<rank[j].ordinal()) {
						Rank temp=rank[i];
						rank[i]=rank[j];
						rank[j]=temp;
					}
			return rank;
		}
		public static int toNumber(final Card.Rank[] rank) {
			assert rank.length==5;
			int n=0;
			for(int i=0;i<5;i++)
				n+=n*13+rank[i].ordinal()-2;
			return n;
		}
		public static Rank fromInt(int n) {
			for(Rank r:Rank.values())
				if(r.ordinal()==n)
					return r;
			return null;
		}
		public static Rank fromCharacter(char c) {
			for(Rank r:Rank.values())
				if(r.c==c)
					return r;
			return null;
		}
		public static Rank[] fromCharacters(final String cards) {
			final Card.Rank[] rank=new Card.Rank[5];
			for(int i=0;i<5;i++)
				rank[i]=Card.Rank.fromCharacter(cards.charAt(i));
			return rank;
		}
		public Character toCharacter() {
			return c;
		}
		public String toString() {
			return s;
		}
		public static String toString(final Rank[] rank) {
			String s="";
			for(Rank r:rank)
				s+=r.toCharacter();
			return s;
		}
		// private final byte b_;
		private final Character c;
		private final String s;
	}
	public enum Suit {
		joker(new Character('*'),"Joker"),clubs(new Character('c'/* try character for a real dimond? */),"Club"),diamonds(new Character('d'),"Diamond"),hearts(new Character('h'),"Heart"),spades(new Character('s'),"Spade");
		Suit(final Character c,final String s) {
			// this.b_=b;
			this.c=c;
			this.s=s;
		}
		public static Suit fromCharacter(char c) {
			for(Suit s:Suit.values())
				if(s.c==c)
					return s;
			return null;
		}
		public Character toCharacter() {
			return c;
		}
		public String toString() {
			return s;
		}
		public static String toString(final Suit[] suit) {
			String s_="";
			for(Suit s:suit)
				s_+=s.toCharacter();
			return s_;
		}
		public static boolean areSuited(final Suit[] suit) {
			final int n=suit.length;
			for(int i=0;i<n-1;i++)
				if(suit[i]!=suit[i+1])
					return false;
			return true;
		}
		// private final byte b_;
		private final Character c;
		private final String s;
	}
	private Card(Rank rank,Suit suit) {
		this.rank=rank;
		this.suit=suit;
	}
	public static boolean areSuited(final Card[] card) {
		final int n=card.length;
		for(int i=0;i<n-1;i++)
			if(card[i].suit()!=card[i+1].suit()) // may be null!
				return false;
		return true;
	}
	public static boolean areNatural(final Card[] card) {
		for(Card c:card)
			if(!c.isNatural())
				return false;
		return true;
	}
	public static Rank[] ranks(final Card[] card) {
		final Rank[] r=new Rank[card.length];
		for(int i=0;i<card.length;i++)
			r[i]=card[i].rank();
		return r;
	}
	public static Card.Suit[] suits(final Card[] card) {
		final Card.Suit[] suits=new Card.Suit[card.length];
		for(int i=0;i<card.length;i++)
			suits[i]=card[i].suit();
		return suits;
	}
	static Card[] toCanonicalForm(Rank[] rank,boolean areSuited) {
		Rank.toCanonicalForm(rank);
		final Card[] c=new Card[rank.length];
		for(int i=0;i<rank.length;i++)
			c[i]=instance(rank[i],!areSuited&i==0?Suit.diamonds:Suit.clubs);
		return c;
	}
	static Card[] toCanonicalForm(Card[] card) {
		return toCanonicalForm(ranks(card),areSuited(card));
	}
	static void replace(final Card[] cards,final int i,final Card card) {
		cards[i]=card;
	}
	public boolean isNatural() {
		return rank!=Rank.joker&&suit!=Suit.joker;
	}
	public Rank rank() {
		return rank;
	}
	public Suit suit() {
		return suit;
	}
	public String toString() {
		return rank+" of "+suit+'s';
	}
	public static String toString(final Card[] card) {
		String s=new String();
		for(int i=0;i<card.length;i++) {
			if(i>0)
				s+=", ";
			s+=card[i].toCharacters();
		}
		return s;
	}
	public String toCharacters() {
		return ""+rank().toCharacter()+suit().toCharacter();
	}
	public static Card instance(Rank rank,Suit suit) { // some hack for speed
		return element[4*rank.ordinal()+suit.ordinal()];
	}
	public static Card[] create(int standards,int jokers) {
		int cards=0;
		Card card[]=new Card[standards*52+jokers];
		for(int i=0;i<standards;i++)
			for(Suit suit:EnumSet.range(Suit.clubs,Suit.spades))
				for(Rank rank:EnumSet.range(Rank.deuce,Rank.aceHigh))
					card[cards++]=instance(rank,suit);
		for(int i=0;i<jokers;i++)
			card[cards++]=instance(Rank.joker,Suit.joker);
		return card;
	}
	public static ArrayList<Card> newDeck() {
		return new ArrayList<Card>(deck); // Return copy of prototype deck
	}
	private final Rank rank;
	private final Suit suit;
	static final Card joker=new Card(Rank.joker,Suit.joker);
	static final Card aceLowOfClubs=new Card(Rank.aceLow,Suit.clubs);
	static final Card aceLowOfDiamonds=new Card(Rank.aceLow,Suit.diamonds);
	static final Card aceLowOfHearts=new Card(Rank.aceLow,Suit.hearts);
	static final Card aceLowOfSpades=new Card(Rank.aceLow,Suit.spades);
	static final Card dueceOfClubs=new Card(Rank.deuce,Suit.clubs);
	static final Card dueceOfDiamonds=new Card(Rank.deuce,Suit.diamonds);
	static final Card dueceOfHearts=new Card(Rank.deuce,Suit.hearts);
	static final Card dueceOfSpades=new Card(Rank.deuce,Suit.spades);
	static final Card treyOfClubs=new Card(Rank.trey,Suit.clubs);
	static final Card treyOfDiamonds=new Card(Rank.trey,Suit.diamonds);
	static final Card treyOfHearts=new Card(Rank.trey,Suit.hearts);
	static final Card treyOfSpades=new Card(Rank.trey,Suit.spades);
	static final Card fourOfClubs=new Card(Rank.four,Suit.clubs);
	static final Card fourOfDiamonds=new Card(Rank.four,Suit.diamonds);
	static final Card fourOfHearts=new Card(Rank.four,Suit.hearts);
	static final Card fourOfSpades=new Card(Rank.four,Suit.spades);
	static final Card fiveOfClubs=new Card(Rank.five,Suit.clubs);
	static final Card fiveOfDiamonds=new Card(Rank.five,Suit.diamonds);
	static final Card fiveOfHearts=new Card(Rank.five,Suit.hearts);
	static final Card fiveOfSpades=new Card(Rank.five,Suit.spades);
	static final Card sixOfClubs=new Card(Rank.six,Suit.clubs);
	static final Card sixOfDiamonds=new Card(Rank.six,Suit.diamonds);
	static final Card sixOfHearts=new Card(Rank.six,Suit.hearts);
	static final Card sixOfSpades=new Card(Rank.six,Suit.spades);
	static final Card sevenOfClubs=new Card(Rank.seven,Suit.clubs);
	static final Card sevenOfDiamonds=new Card(Rank.seven,Suit.diamonds);
	static final Card sevenOfHearts=new Card(Rank.seven,Suit.hearts);
	static final Card sevenOfSpades=new Card(Rank.seven,Suit.spades);
	static final Card eightOfClubs=new Card(Rank.eight,Suit.clubs);
	static final Card eightOfDiamonds=new Card(Rank.eight,Suit.diamonds);
	static final Card eightOfHearts=new Card(Rank.eight,Suit.hearts);
	static final Card eightOfSpades=new Card(Rank.eight,Suit.spades);
	static final Card nineOfClubs=new Card(Rank.nine,Suit.clubs);
	static final Card nineOfDiamonds=new Card(Rank.nine,Suit.diamonds);
	static final Card nineOfHearts=new Card(Rank.nine,Suit.hearts);
	static final Card nineOfSpades=new Card(Rank.nine,Suit.spades);
	static final Card tenOfClubs=new Card(Rank.ten,Suit.clubs);
	static final Card tenOfDiamonds=new Card(Rank.ten,Suit.diamonds);
	static final Card tenOfHearts=new Card(Rank.ten,Suit.hearts);
	static final Card tenOfSpades=new Card(Rank.ten,Suit.spades);
	static final Card jackOfClubs=new Card(Rank.jack,Suit.clubs);
	static final Card jackOfDiamonds=new Card(Rank.jack,Suit.diamonds);
	static final Card jackOfHearts=new Card(Rank.jack,Suit.hearts);
	static final Card jackOfSpades=new Card(Rank.jack,Suit.spades);
	static final Card queenOfClubs=new Card(Rank.queen,Suit.clubs);
	static final Card queenOfDiamonds=new Card(Rank.queen,Suit.diamonds);
	static final Card queenOfHearts=new Card(Rank.queen,Suit.hearts);
	static final Card queenOfSpades=new Card(Rank.queen,Suit.spades);
	static final Card kingOfClubs=new Card(Rank.king,Suit.clubs);
	static final Card kingOfDiamonds=new Card(Rank.king,Suit.diamonds);
	static final Card kingOfHearts=new Card(Rank.king,Suit.hearts);
	static final Card kingOfSpades=new Card(Rank.king,Suit.spades);
	static final Card aceOfClubs=new Card(Rank.aceHigh,Suit.clubs);
	static final Card aceOfDiamonds=new Card(Rank.aceHigh,Suit.diamonds);
	static final Card aceOfHearts=new Card(Rank.aceHigh,Suit.hearts);
	static final Card aceOfSpades=new Card(Rank.aceHigh,Suit.spades);
	private static final Card element[]={joker,joker,joker,joker,joker,aceLowOfClubs,aceLowOfDiamonds,aceLowOfHearts,aceLowOfSpades,dueceOfClubs,dueceOfDiamonds,dueceOfHearts,dueceOfSpades,treyOfClubs,treyOfDiamonds,treyOfHearts,treyOfSpades,fourOfClubs,fourOfDiamonds,fourOfHearts,fourOfSpades,fiveOfClubs,fiveOfDiamonds,fiveOfHearts,fiveOfSpades,sixOfClubs,sixOfDiamonds,sixOfHearts,sixOfSpades,sevenOfClubs,sevenOfDiamonds,sevenOfHearts,sevenOfSpades,eightOfClubs,eightOfDiamonds,eightOfHearts,eightOfSpades,nineOfClubs,nineOfDiamonds,nineOfHearts,nineOfSpades,tenOfClubs,tenOfDiamonds,tenOfHearts,tenOfSpades,jackOfClubs,jackOfDiamonds,jackOfHearts,jackOfSpades,queenOfClubs,queenOfDiamonds,queenOfHearts,queenOfSpades,kingOfClubs,kingOfDiamonds,kingOfHearts,kingOfSpades,aceOfClubs,
			aceOfDiamonds,aceOfHearts,aceOfSpades};
	private static final List<Card> deck=new ArrayList<Card>();
	static {
		for(Suit suit:EnumSet.range(Suit.clubs,Suit.spades))
			for(Rank rank:EnumSet.range(Rank.deuce,Rank.aceHigh))
				deck.add(new Card(rank,suit));
		deck.add(new Card(Rank.joker,Suit.joker));
	}
}