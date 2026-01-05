package poker.universe;
import java.io.*;
import java.util.*;
import com.tayek.utilities.Utilities;
import equipment.Rank;
import lookup.OldLookup;
import poker.*;
// this is new code - attempting to make dabinary and the data in lookup from the high universe
public class TestLookupTables {
	static class RanksAndHandNumber {
		RanksAndHandNumber(final Rank[] rank,final short handNumber,final int key) {
			this.rank=rank;
			this.handNumber=handNumber;
			this.key=key;
		}
		public String toString() {
			return Rank.toString(rank)+' '+handNumber+' '+key;
		}
		final Rank[] rank;
		final short handNumber;
		final int key;
	}
	public static void main(String[] argument) throws Exception {
		// duplicated code from LookupTestCase to read file :(
		final File f=new File("highuniverse.txt"); /* make with -d -w */
		final BufferedReader reader=new BufferedReader(new FileReader(f));
		int lines=0;
		final RanksAndHandNumber[] flush=new RanksAndHandNumber[Constants.flushes],other=new RanksAndHandNumber[Constants.nonFlushes];
		int flushes=0,others=0;
		for(String line=reader.readLine();line!=null;line=reader.readLine(),lines++) {
			final List<String> l=new LinkedList<String>();
			for(StringTokenizer st=new StringTokenizer(line," ");st.hasMoreTokens();)
				l.add(st.nextToken());
			assert l.size()==10; /* 1 AAAAA 0 1 5 14 14 14 14 14 */
			final short handNumber=Short.parseShort(l.get(0));
			final String cards=l.get(1);
			final PokerHand.HighType type=PokerHand.HighType.fromCharacter(l.get(4).charAt(0));
			final Rank[] rank=Rank.fromCharacters(cards);
			OldLookup.toCanonicalForm(rank);
			int n=OldLookup.toNumber(rank);
			final RanksAndHandNumber rahn=new RanksAndHandNumber(rank,handNumber,n);
			if(type==PokerHand.HighType.flush||type==PokerHand.HighType.straightFlush) flush[flushes++]=rahn;
			else other[others++]=rahn;
			//if(lines<100) System.out.println(line+' '+n);
		}
		assert flushes==Constants.flushes;
		assert others==Constants.nonFlushes;
		sort(flush);
		toFile(other,new File("others"));
		sort(other);
		toFile(other,new File("others.sorted"));
		//System.out.println(Arrays.asList(flush));
		//System.out.println(13*13*13*13*13);
		// ollections.so
		reader.close();
		for(int i=0;i<Constants.nonFlushes;i++)
			assert other[i].handNumber==OldLookup.other(i);
		for(int i=0;i<Constants.flushes;i++)
			assert flush[i].handNumber==OldLookup.flush(i);
		for(int i=0;i<7;i++)
			System.out.println(other[i]);
		for(int i=other.length-7;i<other.length;i++)
			System.out.println(other[i]);
	}
	private static void toFile(final RanksAndHandNumber[] rahn,final File file) throws Exception {
		StringBuilder sb=new StringBuilder();
		for(RanksAndHandNumber r:rahn)
			sb.append(r).append('\n');
		Utilities.toFile(sb.toString(),file);
	}
	private static void sort(final RanksAndHandNumber[] rahn) {
		for(int i=0;i<rahn.length-1;i++)
			for(int j=i+1;j<rahn.length;j++)
				if(rahn[i].key>rahn[j].key) {
					final RanksAndHandNumber temp=rahn[i];
					rahn[i]=rahn[j];
					rahn[j]=temp;
				}
	}
}
