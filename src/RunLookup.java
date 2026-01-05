import java.util.*;
import static poker.Constants.*;
import equipment.*;
import lookup.OldLookup;
import poker.*;
public class RunLookup {
	static void shuffle() {
		final Random random=new Random();
		for(int i=51;i>0;i--) {
			int r=random.nextInt();
			if(r<0) // really didn't expect this. bug?
				r=-r;
			r%=i;
			{
				Card temp=deck[i];
				deck[i]=deck[r];
				deck[r]=temp;
			}
		}
		nextCard=0;
	}
	void testAll() {
		int n=testOne(cards);
		for(int i=0;i<permutation.length;i++) {
			int[] p=permutation[i];
			for(int j=0;j<5;j++)
				cards2[j]=cards[p[j]];
			if(n!=testOne(cards2))
				System.err.println("testAll fails");
		}
	}
	int testOne(Card[] cards) {
		Hand hand=new PokerHand(cards);
		int highHandNumber;
		if((highHandNumber=OldLookup.lookup(cards))!=0) {
			if(verbose||hands%m==0) {
				long now=System.currentTimeMillis();
				PokerHand.HighType type=PokerHand.HighType.type(highHandNumber);
				String s=hand.toCharacters()+" is "+highHandNumber+" "+" "+type;
				if(hands!=0)
					s+=" "+(now-time)+" ms. "+(m*1000./(now-time))+" hands/second";
				System.err.println(hands+" "+s);
				time=now;
			}
		} else System.err.println("testOne fails");
		hands++;
		return highHandNumber;
	}
	void runShort() {
		int n=52,n1=n-1,n2=n-2,n3=n-3,n4=n-4;
		System.err.println("testing "+naturalFiveCardCombinations+" hands");
		for(hands=0,time=0,c1=0;c1<n4;c1++) {
			cards[0]=deck[c1];
			for(c2=c1+1;c2<n3;c2++) {
				cards[1]=deck[c2];
				for(c3=c2+1;c3<n2;c3++) {
					cards[2]=deck[c3];
					for(c4=c3+1;c4<n1;c4++) {
						cards[3]=deck[c4];
						for(c5=c4+1;c5<n;c5++) {
							cards[4]=deck[c5];
							// testAll();
							testOne(cards);
						}
					}
				}
			}
		}
		System.err.println("verify "+naturalFiveCardCombinations+"="+hands);
		if(hands==naturalFiveCardCombinations)
			System.err.println("verified");
		else System.err.println("runShort fails");
	}
	void runLong() {
		int n=52;
		int total=52*51*50*49*48;
		System.err.println("testing "+total+" hands");
		for(hands=0,time=0,c1=0;c1<n;c1++) {
			cards[0]=deck[c1];
			for(c2=0;c2<n;c2++) {
				cards[1]=deck[c2];
				for(c3=0;c3<n;c3++) {
					cards[2]=deck[c3];
					for(c4=0;c4<n;c4++) {
						cards[3]=deck[c4];
						for(c5=0;c5<n;c5++)
							if(c1!=c2&&c1!=c3&&c1!=c4&&c1!=c5&&c2!=c3&&c2!=c4&&c2!=c5&&c3!=c4&&c3!=c5&&c4!=c5) {
								cards[4]=deck[c5];
								testOne(cards);
							}
					}
				}
			}
		}
		System.err.println("verify "+total+"="+hands);
		if(total==hands)
			System.err.println("verified");
		else System.err.println("fail");
	}
	void run() {
		System.err.println("run");
		List<int[]> v=Cards.permutations(5);
		permutation=new int[v.size()][];
		for(int i=0;i<v.size();i++)
			permutation[i]=(int[])v.get(i);
		v=null;
		if(true)
			runLong();
		else runShort();
	}
	static int[][] permutation;
	static final int m=100000000;
	static long time;
	static int hands;
	static boolean verbose=false;
	static int c1,c2,c3,c4,c5;
	static Rank[] ranks;
	static boolean isSuited;
	static short highHandNumber,lowHandNumber;
	static int nextCard;
	static final Card[] deck=new Deck().cards();
	static final Card[] cards=new Card[5],cards2=new Card[5];
	public static void main(String[] arg) {
		RunLookup runLookup=new RunLookup();
		while(true)
			runLookup.run();
	}
}
