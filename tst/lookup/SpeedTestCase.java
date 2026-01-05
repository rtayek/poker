package lookup;
import java.util.Arrays;
import poker.PokerHand;
import poker.PokerHand.HighType;
import equipment.Card;
import equipment.Cards;
import equipment.Deck;
import equipment.Rank;
import junit.framework.TestCase;
public class SpeedTestCase extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	int lookup(Card[] cards) {
		int n=OldLookup.lookup(cards);
		if(!(HighType.fiveOfAKind.first()<=n&&n<=HighType.noPair.last())) throw new RuntimeException("oops");
		return n;
	}
    public void testSpeedInlined() {
        int n=0;
        long t0=System.nanoTime();
        Card[] cards=new Card[5];
        for(Card c1:Card.naturals)
            for(Card c2:Card.naturals)
                if(!c2.equals(c1)) for(Card c3:Card.naturals)
                    if(!c3.equals(c2)&&!c3.equals(c1)) for(Card c4:Card.naturals)
                        if(!c4.equals(c3)&&!c4.equals(c2)&&!c4.equals(c1)) for(Card c5:Card.naturals)
                            if(!c5.equals(c4)&&!c5.equals(c3)&&!c5.equals(c2)&&!c5.equals(c1)) {
                                cards[0]=c1;
                                cards[1]=c2;
                                cards[2]=c3;
                                cards[3]=c4;
                                cards[4]=c5;
                                Rank[] ranks=Cards.ranks(cards);
                                boolean isSuited=Cards.areSuited(cards);
                                if(isSuited) {
                                    boolean same=true;
                                    for(int i=0;i<cards.length-1;i++)
                                        if(ranks[i]!=ranks[ranks.length-1]) {
                                            same=false;
                                            break;
                                        }
                                    // just in case we get 5 of a kind all suited!
                                    // we are gettiing suited full houeses and others!
                                    // not a problem now.
                                    // how to fix this?
                                    if(isSuited&&same) isSuited=false;
                                }
                                for(int i=0;i<ranks.length;i++)
                                    if(ranks[i]==Rank.aceLow) ranks[i]=Rank.ace;
                                OldLookup.sortDescending(ranks); // yikes! how could this ever work?
                                // System.out.println("b4 canonical: "+Arrays.asList(ranks));
                                int n1=-1;
                                try {
                                    if(ranks.length>5) System.out.println("warning: ranks has "+ranks.length+" elements.");
									int c11=ranks[0].ordinal()-2;
									int c21=ranks[1].ordinal()-2;
									int c31=ranks[2].ordinal()-2;
									int c41=ranks[3].ordinal()-2;
									int c51=ranks[4].ordinal()-2;
									int n2=-1,index=-1;
									if (isSuited) {
										index=OldLookup.b(c11,0)+OldLookup.b(c21,1)+OldLookup.b(c31,2)+OldLookup.b(c41,3)+OldLookup.b(c51,4);
										if (index>=OldLookup.flush().length) {
											System.out.println(c11+" "+c21+" "+c31+" "+c41+" "+c51);
											System.out.println("badness: "+index+"/"+OldLookup.flush().length);
										}
										n2=OldLookup.flush(index);
									} else {
										index=OldLookup.a(c11,0)+OldLookup.a(c21,1)+OldLookup.a(c31,2)+OldLookup.a(c41,3)+OldLookup.a(c51,4);
										if (index>=OldLookup.other().length) {
											System.out.println(c11+" "+c21+" "+c31+" "+c41+" "+c51);
											System.out.println("badness: "+index+"/"+OldLookup.other().length);
										}
										n2=OldLookup.other(index);
									}
									n1=n2;
								} catch(Exception e) {
									System.out.println("lookup caught: "+Arrays.asList(ranks));
									System.out.println("returning -1!");
									// throw new RuntimeException(e);
								}
								if(!(HighType.fiveOfAKind.first()<=n1&&n1<=HighType.noPair.last())) throw new RuntimeException("oops");
								n++;
							}
		long dt=System.nanoTime()-t0;
		double rate=n*(double)oneBillion/(double)dt;
		double rate2=n/(double)dt*oneBillion;
		System.out.println(rate+" hands/sec.");
		System.out.println(rate2+" hands/sec.");
		assertEquals(Cards.c(52,5)*Cards.f(5),n);
	}
	public void testSpeed() { // all combinations of 5 cards
		int n=0;
		long t0=System.nanoTime();
		Card[] cards=new Card[5];
		for(Card c1:Card.naturals)
			for(Card c2:Card.naturals)
				if (!c2.equals(c1)) for(Card c3:Card.naturals)
					if (!c3.equals(c2)&&!c3.equals(c1)) for(Card c4:Card.naturals)
						if (!c4.equals(c3)&&!c4.equals(c2)&&!c4.equals(c1)) for(Card c5:Card.naturals)
							if (!c5.equals(c4)&&!c5.equals(c3)&&!c5.equals(c2)&&!c5.equals(c1)) {
								cards[0]=c1;
								cards[1]=c2;
								cards[2]=c3;
								cards[3]=c4;
								cards[4]=c5;
								lookup(cards);
								n++;
							}
		long dt=System.nanoTime()-t0;
		double rate=n*(double)oneBillion/(double)dt;
		double rate2=n/(double)dt*oneBillion;
		System.out.println(n+" hands");
		System.out.println(rate+" hands/sec.");
		System.out.println(rate2+" hands/sec.");
		assertEquals(Cards.c(52,5)*Cards.f(5),n);
	}
	static final int oneBillion=1_000_000_000;
}
