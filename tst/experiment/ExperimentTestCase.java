package experiment;

import static poker.Constants.totalHighHands;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import equipment.Rank;
import poker.PokerHand;
import poker.PokerHand.HighType;
import junit.framework.TestCase;
import lookup.OldLookup;

public class ExperimentTestCase extends TestCase {
	public void testPokerHands() {
		for(PokerHand.HighType highType:HighType.values()) {
			assertEquals(highType.first(),Experiment.lookup(highType.firstSample().cards()));
			assertEquals(highType.last(),Experiment.lookup(highType.lastSample().cards()));
		}
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
			/*
			final Rank[] ranks=Rank.fromCharacters(cards);
			if(lines==1)
				System.out.println(type+" "+Arrays.asList(ranks));
			*/
			PokerHand pokerHand=PokerHand.from(handNumber);
			int lookedUpHandNumber=Experiment.lookup(pokerHand.cards());
			assertEquals(handNumber,lookedUpHandNumber);
			assertEquals(type,PokerHand.HighType.type(handNumber));
		}
		assertEquals(totalHighHands,lines);
		reader.close();
	}
}
