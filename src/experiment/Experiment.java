package experiment;
import static equipment.Rank.*;
import static equipment.Suit.*;
import java.util.TreeMap;
import equipment.*;
import poker.*;
public final class Experiment {
	static int lookup(Card[] cards) {
		return lookup(cards[0],cards[1],cards[2],cards[3],cards[4]);
	}
	static int lookup(Card c1,Card c2,Card c3,Card c4,Card c5) {
		int r1,r2,r3,r4,r5;
		r1=c1.rank().ordinal()-2;
		r2=c2.rank().ordinal()-2;
		r3=c3.rank().ordinal()-2;
		r4=c4.rank().ordinal()-2;
		r5=c5.rank().ordinal()-2;
		boolean isSuited=c1.suit()==c2.suit()&&c1.suit()==c3.suit()&&c1.suit()==c4.suit()&&c1.suit()==c5.suit();
		int n=lookup(r1,r2,r3,r4,r5,isSuited);
		return n;
	}
	private static int lookup(int r1,int r2,int r3,int r4,int r5,boolean isSuited) {
		int t;
		if (r1<r2) {
			t=r1;
			r1=r2;
			r2=t;
		}
		if (r1<r3) {
			t=r1;
			r1=r3;
			r3=t;
		}
		if (r1<r4) {
			t=r1;
			r1=r4;
			r4=t;
		}
		if (r1<r5) {
			t=r1;
			r1=r5;
			r5=t;
		}
		if (r2<r3) {
			t=r2;
			r2=r3;
			r3=t;
		}
		if (r2<r4) {
			t=r2;
			r2=r4;
			r4=t;
		}
		if (r2<r5) {
			t=r2;
			r2=r5;
			r5=t;
		}
		if (r3<r4) {
			t=r3;
			r3=r4;
			r4=t;
		}
		if (r3<r5) {
			t=r3;
			r3=r5;
			r5=t;
		}
		if (r4<r5) {
			t=r4;
			r4=r5;
			r5=t;
		}
		boolean old=true;
		int n=-1,index=-1;
		if (isSuited) {
			if (old) index=b[r1][0]+b[r2][1]+b[r3][2]+b[r4][3]+b[r5][4];
			else index=b1[r1]+b2[r2]+b3[r3]+b4[r4]+b5[r5];
			if (index>=flush.length) {
				System.out.println(r1+" "+r2+" "+r3+" "+r4+" "+r5);
				System.out.println("badness: "+index+"/"+flush.length);
			}
			n=flush[index];
		} else {
			if(old) index=a[r1][0]+a[r2][1]+a[r3][2]+a[r4][3]+a[r5][4];
			else index=a1[r1]+a2[r2]+a3[r3]+a4[r4]+a5[r5];
			if (index>=other.length) {
				System.out.println(r1+" "+r2+" "+r3+" "+r4+" "+r5);
				System.out.println("badness: "+index+"/"+other.length);
			}
			n=other[index];
		}
		return n;
	}
	public static void run() {
		int n=0;
		long t0=System.nanoTime();
		Card[] cards=new Card[5];
		for(Card c1:Card.naturals)
			for(Card c2:Card.naturals)
				if (!c2.equals(c1)) for(Card c3:Card.naturals)
					if (!c3.equals(c2)&&!c3.equals(c1)) for(Card c4:Card.naturals)
						if (!c4.equals(c3)&&!c4.equals(c2)&&!c4.equals(c1)) for(Card c5:Card.naturals)
							if (!c5.equals(c4)&&!c5.equals(c3)&&!c5.equals(c2)&&!c5.equals(c1)) {
								lookup(c1,c2,c3,c4,c5);
								n++;
							}
		long dt=System.nanoTime()-t0;
		double rate=n*(double)oneBillion/(double)dt;
		double rate2=n/(double)dt*oneBillion;
		System.out.println(n+" hands in "+dt/oneBillion+" seconds. ");
		System.out.println(Cards.c(52,5)*Cards.f(5)==n);
		System.out.println(rate+" hands/sec.");
		System.out.println(rate2+" hands/sec.");
		System.out.println(dt/n+" ns./hand");
	}
	private static void generate(String string,int[][] x) {
		for(int j=0;j<x[0].length;j++) {
			System.out.print("static final short[] "+string+(j+1)+"=new short[]{");
			for(int i=0;i<x.length;i++)
				System.out.print(x[i][j]+",");
			System.out.println("};");
		}
	}
	static void make() {
		generate("a",a);
		generate("b",b);
	}
	public static void main(String[] args) {
		run();
		//make();
	}
	TreeMap<Integer,Integer> map=new TreeMap<>();
	static final int oneBillion=1_000_000_000;
	static final boolean alone=false;
	private static final short[] flush=alone?new short[1287]:Data.flush;
	private static final short[] other=alone?new short[6188]:Data.other;
	private static final int[][] a=alone?new int[13][5]:Data.a,b=alone?new int[13][5]:Data.b;
	static final short[] a1=alone?new short[13]:Data.a1;
	static final short[] a2=alone?new short[13]:Data.a2;
	static final short[] a3=alone?new short[13]:Data.a3;
	static final short[] a4=alone?new short[13]:Data.a4;
	static final short[] a5=alone?new short[13]:Data.a5;
	static final short[] b1=alone?new short[13]:Data.b1;
	static final short[] b2=alone?new short[13]:Data.b2;
	static final short[] b3=alone?new short[13]:Data.b3;
	static final short[] b4=alone?new short[13]:Data.b4;
	static final short[] b5=alone?new short[13]:Data.b5;

}
