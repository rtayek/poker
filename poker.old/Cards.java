package poker;
import java.util.*;
public abstract class Cards {
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
	Cards(final Card[] cards) {
		this.cards=cards;
	}
	public final Card card(int n) {
		return cards[n];
	}
	boolean areSuited() {
		return Card.areSuited(cards);
	}
	public boolean areNatural() {
		return Card.areNatural(cards);
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
	protected Card[] cards() {
		return cards;
	}
	void replace(int i,Card card) {
		Card.replace(cards,i,card);
	}
	void shuffle() {
		final Random random=new Random();
		final int n=cards.length;
		for(int i=n-1;i>0;i--) {
			int r=random.nextInt();
			int k=r%(r>0?i:-r);
			{
				Card temp=cards[i];
				cards[i]=cards[k];
				cards[k]=temp;
			}
		}
	}
	public static final long f(final int n) { // factorial
		long i,p;
		if(n<=1) p=1;
		else for(p=n,i=2;i<=n-1;i++)
			p=p*i;
		return (p);
	}
	public static final int c(final int n,final int r) { // binomial coefficient
		long i,p;
		if(r<0||n<0||r>n) p=0;
		else if(r==0) p=1;
		else if(r>n-r) p=c(n,n-r);
		else {
			for(p=1,i=n;i>=n-r+1;i--)
				p=p*i;
			p=p/f(r);
		}
		return ((int)p);
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
	public String toString() {
		return Card.toString(cards);
	}
	Card.Rank[] ranks() {
		return Card.ranks(cards);
	}
	Card.Suit[] suits() {
		return Card.suits(cards);
	}
	public static final int naturalHands=c(52,5),allHands=c(53,5);
	private final Card[] cards;
}