package poker;
import static poker.Constants.*;
import junit.framework.TestCase;
import java.io.*;
import java.util.*;
import equipment.*;
import lookup.OldLookup;
import poker.HighUniverse;
public class LookupTestCase extends TestCase {
	public static void main(String[] args) {
		junit.textui.TestRunner.run(LookupTestCase.class);
	}
	public LookupTestCase(String name) {
		super(name);
		System.out.println(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testHighUniverseFile() throws Exception {
		final File f=new File("highuniverse.txt"); /* make with -d -w */
		int lines=0;
		for(HighUniverse.Entry entry:HighUniverse.read(f)) {
			if(lines==1) {
				System.out.println(entry.line);
				System.out.println(entry.type+" "+Arrays.asList(entry.ranks));
			}
			int lookedUpHandNumber=OldLookup.lookup(entry.ranks,entry.type==PokerHand.HighType.flush||entry.type==PokerHand.HighType.straightFlush);
			assertEquals(entry.handNumber,lookedUpHandNumber);
			assertEquals(entry.type,PokerHand.HighType.type(entry.handNumber));
			lines++;
		}
		assertEquals(totalHighHands,lines);
	}
	public void testVerify() {
		assertTrue(OldLookup.verify());
	}
	public void testAllCombinationsOfFiveCards() {
		int n=52,n1=n-1,n2=n-2,n3=n-3,n4=n-4;
		int c1,c2,c3,c4,c5;
		final Card[] deck=Deck.create(1,0),cards=new Card[5];
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
							final int highHandNumber=OldLookup.lookup(cards);
							assertTrue("lookup fails on: "+new PokerHand(cards),highHandNumber!=0);
							final Card[] inverse=OldLookup.inverseLookup(highHandNumber);
							final Card[] original=OldLookup.toCanonicalForm(cards);
							for(int i=0;i<5;i++)
								assertEquals(original[i],inverse[i]);
							hands++;
						}
					}
				}
			}
		}
		assertEquals("hands should equal total",naturalFiveCardCombinations,hands);
	}
	int hands;
	long time;
}
