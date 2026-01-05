package poker;
import static poker.Constants.*;
import junit.framework.TestCase;
import java.io.*;
import java.util.*;
import equipment.*;
import lookup.OldLookup;
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
		final BufferedReader reader=new BufferedReader(new FileReader(f));
		int lines=0;
		for(String line=reader.readLine();line!=null;line=reader.readLine(),lines++) {
			final List<String> l=new LinkedList<String>();
			for(StringTokenizer st=new StringTokenizer(line," ");st.hasMoreTokens();)
				l.add(st.nextToken());
			assert l.size()==10; /* 1 AAAAA 0 1 5 14 14 14 14 14 */
			final int handNumber=Integer.parseInt(l.get(0));
			final String cards=l.get(1);
			final PokerHand.HighType type=PokerHand.HighType.fromCharacter(l.get(4).charAt(0));
			final Rank[] ranks=Rank.fromCharacters(cards);
			if(lines==1) {
			    System.out.println(line);
				System.out.println(type+" "+Arrays.asList(ranks));
			}
			int lookedUpHandNumber=OldLookup.lookup(ranks,type==PokerHand.HighType.flush||type==PokerHand.HighType.straightFlush);
			assertEquals(handNumber,lookedUpHandNumber);
			assertEquals(type,PokerHand.HighType.type(handNumber));
		}
		assertEquals(totalHighHands,lines);
		reader.close();
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
