package sample;
import java.util.*;
enum Rank {
	joker(new Character('*'),"Joker"),aceLow(new Character('a'),"Ace(low)"),deuce(new Character('2'),"Deuce"),trey(new Character('3'),"Trey"),four(new Character('4'),"Four"),five(new Character('5'),"Five"),six(new Character('6'),"Six"),seven(new Character('7'),"Seven"),eight(new Character('8'),"Eight"),nine(new Character('9'),"Nine"),ten(new Character('T'),"Ten"),jack(new Character('J'),"Jack"),queen(new Character('Q'),"Queen"),king(new Character('K'),"King"),aceHigh(new Character('A'),"Ace");
	Rank(final Character c,final String s) {
		this.c=c;
		this.s=s;
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
		final Rank[] rank=new Rank[5];
		for(int i=0;i<5;i++)
			rank[i]=Rank.fromCharacter(cards.charAt(i));
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
	final Character c;
	final String s;
}
enum Suit {
	joker(new Character('*'),"Joker"),clubs(new Character('c'/* try character for a real dimond? */),"Club"),diamonds(new Character('d'),"Diamond"),hearts(new Character('h'),"Heart"),spades(new Character('s'),"Spade");
	Suit(final Character c,final String s) {
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
	final Character c;
	final String s;
}
enum Card {
	joker(Rank.joker,Suit.joker),aceLowOfClubs(Rank.aceLow,Suit.clubs),aceLowOfDiamonds(Rank.aceLow,Suit.diamonds),aceLowOfHearts(Rank.aceLow,Suit.hearts),aceLowOfSpades(Rank.aceLow,Suit.spades),dueceOfClubs(Rank.deuce,Suit.clubs),dueceOfDiamonds(Rank.deuce,Suit.diamonds),dueceOfHearts(Rank.deuce,Suit.hearts),dueceOfSpades(Rank.deuce,Suit.spades),treyOfClubs(Rank.trey,Suit.clubs),treyOfDiamonds(Rank.trey,Suit.diamonds),treyOfHearts(Rank.trey,Suit.hearts),treyOfSpades(Rank.trey,Suit.spades),fourOfClubs(Rank.four,Suit.clubs),fourOfDiamonds(Rank.four,Suit.diamonds),fourOfHearts(Rank.four,Suit.hearts),fourOfSpades(Rank.four,Suit.spades),fiveOfClubs(Rank.five,Suit.clubs),fiveOfDiamonds(Rank.five,Suit.diamonds),fiveOfHearts(Rank.five,Suit.hearts),fiveOfSpades(Rank.five,Suit.spades),sixOfClubs(
			Rank.six,Suit.clubs),sixOfDiamonds(Rank.six,Suit.diamonds),sixOfHearts(Rank.six,Suit.hearts),sixOfSpades(Rank.six,Suit.spades),sevenOfClubs(Rank.seven,Suit.clubs),sevenOfDiamonds(Rank.seven,Suit.diamonds),sevenOfHearts(Rank.seven,Suit.hearts),sevenOfSpades(Rank.seven,Suit.spades),eightOfClubs(Rank.eight,Suit.clubs),eightOfDiamonds(Rank.eight,Suit.diamonds),eightOfHearts(Rank.eight,Suit.hearts),eightOfSpades(Rank.eight,Suit.spades),nineOfClubs(Rank.nine,Suit.clubs),nineOfDiamonds(Rank.nine,Suit.diamonds),nineOfHearts(Rank.nine,Suit.hearts),nineOfSpades(Rank.nine,Suit.spades),tenOfClubs(Rank.ten,Suit.clubs),tenOfDiamonds(Rank.ten,Suit.diamonds),tenOfHearts(Rank.ten,Suit.hearts),tenOfSpades(Rank.ten,Suit.spades),jackOfClubs(Rank.jack,Suit.clubs),jackOfDiamonds(Rank.jack,
			Suit.diamonds),jackOfHearts(Rank.jack,Suit.hearts),jackOfSpades(Rank.jack,Suit.spades),queenOfClubs(Rank.queen,Suit.clubs),queenOfDiamonds(Rank.queen,Suit.diamonds),queenOfHearts(Rank.queen,Suit.hearts),queenOfSpades(Rank.queen,Suit.spades),kingOfClubs(Rank.king,Suit.clubs),kingOfDiamonds(Rank.king,Suit.diamonds),kingOfHearts(Rank.king,Suit.hearts),kingOfSpades(Rank.king,Suit.spades),aceOfClubs(Rank.aceHigh,Suit.clubs),aceOfDiamonds(Rank.aceHigh,Suit.diamonds),aceOfHearts(Rank.aceHigh,Suit.hearts),aceOfSpades(Rank.aceHigh,Suit.spades);
	private Card(Rank rank,Suit suit) {
		this.rank=rank;
		this.suit=suit;
	}
	public Rank rank() {
		return rank;
	}
	public Suit suit() {
		return suit;
	}
	public String toString() {
		if(rank.equals(Rank.joker)||suit.equals(Suit.joker))
			return "Joker";
		else return rank+" of "+suit+'s';
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
	public boolean isNatural() {
		return rank!=Rank.joker&&suit!=Suit.joker;
	}
	public static Card instance(Rank rank,Suit suit) { // some hack for speed
		final int n=4*rank.ordinal()+suit.ordinal();
		// System.out.println(rank+" "+suit+" "+n);
		return element[4*rank.ordinal()+suit.ordinal()];
	}
	public static void replace(final Card[] cards,final int i,final Card card) {
		cards[i]=card;
	}
	static Card[] create(int standards,int jokers) {
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
	private final Rank rank;
	private final Suit suit;
	private static final Card element[]={joker,joker,joker,joker,joker,aceLowOfClubs,aceLowOfDiamonds,aceLowOfHearts,aceLowOfSpades,dueceOfClubs,dueceOfDiamonds,dueceOfHearts,dueceOfSpades,treyOfClubs,treyOfDiamonds,treyOfHearts,treyOfSpades,fourOfClubs,fourOfDiamonds,fourOfHearts,fourOfSpades,fiveOfClubs,fiveOfDiamonds,fiveOfHearts,fiveOfSpades,sixOfClubs,sixOfDiamonds,sixOfHearts,sixOfSpades,sevenOfClubs,sevenOfDiamonds,sevenOfHearts,sevenOfSpades,eightOfClubs,eightOfDiamonds,eightOfHearts,eightOfSpades,nineOfClubs,nineOfDiamonds,nineOfHearts,nineOfSpades,tenOfClubs,tenOfDiamonds,tenOfHearts,tenOfSpades,jackOfClubs,jackOfDiamonds,jackOfHearts,jackOfSpades,queenOfClubs,queenOfDiamonds,queenOfHearts,queenOfSpades,kingOfClubs,kingOfDiamonds,kingOfHearts,kingOfSpades,aceOfClubs,
			aceOfDiamonds,aceOfHearts,aceOfSpades};
}
abstract class Cards {
	public Cards(final Card[] cards) {
		this.cards=cards;
	}
	public final Card card(int n) {
		return cards[n];
	}
	public boolean areSuited() {
		return areSuited(cards);
	}
	public boolean areNatural() {
		return areNatural(cards);
	}
	public String toCharacters() {
		String s=new String();
		for(Card c:cards)
			s+=c.toCharacters();
		return s;
	}
	public int size() {
		return cards.length;
	}
	public Card[] cards() {
		return cards;
	}
	protected void replace(int i,Card card) {
		Card.replace(cards,i,card);
	}
	public void shuffle() {
		final Random random=new Random();
		final int n=cards.length;
		Card temp;
		for(int i=n-1;i>0;i--) {
			int r=random.nextInt(i);
			temp=cards[i];
			cards[i]=cards[r];
			cards[r]=temp;
		}
	}
	public Rank[] ranks() {
		return Cards.ranks(cards);
	}
	public Suit[] suits() {
		return Cards.suits(cards);
	}
	public String toString() {
		return Card.toString(cards);
	}
	protected static Card[] toArray(final Card card1,final Card card2,final Card card3,final Card card4,final Card card5) {
		final Card card[]=new Card[5];
		card[0]=card1;
		card[1]=card2;
		card[2]=card3;
		card[3]=card4;
		card[4]=card5;
		return card;
	}
	static Card[] toArray(final Card card1,final Card card2,final Card card3,final Card card4,final Card card5,final Card card6,final Card card7) {
		final Card card[]=new Card[7];
		card[0]=card1;
		card[1]=card2;
		card[2]=card3;
		card[3]=card4;
		card[4]=card5;
		card[6]=card6;
		card[7]=card7;
		return card;
	}
	public static Suit[] suits(final Card[] card) {
		final int n=card.length;
		final Suit[] suits=new Suit[n];
		for(int i=0;i<n;i++)
			suits[i]=card[i].suit();
		return suits;
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
		final Rank[] rank=new Rank[card.length];
		for(int i=0;i<card.length;i++)
			if(card[i]!=null)
				rank[i]=card[i].rank();
			else throw new RuntimeException("card is null!");
		return rank;
	}
	public static final long f(final int n) { // factorial
		long i,p;
		if(n<=1)
			p=1;
		else for(p=n,i=2;i<=n-1;i++)
			p=p*i;
		return (p);
	}
	public static final int c(final int n,final int r) { // binomial coefficient
		long i,p;
		if(r<0||n<0||r>n)
			p=0;
		else if(r==0)
			p=1;
		else if(r>n-r)
			p=c(n,n-r);
		else {
			for(p=1,i=n;i>=n-r+1;i--)
				p=p*i;
			p=p/f(r);
		}
		return (int)p;
	}
	public static List<int[]> permutations(int n) {
		final List<int[]> v=new ArrayList<int[]>();
		if(n<=1) {
			int[] a=new int[1];
			a[0]=0;
			v.add(a);
		} else {
			final List<int[]> v1=permutations(n-1);
			int nm1f=v1.size();
			for(int i=0;i<nm1f;i++) {
				int[] a=(int[])v1.get(i);
				for(int j=0;j<n;j++) { // copy a, inserting n at a2[j]
					int[] a2=new int[n];
					for(int k=0;k<j;k++)
						a2[k]=a[k];
					a2[j]=n-1;
					for(int k=j;k<n-1;k++)
						a2[k+1]=a[k];
					v.add(a2);
				}
			}
		}
		return v;
	}
	private final Card[] cards;
}
class Deck extends Cards {
	public Deck() {
		this(create(1,0));
	}
	public Deck(int standards,int jokers) {
		this(create(standards,jokers));
	}
	Deck(Card[] cards) {
		super(cards);
	}
	public final int stubLength() {
		return size()-nextCard;
	}
	public void shuffle() {
		super.shuffle();
		nextCard=0;
	}
	public final Card draw() {
		return card(nextCard++);
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
	public Card[] cards() {
		return super.cards();
	}
	public static Card[] create(int standards,int jokers) {
		int cards=0;
		Card card[]=new Card[standards*52+jokers];
		for(int i=0;i<standards;i++)
			for(Suit suit:EnumSet.range(Suit.clubs,Suit.spades))
				for(Rank rank:EnumSet.range(Rank.deuce,Rank.aceHigh))
					card[cards++]=Card.instance(rank,suit);
		for(int i=0;i<jokers;i++)
			card[cards++]=Card.instance(Rank.joker,Suit.joker);
		return card;
	}
	static boolean isDeckSuited(Cards c) { // suit order may be wrong for this
		Card[] cards=c.cards();
		for(int i=0;i<Math.min(cards.length,deck.cards().length);i++)
			if(cards[i]!=deck.cards()[i])
				return false;
		return true;
	}
	static Deck deck=new Deck();
	private int nextCard;
}
public class Sample {
	public static void main(String[] arguments) {
		System.out.println(Arrays.asList(Rank.values()));
		System.out.println(Arrays.asList(Suit.values()));
		Deck deck=new Deck();
		System.out.println(Arrays.asList(deck.cards()));
		deck.shuffle();
		System.out.println(Arrays.asList(deck.cards()));
	}
}