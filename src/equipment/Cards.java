package equipment;
import java.util.*;
import static java.lang.Math.*;
public abstract class Cards {
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
	@Override public String toString() {
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
		final Card cards[]=new Card[7];
		cards[0]=card1;
		cards[1]=card2;
		cards[2]=card3;
		cards[3]=card4;
		cards[4]=card5;
		cards[5]=card6;
		cards[6]=card7;
		return cards;
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
		boolean rc=true;
		for(int i=0;i<n-1;i++)
			if(card[i].suit()!=card[i+1].suit()){ // may be null!
				rc=false;
				break;
			}
		return rc;
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
	public static final long c(final int n,final int r) { // binomial coefficient
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
		return (int)p; // why do we cast?
	}
	public static final double bernoulli(int x,int n,double p) {
		//System.out.println(x+" "+n+" "+p+" "+c(x,n)+" "+pow(p,x)+" "+pow(1-p,n-x));
		return c(n,x)*pow(p,x)*pow(1-p,n-x);
		
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
