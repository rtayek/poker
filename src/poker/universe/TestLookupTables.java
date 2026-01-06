package poker.universe;
import java.io.File;
import com.tayek.utilities.Utilities;
import equipment.Rank;
import lookup.OldLookup;
import poker.Constants;
import poker.HighUniverse;
import poker.PokerHand;
// this is new code - attempting to make dabinary and the data in lookup from the high universe
public class TestLookupTables {
	static record RanksAndHandNumber(Rank[] rank,short handNumber,int key) {
		@Override public String toString() {
			return Rank.toString(rank)+' '+handNumber+' '+key;
		}
	}
	public static void main(String[] argument) throws Exception {
		final File f=new File("highuniverse.txt"); /* make with -d -w */
		final RanksAndHandNumber[] flush=new RanksAndHandNumber[Constants.flushes],other=new RanksAndHandNumber[Constants.nonFlushes];
		int flushes=0,others=0;
		for(HighUniverse.Entry entry:HighUniverse.read(f)) {
			final short handNumber=(short)entry.handNumber();
			final PokerHand.HighType type=entry.type();
			final Rank[] rank=entry.ranks();
			OldLookup.toCanonicalForm(rank);
			int n=OldLookup.toNumber(rank);
			final RanksAndHandNumber rahn=new RanksAndHandNumber(rank,handNumber,n);
			if(type==PokerHand.HighType.flush||type==PokerHand.HighType.straightFlush) flush[flushes++]=rahn;
			else other[others++]=rahn;
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
		for(int i=0;i<Constants.nonFlushes;i++)
			assert other[i].handNumber()==OldLookup.other(i);
		for(int i=0;i<Constants.flushes;i++)
			assert flush[i].handNumber()==OldLookup.flush(i);
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
				if(rahn[i].key()>rahn[j].key()) {
					final RanksAndHandNumber temp=rahn[i];
					rahn[i]=rahn[j];
					rahn[j]=temp;
				}
	}
}
