package poker;
import static poker.Constants.*;
import java.io.*;
import java.nio.file.Files;
import equipment.Rank;
import junit.framework.TestCase;
import lookup.OldLookup;
public class ReadBinaryTestCase extends TestCase {
	public static void main(String[] args) {
		junit.textui.TestRunner.run(ReadBinaryTestCase.class);
	}
	public ReadBinaryTestCase(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testFiveAcesAndFiveDeuces() {
		final Rank[] r=new Rank[5];
		for(int i=0;i<5;i++)
			r[i]=Rank.ace;
		assertEquals(1,OldLookup.lookup(r,false));
		for(int i=0;i<5;i++)
			r[i]=Rank.deuce;
		assertEquals(13,OldLookup.lookup(r,false));
	}
	// 22,1612,1611,1610,1609,21, ... 340,339,338,337,336,14,
	// 13,179,335,323,167,12, ... 28,27,26,25,24,1
	public void testReadBinary() throws Exception {
		final File f=new File("dabinary"); // 7 bytes, 5 ranks and a hand number in little endian
		assertEquals(totalHighHands,flushes+nonFlushes);
		if(f.exists()&&f.canRead()) {
			try (DataInputStream dis=new DataInputStream(Files.newInputStream(f.toPath()))) {
				final byte[] hand=new byte[5];
				final Rank[] rank=new Rank[5];
				int hands=0;
				for(int n=dis.read(hand);n==5;n=dis.read(hand),hands++) {
					for(int i=0;i<5;i++)
						rank[i]=Rank.fromInt(hand[i]+1); // why do we need to add 1?, maybe because it starts at 2. maybe store all stuff as 2-based?
					final PokerHand.HighType type=PokerHand.HighType.fromInteger(dis.read());
					final int lowOrderByte=dis.read(); // original file was little endian, get low order byte
					assert (lowOrderByte>=0);
					final int handNumber=(dis.read()<<8)+lowOrderByte; /* it's in [1,7475] */
					final int n2=OldLookup.lookup(rank/* these have aceLow's in them! */,hands<Constants.nonFlushes?false:true);
					// assertEquals(handNumber,n2);
					if(false) if(hands<14||hands>Constants.nonFlushes-5&&hands<Constants.nonFlushes+6||hands>Constants.totalHighHands-5) {
						System.out.println(Rank.toString(rank)+' '+handNumber+' '+OldLookup.toNumber(rank));
					}
				}
				assertEquals(Constants.totalHighHands,hands);
			}
		} else throw new RuntimeException("can not read: "+f);
	}
}
